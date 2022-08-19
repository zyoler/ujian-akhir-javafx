package org.neophyte.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.User;
import org.neophyte.utils.DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {
    public static Stage stage;
    public Button BtnLoggin;
    public TextField usernamefield;
    public PasswordField passwordField;
    public CheckBox cekBokShowPassword;
    public TextField fieldSow;
    public JFXButton btnForgotPass;

    @FXML
    void initialize(){

        fieldSow.managedProperty().bind(cekBokShowPassword.selectedProperty());
        fieldSow.visibleProperty().bind(cekBokShowPassword.selectedProperty());

        passwordField.managedProperty().bind(cekBokShowPassword.selectedProperty().not());
        passwordField.visibleProperty().bind(cekBokShowPassword.selectedProperty().not());

        fieldSow.textProperty().bindBidirectional(passwordField.textProperty());

        BtnLoggin.setOnAction(event -> {
            Stage mainStage = new Stage();
            Parent root = null;
            try {
                Connection connection = DataBase.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
                preparedStatement.setString(1, usernamefield.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    // Berhasil
                    if (Objects.equals(passwordField.getText(), resultSet.getString("password"))) {
                        Main.logginInadmin = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("nama_user"), resultSet.getBoolean("admin"));
                        //Main.refresh();
                        if (Main.logginInadmin.isAdmin()){
                            Main.cek = "admin";
                            try {
                                root = FXMLLoader.load(getClass().getResource("/main.fxml"));
                                Scene adminScene = new Scene(root);
                                mainStage.setScene(adminScene);
                                stage = mainStage;
                                mainStage.show();
                                Main.getMainStage().close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Main.logginInadmin = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("nama_user"), resultSet.getBoolean("admin"));
                            try {
                                root = FXMLLoader.load(getClass().getResource("/main.fxml"));
                                Scene adminScene = new Scene(root);
                                mainStage.setScene(adminScene);
                                stage = mainStage;
                                mainStage.show();
                                Main.getMainStage().close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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

        btnForgotPass.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/masukForgot.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setResizable(false);
                stage.initOwner(Main.getMainStage());
                ((Stage) btnForgotPass.getScene().getWindow()).close();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public static Stage getStage() {
        return stage;
    }
}
