package org.neophyte.controller;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateUserController {

    public TextField idField;
    public TextField usernameField;
    public TextField passwordField;
    public TextField nameField;
    public CheckBox isActive;
    public Button saveButton;
    public Button cancelButton;

    User user;

    public void cancel(){
        ((Stage) usernameField.getScene().getWindow()).close();
    }

    public void save() {

        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("UPDATE user SET username = ?, password = ?, nama = ?, active = ? WHERE id = ? ");
            preparedStatement.setString(1,usernameField.getText());
            preparedStatement.setString(2,passwordField.getText());
            preparedStatement.setString(3,nameField.getText());
            preparedStatement.setBoolean(4,isActive.isSelected());
            preparedStatement.setInt(5,user.getId());
            preparedStatement.executeUpdate();
            Adminmastercontroller.loadData();
            ((Stage) usernameField.getScene().getWindow()).close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillForm(User user){
        this.user = user;
        idField.setText(user.getId() + "");
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        nameField.setText(user.getName());
        isActive.setSelected(user.isActive());
        if(this.user.getId() == 1){
            isActive.setDisable(true);
            usernameField.setDisable(true);
        }
    }

    public void initialize() {

    }
}
