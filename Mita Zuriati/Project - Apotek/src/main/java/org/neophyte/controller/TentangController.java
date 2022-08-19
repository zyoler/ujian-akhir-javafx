package org.neophyte.controller;


import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.neophyte.Main;


public class TentangController {
    public Button kembaliButton;
    public void initialize(){
        kembaliButton.setOnAction(event -> {
            ((Stage) kembaliButton.getScene().getWindow()).close();
            Main.refresh();
        });
    }
}
