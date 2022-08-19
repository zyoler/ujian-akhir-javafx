package org.neophyte.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class CreateUserController {

    public TextField usernameField;
    public PasswordField passwordField;
    public TextField nameField;
    public CheckBox adminCheckBox;

    public Button saveButton;
    public Button cancelButton;

    User user;

    public void validation(){
        Validasi.addTextLimiter(usernameField,50);
        Validasi.addTextLimiter(passwordField,50);
        Validasi.addTextLimiter(nameField,50);
        Validasi.textOnly(nameField);
    }
    public void initialize(){
        validation();
    }
    public void close() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
    public void save() {
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
        } else {
            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement;
                if (user == null) // Tambah user
                    preparedStatement = connection.prepareStatement("INSERT INTO user (username, password, name, admin) VALUES (?, ?, ?, ?)");
                else { // Edit user
                    preparedStatement = connection.prepareStatement("UPDATE user SET username = ?, password = ?, name = ?, admin = ? WHERE id = ?");
                    preparedStatement.setInt(5, user.getId());
                }
                preparedStatement.setString(1, usernameField.getText());
                preparedStatement.setString(2, passwordField.getText());
                preparedStatement.setString(3, nameField.getText());
                preparedStatement.setBoolean(4, adminCheckBox.isSelected());
                preparedStatement.executeUpdate();
                AdminPanelController.loadData();
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
        adminCheckBox.setSelected(user.isAdmin());
    }
}
