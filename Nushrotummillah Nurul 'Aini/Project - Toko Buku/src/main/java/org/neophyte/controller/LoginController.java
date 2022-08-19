package org.neophyte.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginController {

    public Button loginButton;
    public TextField username;
    public PasswordField password;

    public void initialize(){
        loginButton.setOnAction(event -> {
            try{
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from user where username = ?");
                preparedStatement.setString(1, username.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    if(Objects.equals(password.getText(),resultSet.getString("password"))){
                        ((Stage) loginButton.getScene().getWindow()).close();
                        Main.loggedInUser = new User(resultSet.getInt("id"), resultSet.getString("nama"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getBoolean("admin"));
                        if(Main.loggedInUser.isAdmin()){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Admin");
                            alert.setHeaderText(null);
                            alert.setContentText("Welcome Admin.");
                            alert.show();

                        }else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("User");
                            alert.setHeaderText(null);
                            alert.setContentText("Welcome User.");
                            alert.show();
                        }
                        MainController.log = true;
                        MainController.refresh();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Wrong password");
                        alert.setHeaderText(null);
                        alert.setContentText("Password yang Anda masukkan salah.");
                        alert.show();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Username not found");
                    alert.setHeaderText(null);
                    alert.setContentText("Username yang Anda masukkan tidak ditemukan.");
                    alert.show();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}