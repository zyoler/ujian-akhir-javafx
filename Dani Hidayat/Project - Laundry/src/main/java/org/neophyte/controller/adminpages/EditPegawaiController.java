package org.neophyte.controller.adminpages;

import javafx.scene.control.*;
import javafx.stage.Stage;
import org.neophyte.controller.Validation;
import org.neophyte.model.Pegawai;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Objects;

public class EditPegawaiController {

    public TextField idField;
    public TextField namaField;
    public TextField alamatField;
    public TextField usiaField;
    public TextField telpButton;
    public RadioButton ikhwanButton;
    public RadioButton akhwatButton;
    public Button saveButton;
    public Button closeButton;
    Pegawai pegawai;

    public void fillForm(Pegawai pegawai){
        this.pegawai = pegawai;
        idField.setText(pegawai.getId());
        namaField.setText(pegawai.getNama());
        alamatField.setText(pegawai.getAlamat());
        usiaField.setText(pegawai.getUsia());
        telpButton.setText(pegawai.getNoTelp());
        if(Objects.equals(pegawai.getJenisKelamin(), "Laki-laki")){
            ikhwanButton.setSelected(true);
        }else{
            akhwatButton.setSelected(true);
        }
    }

    public void initialize(){
        idField.setDisable(true);
        validation();

        final ToggleGroup group = new ToggleGroup();
        ikhwanButton.setToggleGroup(group);
        akhwatButton.setToggleGroup(group);
        saveButton.setOnAction(event -> {
            ArrayList<Object> cek = new ArrayList<>();
            cek.add(namaField.getText());cek.add(alamatField.getText());cek.add(usiaField.getText());cek.add(telpButton.getText());
            if(cek.contains("")){
                alert();
            }else if(!(ikhwanButton.isSelected() || akhwatButton.isSelected())){
                alert();
            }else{
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE pegawai SET nama = ?, alamat = ?, usia = ?,jk = ?,telp = ? WHERE id = ?");
                    preparedStatement.setString(1, namaField.getText());
                    preparedStatement.setString(2, alamatField.getText());
                    preparedStatement.setString(3, usiaField.getText());
                    preparedStatement.setString(5, telpButton.getText());
                    if (ikhwanButton.isSelected()) {
                        preparedStatement.setString(4, "Laki-laki");
                    } else {
                        preparedStatement.setString(4, "Perempuan");
                    }
                    preparedStatement.setString(6, pegawai.getId());
                    preparedStatement.executeUpdate();
                    PegawaiController.loadData();
                    ((Stage) closeButton.getScene().getWindow()).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        closeButton.setOnAction(event -> {
            ((Stage) closeButton.getScene().getWindow()).close();
        });
    }

    public void validation() {
        Validation.textOnly(namaField);
        Validation.numericOnly(telpButton);Validation.addTextLimiter(telpButton,13);
        Validation.numericOnly(usiaField);Validation.addTextLimiter(usiaField,2);
    }

    public void alert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Pastikan data terisi semua");
        alert.show();
    }
}
