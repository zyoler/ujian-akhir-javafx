package org.neophyte.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.model.User;
import org.neophyte.utils.DataBase;
import org.neophyte.validasi.ValidasiUmum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class CrudUserController {
    public Button saveUserBtn;
    public Button cancelUserBtn;

    public TextField TextFieldPassword;
    public TextField TextFieldUsername;
    public TextField TextFieldName;
    public CheckBox adminCheckBox;


    User user;

    public void close() {
        ((Stage) cancelUserBtn.getScene().getWindow()).close();
    }

    public void save() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (Objects.equals(TextFieldUsername.getText(), "")) {
            alert.setContentText("Username belum diisi.");
            alert.show();
        } else if (ValidasiUmum.username(TextFieldUsername.getText())==false) {
            alert.setContentText("Username Hanya angka dan huruf, dan 6 - 30 carachter");
            alert.show();
        } else if (Objects.equals(TextFieldPassword.getText(), "")) {
            alert.setContentText("Kata sandi belum diisi.");
            alert.show();
        } else if (ValidasiUmum.password(TextFieldPassword.getText())==false) {
            alert.setContentText("Kata Sandi Berisi Angka dan Huruf saja! 8 karakter");
            alert.show();
        } else if (Objects.equals(TextFieldName.getText(), "")) {
            alert.setContentText("Nama belum diisi.");
            alert.show();
        } else if (!ValidasiUmum.nama2(TextFieldName.getText())) {
            alert.setContentText("Nama tidak boleh berisi angka");
            alert.show();
        } else {
            try {
                Connection connection = DataBase.getConnection();
                PreparedStatement preparedStatement;
                if (user == null) // Tambah user
                    preparedStatement = connection.prepareStatement("INSERT INTO user (username, password, nama_user, admin) VALUES (?, ?, ?, ?)");
                else { // Edit user
                    preparedStatement = connection.prepareStatement("UPDATE user SET username = ?, password = ?, nama_user = ?, admin = ? WHERE id = ?");
                    preparedStatement.setInt(5, user.getId());
                }
                preparedStatement.setString(1, TextFieldUsername.getText());
                preparedStatement.setString(2, TextFieldPassword.getText());
                preparedStatement.setString(3, TextFieldName.getText());
                preparedStatement.setBoolean(4, adminCheckBox.isSelected());
                preparedStatement.executeUpdate();
                KelolaStudioController.loadData();
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillForm(User user) {
        this.user = user;
        TextFieldUsername.setText(user.getUsername());
        TextFieldPassword.setText(user.getPassword());
        TextFieldName.setText(user.getNamaUser());
        adminCheckBox.setSelected(user.isAdmin());
    }
}
