package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.neophyte.Main;

import java.io.IOException;

public class HalamanUtamaContoller {

    public Button beranda;
    public Button peminjaman;
    public Button Admin;
    public Button buku;
    //button gambar
    public Button admin;
    public Button anggota;
    public Button Buku;
    public Button Peminjaman;
    public Button pengembalian;
    public Button Logout;

    Stage stage = Main.getStage();
    public void initialize(){

        beranda.setOnAction(event -> {
            try {
                HBox hBox = FXMLLoader.load(getClass().getResource("/HalamanUtama.fxml"));
                Scene adminScene = new Scene(hBox);
                stage.setScene(adminScene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        buku.setOnAction(event -> {
            try {
                HBox hBox = FXMLLoader.load(getClass().getResource("/crud/CrudBuku.fxml"));
                Scene scene = new Scene(hBox);
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        peminjaman.setOnAction(event -> {
            try {
                VBox vBox = FXMLLoader.load(getClass().getResource("/crud/CrudPeminjaman.fxml"));
                Scene peminjamanscene = new Scene(vBox);
                stage.setScene(peminjamanscene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Admin.setOnAction(event -> {
            try{
                HBox hBox = FXMLLoader.load(getClass().getResource("/crud/CrudAdmin.fxml"));
                Scene scene = new Scene(hBox);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        admin.setOnAction(event -> {
            try{
                HBox hBox = FXMLLoader.load(getClass().getResource("/crud/CrudAdmin.fxml"));
                Scene adminScene = new Scene(hBox);
                stage.setScene(adminScene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        anggota.setOnAction(event -> {
            try{
                HBox hBox = FXMLLoader.load(getClass().getResource("/crud/CrudAnggota.fxml"));
                Scene adminScene = new Scene(hBox);
                stage.setScene(adminScene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Buku.setOnAction(event -> {
            try{
                HBox hBox = FXMLLoader.load(getClass().getResource("/crud/CrudBuku.fxml"));
                Scene adminScene = new Scene(hBox);
                stage.setScene(adminScene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Peminjaman.setOnAction(event -> {
            try{
                VBox vBox = FXMLLoader.load(getClass().getResource("/crud/CrudPeminjaman.fxml"));
                Scene adminScene = new Scene(vBox);
                stage.setScene(adminScene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        pengembalian.setOnAction(event -> {
            try{
                VBox vBox = FXMLLoader.load(getClass().getResource("/Pengembalian.fxml"));
                Scene adminScene = new Scene(vBox);
                stage.setScene(adminScene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Logout.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText(null);
            alert.setContentText("Apakah Anda yakin ingin Keluar ?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                try {
                    Main.refresh();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
