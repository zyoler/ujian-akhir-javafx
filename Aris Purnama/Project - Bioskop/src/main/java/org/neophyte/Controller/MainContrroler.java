package org.neophyte.Controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.Main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;


public class MainContrroler {

    public VBox content;
    public Label users;
    public Button btnBeranda;
    public HBox movie;
    public Button btnDataTransaksi;
    public Button btnLogut;
    public Button btnTentang;
    
    public Label dateNow;
    public Label jamNow;

    Stage mainStage = new Stage();
    public Button btnKelolaStudio;
    public Button btnDetailMovie;
    public Button btnPemesanan;

    private volatile boolean stop = false;
    Thread thread = new Thread(() -> {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        while(!stop){
            try{
                Thread.sleep(1000);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            final String timenow = sdf.format(new Date());
            Platform.runLater(() -> {
                jamNow.setText(timenow + " WIB");
            });
        }
    });
    private void timeNow(){
        thread.setDaemon(true);
        thread.start();
    }

    public void dateNow(){
        LocalDate date = LocalDate.now();
        dateNow.setText(String.valueOf(date));
    }

    void setPage(String nama) {
        VBox root = null;
        try {
            Main.skunderStage.setTitle(nama);
            root = FXMLLoader.load(getClass().getResource("/crud/" + nama + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content.getChildren().setAll(root);
    }

    void setMenuHome(String nama) {
        VBox root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/" + nama + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content.getChildren().setAll(root);
    }

    public void initialize() {
        dateNow();
        timeNow();
/////////////////////////////////////////////ON ACSEC //////////////////////////////////////
        //  if (Main.logginInadmin != null) { // sudah login
        if (Objects.equals(Main.cek, "admin")) {
            btnPemesanan.setDisable(false);
            btnDetailMovie.setDisable(false);
            btnDataTransaksi.setDisable(false);
            btnKelolaStudio.setDisable(false);
            users.setText(Main.logginInadmin.getNamaUser());
        } else { // belum login
            btnPemesanan.setDisable(false);
            btnDetailMovie.setDisable(false);
            btnDataTransaksi.setDisable(false);
            btnKelolaStudio.setDisable(true);
            users.setText(Main.logginInadmin.getNamaUser());
        }
        setMenuHome("beranda");
///////////////////////// ONCLIK //////////////////////////////////////////
        btnKelolaStudio.setOnAction(event -> {
            setPage("index");
        });
        btnBeranda.setOnAction(event -> {
            setMenuHome("beranda");
        });
        btnDetailMovie.setOnAction(event -> {
            setMenuHome("jadwalTayang");
        });
        btnPemesanan.setOnAction(event -> {
//            setMenuHome("pemesanan");
            Parent root = null;
            Stage pemesananStage = new Stage();
            try {
                root = FXMLLoader.load(getClass().getResource("/view/pemesanan.fxml"));
                Scene pesanScene = new Scene(root);
                pemesananStage.setScene(pesanScene);
                pemesananStage.setTitle("Form Pemesanan");
                pemesananStage.initOwner(LoginController.stage);
                pemesananStage.initModality(Modality.APPLICATION_MODAL);
                pemesananStage.setResizable(false);
                pemesananStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnDataTransaksi.setOnAction(event -> {
            setMenuHome("dataTransaksi");
        });
        btnLogut.setOnAction(event -> {
            Parent root = null;
            Stage pemesananStage = new Stage();
            try {
                root = FXMLLoader.load(getClass().getResource("/login.fxml"));
                Scene pesanScene = new Scene(root);
                pemesananStage.setScene(pesanScene);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Ingin Logout ?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK)
                    LoginController.getStage().close();
                pemesananStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnTentang.setOnAction(event -> {
            Parent root = null;
            Stage pemesananStage = new Stage();
            try {
                root = FXMLLoader.load(getClass().getResource("/view/tentang.fxml"));
                Scene pesanScene = new Scene(root);
                pemesananStage.setScene(pesanScene);
                pemesananStage.initOwner(LoginController.stage);
                pemesananStage.initModality(Modality.APPLICATION_MODAL);
                pemesananStage.setResizable(false);
                pemesananStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
