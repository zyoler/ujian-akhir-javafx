package org.neophyte.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class CreateAdminController {
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField nameField;

    public Button saveButton;
    public Button cancleButton;

    User user;

    public void close() {
        ((Stage) cancleButton.getScene().getWindow()).close();
    }

    public void save(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (Objects.equals(usernameField.getText(), "")) {
            alert.setContentText("Nama pengguna belum diisi.");
            alert.show();
        } else if (Objects.equals(passwordField.getText(), "")) {
            alert.setContentText("Kata sandi belum diisi.");
            alert.show();
        } else if (Objects.equals(nameField.getText(), "")) {
            alert.setContentText("Nama belum diisi.");
            alert.show();
        } else if (nameField.getText().matches(".*\\d.*")) {
            alert.setContentText("Nama tidak boleh berisi angka.");
            alert.show();
        } else {
            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement;
                if (user == null) // Tambah user
                    preparedStatement = connection.prepareStatement("INSERT INTO USER (username, password, name) VALUES (?, ?, ?)");
                else { // Edit user
                    preparedStatement = connection.prepareStatement("UPDATE user SET username = ?, password = ?, name = ? WHERE id = ?");
                    preparedStatement.setInt(4, user.getId());
                }
                preparedStatement.setString(1, usernameField.getText());
                preparedStatement.setString(2, passwordField.getText());
                preparedStatement.setString(3, nameField.getText());
                preparedStatement.executeUpdate();
                CrudAdminController.loadData();
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillForm(User user) {
        this.user = user;
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        nameField.setText(user.getName());
    }
}
