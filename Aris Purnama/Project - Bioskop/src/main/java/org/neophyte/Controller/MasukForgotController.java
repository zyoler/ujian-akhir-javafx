package org.neophyte.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.utils.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class MasukForgotController {
    public TextField textUsername;
    public Button nextBtnMask;
    public static String tampUsername;
    private Object SQLException;

    public void initialize() {
        nextBtnMask.setOnAction(event -> {
            Connection conn = DataBase.getConnection();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            PreparedStatement preparedStatement = null;
            try {
                try {
                    preparedStatement = conn.prepareStatement("SELECT DISTINCT USERNAME FROM USER WHERE USERNAME = ?");
                    preparedStatement.setString(1, textUsername.getText());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        if (Objects.equals(resultSet.getString("username"), textUsername.getText())) {
                            tampUsername = resultSet.getString("username");
                            System.out.println(tampUsername);
                            alert.setContentText("Username Ditemukan Dengan Usernama : " + resultSet.getString("username"));
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.OK) {
                                ((Stage) nextBtnMask.getScene().getWindow()).close();
                                try {
                                    Parent root = FXMLLoader.load(getClass().getResource("/view/forgot.fxml"));
                                    Scene scene = new Scene(root);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.initModality(Modality.WINDOW_MODAL);
                                    stage.setResizable(false);
                                    stage.initOwner(Main.getMainStage());
                                    stage.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else{
                                alert.setContentText("Data Tidak Ditemukan! Silahkan Inputkan Kembali");
                                alert.show();
                            }
                        }else{
                            alert.setContentText("Data Tidak Ditemukan! Silahkan Inputkan Kembali");
                            alert.show();
                        }

                    }
                    alert.setContentText("Data Tidak Ditemukan! Silahkan Inputkan Kembali");
                    alert.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}