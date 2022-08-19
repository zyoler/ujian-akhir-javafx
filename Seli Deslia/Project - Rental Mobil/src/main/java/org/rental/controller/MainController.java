package org.rental.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.rental.Main;

import java.io.IOException;
import java.util.Objects;

import static org.rental.Main.stage;

public class MainController {
    public Button homeButton;
    public Button adminButton;
    public Button mobilButton;
    public Button peminjamanButton;
    public Button logOutButton;
    public Button reportMobilButton;
    public Button pengembalianButton;
    public Button tentangButtom;
    public Button pembayaranButton;
    public Button HistoryButton;


    public void initialize(){

        homeButton.setOnAction(event -> {
           Main.refresh();
        });

        adminButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("/crudAdmin.fxml"));
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.setTitle("Rental Mobil");
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        reportMobilButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("/reportMobil.fxml"));
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.setTitle("Rental Mobil");
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        HistoryButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("/HistoryPeminjaman.fxml"));
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.setTitle("Rental Mobil");
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        pembayaranButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("/bayar.fxml"));
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.setTitle("Rental Mobil");
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        tentangButtom.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("/tentang.fxml"));
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.setTitle("Rental Mobil");
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        pengembalianButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("/pengembalianMobil.fxml"));
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.setTitle("Rental Mobil");
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        mobilButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("/crudMobil.fxml"));
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.setTitle("Rental Mobil");
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        peminjamanButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("/crudPeminjaman.fxml"));
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.setTitle("Rental Mobil");
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

            logOutButton.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Keluar");
                alert.setHeaderText(null);
                alert.setContentText("Apakah anda ingin keluar dari aplikasi Rental Mobil?");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    Platform.exit();
                }
            });
    }
}
