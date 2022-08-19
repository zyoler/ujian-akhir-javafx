package org.neo.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.neo.models.Admin;
import org.neo.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CrudAdmin {
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField nameField;
    public TextField nameField1;
    public Button saveButton;
    public Button cancelButton;

    Admin admin;
    int maxLength = 13;

    public void initialize(){
        nameField1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                nameField1.setText(newValue.replaceAll("[^\\d]", ""));
            }else if (nameField1.getText().length() > maxLength) {
                String s = nameField1.getText().substring(0, maxLength);
                nameField1.setText(s);
            }
        });

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                nameField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        saveButton.setOnAction(event -> {
            if(usernameField.getText().trim().isEmpty() || passwordField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty() || nameField1.getText().trim().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning!!");
                alert.setHeaderText(null);
                alert.setContentText("Harap isi data terlebih dahulu!");
                alert.show();
            }
            try {
                Connection connection = Database.connect();
                PreparedStatement preparedStatement;
                if (admin == null)
                    preparedStatement = connection.prepareStatement("INSERT INTO ADMIN (FULLNAME, NOHP, USERNAME, PASSWORD) VALUES (?, ?, ?, ?)");
                else {
                    preparedStatement = connection.prepareStatement("UPDATE ADMIN SET FULLNAME = ?, NOHP = ?, USERNAME = ?, PASSWORD = ? WHERE ID_ADMIN = ?");
                    preparedStatement.setInt(5, admin.getId_admin());
                }
                preparedStatement.setString(1, nameField.getText());
                preparedStatement.setString(2, nameField1.getText());
                preparedStatement.setString(3, usernameField.getText());
                preparedStatement.setString(4, passwordField.getText());
                preparedStatement.executeUpdate();
                AdminPanelController.loadDataAdmin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void fillForm(Admin admin) {
        this.admin = admin;
        usernameField.setText(admin.getUsername());
        passwordField.setText(admin.getPassword());
        nameField.setText(admin.getFullName());
        nameField1.setText(admin.getNoHp());
    }
}
