package org.neo.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    public Button loginButton;

    public void initialize(){
        loginButton.setOnAction(event -> {
            Stage loginStage = new Stage();
            try {
                VBox vBox = FXMLLoader.load(getClass().getResource("/login.fxml"));
                Scene scene = new Scene(vBox);
                loginStage.setScene(scene);
                loginStage.setTitle("Masuk");
                loginStage.setResizable(false);
                loginStage.initOwner(org.neo.main.Main.getStage());
                loginStage.initModality(Modality.WINDOW_MODAL);
                loginStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
