package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {
    public Button loginButton;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button exitButton;

    public void validation(){
        Validasi.addTextLimiter(usernameField,50);
        Validasi.addTextLimiter(passwordField,50);
    }

    public void initialize(){
        loginButton.setOnAction(event -> {
            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
                preparedStatement.setString(1, usernameField.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    // Berhasil
                    if (Objects.equals(passwordField.getText(), resultSet.getString("password"))) {

                        ((Stage) loginButton.getScene().getWindow()).close();
                        Main.loggedInUser = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("name"), resultSet.getBoolean("admin"));
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/pages/home.fxml"));
                            Scene adminScene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(adminScene);
                            stage.show();
                            System.out.println("abcdefghijklm");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else { // Kata sandi salah
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Kata sandi salah");
                        alert.setHeaderText(null);
                        alert.setContentText("Kata sandi yang Anda masukkan salah.");
                        alert.show();
                    }
                } else { // Nama pengguna salah
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Pengguna tidak ditemukan");
                    alert.setHeaderText(null);
                    alert.setContentText("Nama pengguna yang Anda masukkan tidak ditemukan.");
                    alert.show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        exitButton.setOnAction(event -> {
            ((Stage) exitButton.getScene().getWindow()).close();

        });
    }
}
