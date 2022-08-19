package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    public Button pegawaiButton;
    public Button pelangganButton;
    public Button barangButton;
    public Button pesananButton;
    public Button prosesButton;
    public Button selesaiButton;
    public Button quitButton;
    public AnchorPane pane;



    public void setPages(String x) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/pages/mainpages/" + x + ".fxml"));
        }catch (IOException e){
            e.printStackTrace();
        }
        pane.getChildren().setAll(root);
    }

    public void initialize() {
        pegawaiButton.setOnAction(event -> {
            setPages("data_pegawai");
        });
        pelangganButton.setOnAction(event -> {
            setPages("data_pelanggan");
        });
        barangButton.setOnAction(event -> {
            setPages("data_barang");
        });
        pesananButton.setOnAction(event -> {
            setPages("cucian_masuk");
        });
        prosesButton.setOnAction(event -> {
            setPages("proses_cucian");
        });
        selesaiButton.setOnAction(event -> {
            setPages("cucian_keluar");
        });
        quitButton.setOnAction(event -> {
            ((Stage) quitButton.getScene().getWindow()).close();
        });

    }
}
