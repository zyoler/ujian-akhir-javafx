package org.rental.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.rental.model.Admin;
import org.rental.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class CreateAdminController {

    public Button saveButton;
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField nameField;
    public Button cancelButton;
    Admin admin;

    public void close(){
        ((Stage) usernameField.getScene().getWindow()).close();
    }

    public void initialize() {
        saveButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            if (Objects.equals(usernameField.getText(), "")) {
                alert.setContentText("Nama Pengguna belum diisi");
                alert.show();
            } else if (Objects.equals(passwordField.getText(), "")) {
                alert.setContentText("password belum diisi");
                alert.show();
            } else if (Objects.equals(nameField.getText(), "")) {
                alert.setContentText("namefield belum diisi");
                alert.show();
            } else if (nameField.getText().matches(".*\\d.*")) {
                alert.setContentText("Nama tidak boleh berisi angka");
                alert.show();
            } else {
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement;
                    if (admin == null) {
                        preparedStatement = connection.prepareStatement("INSERT INTO ADMIN (username,password,nama_admin) VALUES(?,?,?)");

                    } else {
                        preparedStatement = connection.prepareStatement("UPDATE admin SET username = ? ,password = ?,nama_admin = ? WHERE id=?");
                        preparedStatement.setInt(4, admin.getId());
                    }
                    preparedStatement.setString(1, usernameField.getText());
                    preparedStatement.setString(2, passwordField.getText());
                    preparedStatement.setString(3, nameField.getText());
                    preparedStatement.executeUpdate();
                    CrudAdminController.loadDAta();
                    ((Stage) usernameField.getScene().getWindow()).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cancelButton.setOnAction(event -> {
            close();
        });

    }

    public void fillForm(Admin admin){
        this.admin = admin;
        usernameField.setText(admin.getUsername());
        passwordField.setText(admin.getPassword());
        nameField.setText(admin.getNama_admin());

    }

}
