package org.neophyte.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.neophyte.Main;

import java.io.IOException;

public class tentangController {
    public Button BtnBack;

    public void initialize(){
        BtnBack.setOnAction(event -> {
                ((Stage) BtnBack.getScene().getWindow()).close();

        });
    }
}
