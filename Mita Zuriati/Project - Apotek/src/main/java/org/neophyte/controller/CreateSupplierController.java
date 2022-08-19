package org.neophyte.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.model.Obat;
import org.neophyte.model.Supplier;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.regex.Pattern;

public class CreateSupplierController {
    public TextField idSuppField;
    public TextField namaSuppField;
    public TextField noTelpField;
    public TextField alamatField;

    public Button saveSuppButton;
    public Button cancelSuppButton;
    
    Supplier supplier;

    public void close() {
        ((Stage)cancelSuppButton.getScene().getWindow()).close();
    }
    public void validation() {
        Validasi.textOnly(namaSuppField);
        Validasi.numericOnly(noTelpField);
        Validasi.addTextLimiter(namaSuppField,50);
        Validasi.addTextLimiter(noTelpField,13);
        Validasi.addTextLimiter(alamatField,100);
    }
    public boolean isNumber(String in){
        return Pattern.matches("[0-9]+",in);
    }
    public void save() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (Objects.equals(namaSuppField.getText(), "")) {
            alert.setContentText("Nama Supplier belum diisi.");
            alert.show();
        }else if(Objects.equals(noTelpField.getText(),"")){
            alert.setContentText("Nomor Telepon belum diisi");
            alert.show();
        }else if(Objects.equals(alamatField.getText(),"")){
            alert.setContentText("Alamat belum diisi");
            alert.show();
        }else {
            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement;
                if (supplier == null) // Tambah obat
                    preparedStatement = connection.prepareStatement("INSERT INTO supplier (IDSUPPLIER, NAMASUPPLIER, NOTELEPON, ALAMAT) VALUES (?, ?, ?, ?)");
                else { // Edit obat
                    preparedStatement = connection.prepareStatement("UPDATE supplier SET IDSUPPLIER = ?, NAMASUPPLIER = ?, NOTELEPON = ?, ALAMAT = ? WHERE IDSUPPLIER = ?");
                    preparedStatement.setString(5, supplier.getIdSupplier());
                }
                preparedStatement.setString(1, idSuppField.getText());
                preparedStatement.setString(2, namaSuppField.getText());
                preparedStatement.setString(3, noTelpField.getText());
                preparedStatement.setString(4, alamatField.getText());
                preparedStatement.executeUpdate();
                DataController.loadDataSupp();
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void fillForm(Supplier supplier) {
        this.supplier = supplier;
        idSuppField.setText(supplier.getIdSupplier());
        namaSuppField.setText(supplier.getNamaSupplier());
        noTelpField.setText(supplier.getNoTelepon());
        alamatField.setText(supplier.getAlamat());
    }
    public void cekId(int tamp){
        if(tamp < 10)
            idSuppField.setText("S00" + tamp);
        else if(tamp < 100)
            idSuppField.setText("S0" + tamp);
        idSuppField.setEditable(false);
    }
    public void loadId(){
        int tamp = DataController.nampungId2;
        cekId(tamp);
    }

    public void initialize() {
        loadId();
        validation();
    }
}
