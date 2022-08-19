package org.neo.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import org.neo.main.Main;
import org.neo.models.Admin;
import org.neo.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginController {

    public TextField userTextile;
    public PasswordField passTextile;
    public Button loginButton;
    public CheckBox checkAdmin;
    Database db = new Database();

    public void initialize(){
        loginButton.setOnAction(event -> {
            try {
                Connection connection = db.connect();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM admin WHERE username = ?");
                preparedStatement.setString(1,userTextile.getText());
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()) {
                    if(Objects.equals(passTextile.getText(), rs.getString("password"))){
                        Main.admLogged = new Admin(rs.getString("fullname"),rs.getString("username"),rs.getString("nohp"));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Berhasil Login!!");
                        alert.setHeaderText(null);
                        alert.setContentText("Anda Berhasil Login");
                        ButtonType result = alert.showAndWait().get();
                        if (result.getText().equals("OK")) {
                            ((Stage) loginButton.getScene().getWindow()).close();
                            Main.refresh();
                        }
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Gagal Login!");
                        alert.setHeaderText(null);
                        alert.setContentText("Password anda salah!");
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Gagal Login!");
                    alert.setHeaderText(null);
                    alert.setContentText("User admin tidak ditemukan!");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }
}
