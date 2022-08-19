package org.neophyte.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.model.Anggota;
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateAnggotaController {
    public TextField nameField;
    public TextField nimField;
    public TextField prodiField;
    public TextField semesterField;
    public Button saveButton;
    public Button cancleButton;

    Anggota anggota;

    public void save() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (nameField.getText().matches(".*\\d.*")){
            alert.setContentText("Nama tidak boleh berisi angka.");
            alert.show();
        } else {
            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement;
                if (anggota == null)
                    preparedStatement = connection.prepareStatement("INSERT INTO ANGGOTA (nama, nim, prodi, semester) VALUES (?, ?, ?,?)");
                else {
                    preparedStatement = connection.prepareStatement("UPDATE ANGGOTA SET nama= ? , nim = ?, PRODI = ?, SEMESTER = ? WHERE IDANGGOTA = ?");
                    preparedStatement.setInt(5, anggota.getIdanggota());
                }
                preparedStatement.setString(1, nameField.getText());
                preparedStatement.setString(2, nimField.getText());
                preparedStatement.setString(3, prodiField.getText());
                preparedStatement.setString(4, semesterField.getText());
                preparedStatement.executeUpdate();
                CrudAnggotaController.loadData();
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        ((Stage) cancleButton.getScene().getWindow()).close();
    }
    public void fillForm(Anggota anggota) {
        this.anggota = anggota;
        nameField.setText(anggota.getNama());
        nimField.setText(anggota.getNim());
        prodiField.setText(anggota.getProdi());
        semesterField.setText(Integer.toString(anggota.getSemester()));
    }
}
