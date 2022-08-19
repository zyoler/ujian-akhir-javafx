package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.neophyte.util.Database;
import org.neophyte.Main;
import org.neophyte.model.User;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class MainLoginController {

    public Button loginButton;
    public TextField usernameField;
    public PasswordField passwordField;

    Stage stage = Main.getStage();

    public void initialize(){
        loginButton.setOnAction(event -> {

            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
                preparedStatement.setString(1, usernameField.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if (Objects.equals(passwordField.getText(), resultSet.getString("password"))) {
                        ((Stage) loginButton.getScene().getWindow()).close();
                        Main.loggedInUser = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("name"));
                        Main.refresh();
                            try {
                                HBox hBox = FXMLLoader.load(getClass().getResource("/HalamanUtama.fxml"));
                                Scene adminScene = new Scene(hBox);
                                stage.setScene(adminScene);
                                stage.setTitle("Menu");
                                stage.show();
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
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        });

    }
}
