package org.neophyte.controller.adminpages;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.controller.Validation;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;

public class TambahPelangganController {

    public Button cancelButton;
    public Button simpanButton;
    public TextField idField;
    public TextField namaField;
    public TextField alamatField;
    public TextField telpField;
    public DatePicker masukDate;

    public void initialize(){
        validation();
        cekID(PelangganController.nampungId);

        LocalDate hariIni = LocalDate.now();

        masukDate.setValue(hariIni);
        masukDate.setDisable(true);

        simpanButton.setOnAction(event -> {
            ArrayList<Object> cek = new ArrayList<>();
            cek.add(namaField.getText());cek.add(alamatField.getText());cek.add(telpField.getText());
            if(cek.contains("")){
                System.out.println("Pastikan data terisi semua");
            }else{
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO pelanggan VALUES (?,?,?,?,?,?)");
                    preparedStatement.setString(1, idField.getText());
                    preparedStatement.setString(2, namaField.getText());
                    preparedStatement.setString(3, alamatField.getText());
                    preparedStatement.setString(4, telpField.getText());
                    preparedStatement.setDate(5, Date.valueOf(masukDate.getValue()));
                    preparedStatement.setString(6,"Kosong");
                    preparedStatement.executeUpdate();
                    PelangganController.loadData();
                    close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });
        cancelButton.setOnAction(event -> {
            close();
        });
    }
    public void cekID(int nampungId){
        if(nampungId < 10)
            idField.setText("PLG00" + nampungId);
        else if(nampungId < 100)
            idField.setText("PLG0" + nampungId);
        else
            idField.setText("PLG" + nampungId);
    }

    public void close(){
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public void validation(){
        Validation.textOnly(namaField);
        Validation.numericOnly(telpField);
        Validation.addTextLimiter(telpField,13);
    }
}
