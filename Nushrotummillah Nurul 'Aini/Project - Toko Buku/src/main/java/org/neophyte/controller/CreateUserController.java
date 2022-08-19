package org.neophyte.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class CreateUserController {

    public Button cancel;
    public Button saveButton;
    public TextField nameField;
    public TextField usernameField;
    public PasswordField passwordField;
    public CheckBox adminCheckBox;

    User user;

    public void cancelButton() {
        ((Stage) cancel.getScene().getWindow()).close();
    }

    public void saveUserAction() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (Objects.equals(nameField.getText(), "")) {
            alert.setContentText("Nama belum diisi.");
            alert.show();
        } else if (Objects.equals(usernameField.getText(), "")) {
            alert.setContentText("Username belum diisi.");
            alert.show();
        } else if (Objects.equals(passwordField.getText(), "")) {
            alert.setContentText("Password belum diisi.");
            alert.show();
        } else if (nameField.getText().matches(".*\\d.*")) {
            alert.setContentText("Nama tidak boleh terdapat angka.");
            alert.show();
        } else {
            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement;
                if (user == null)
                    preparedStatement = connection.prepareStatement("INSERT INTO user (nama, username, password, admin) VALUES (?, ?, ?, ?)");
                else {
                    preparedStatement = connection.prepareStatement("UPDATE user SET nama = ?, username = ?, password = ?, admin = ? WHERE id = ?");
                    preparedStatement.setInt(5, user.getId());
                }
                preparedStatement.setString(1, nameField.getText());
                preparedStatement.setString(2, usernameField.getText());
                preparedStatement.setString(3, passwordField.getText());
                preparedStatement.setBoolean(4, adminCheckBox.isSelected());
                preparedStatement.executeUpdate();
                UserController.loadData();
                cancelButton();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillForm(User user){
        this.user = user;
        nameField.setText(user.getNama());
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        adminCheckBox.setSelected(user.isAdmin());
    }
}
