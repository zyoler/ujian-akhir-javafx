package org.rental.controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import org.rental.Main;
import org.rental.util.Database;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import static org.rental.Main.stage;

public class LoginController {
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;

    public void initialize() {
        loginButton.setOnAction(event -> {
            try {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from admin where username=?");
                preparedStatement.setString(1,usernameField.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                if(Objects.equals(usernameField.getText(),"") && Objects.equals(passwordField.getText(),"")){
                    alert.setHeaderText(null);
                    alert.setContentText("Username dan password belum diisi");
                    alert.show();
                }else if(Objects.equals(usernameField.getText(),"")){
                    alert.setHeaderText(null);
                    alert.setContentText("Username belum diisi");
                    alert.show();
                }else if(Objects.equals(passwordField.getText(),"")) {
                    alert.setHeaderText(null);
                    alert.setContentText("password belum diisi");
                    alert.show();
                }else{
                    if (resultSet.next()) {
                        if (Objects.equals(passwordField.getText(), resultSet.getString("password"))) {
                            Parent root = FXMLLoader.load(Main.class.getResource("/main.fxml"));
                            Scene sceneLogin = new Scene(root, 700, 500);
                            stage.setScene(sceneLogin);
                            stage.setTitle("Rental Mobil Login");
                            stage.show();
                        }else {
                            alert.setTitle("Kata Sandi Salah");
                            alert.setHeaderText(null);
                            alert.setContentText("Kata sandi yang anda masukan salah");
                            alert.show();
                        }

                    }else {
                        alert.setTitle("Data Admin tidak ditemukan");
                        alert.setHeaderText(null);
                        alert.setContentText("Data admin tidak ditemukan");
                        alert.show();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
