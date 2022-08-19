package org.rental.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.rental.Main;
import org.rental.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class tentangController {
    public Button kembaliButton;
    public Button keluarButton;

    public void initialize(){
        kembaliButton.setOnAction(event -> {
            Main.refresh();
        });


    }
}
