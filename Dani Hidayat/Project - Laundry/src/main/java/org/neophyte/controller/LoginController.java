package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.neophyte.util.Database;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class LoginController {

    public TextField usernameField;
    public PasswordField passwordField;
    public Button openButton;
    public Button quitButton;

    public void initialize() {
        openButton.setOnAction(event -> {
            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
                preparedStatement.setString(1, usernameField.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if(Objects.equals(passwordField.getText(), resultSet.getString("password"))) {
                        ((Stage) openButton.getScene().getWindow()).close();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("WELCOME");
                        alert.setContentText("Selamat datang " + resultSet.getString("nama"));
                        alert.showAndWait();
                        Parent mainMenu = FXMLLoader.load(getClass().getResource("/pages/main_menu.fxml"));
                        Scene mainScene = new Scene(mainMenu);
                        Stage mainStage = new Stage();
                        mainScene.getStylesheets().add("styles/style.css");
                        mainStage.setScene(mainScene);
                        mainStage.show();
                        Stage stage = new Stage();
                        if(alert.getResult() == ButtonType.OK && (Objects.equals(resultSet.getString("username"), "owner"))  ) {
                            Parent root = FXMLLoader.load(getClass().getResource("/crud/admin_master.fxml"));
                            Scene scene = new Scene(root);
                            stage.initStyle(StageStyle.TRANSPARENT);
                            scene.setFill(Color.TRANSPARENT);
                            scene.getStylesheets().add("styles/styles_table_view.css");
                            stage.setScene(scene);
                            stage.show();
                        }
                        mainStage.setOnCloseRequest(event1 -> {
                            stage.close();
                        });
                    }else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Kata sandi salah");
                        alert.setContentText("Kata sandi yang anda masukan salah");
                        alert.show();
                        usernameField.setText("");passwordField.setText("");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Username salah");
                    alert.setContentText("Username yang anda masukan salah");
                    alert.show();
                    usernameField.setText("");passwordField.setText("");
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        });

        quitButton.setOnAction(event -> {
            ((Stage) quitButton.getScene().getWindow()).close();
        });
    }
}