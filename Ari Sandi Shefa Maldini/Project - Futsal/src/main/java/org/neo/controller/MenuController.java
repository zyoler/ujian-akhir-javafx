package org.neo.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import org.neo.main.Main;


public class MenuController {
    public Button berandaButton;
    public Button bookingButton;
    public Button tentangButton;
    public Button adminButton;
    public Button lapangButton;
    public Button jadwalButton;
    public VBox konten;
    public Button logoutButton;
    VBox root = null;

    void setPage(String page){
        try{
            root = FXMLLoader.load(getClass().getResource("/pages/"+page+".fxml/"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        konten.getChildren().setAll(root);
    }
    public void initialize(){
        if(Main.admLogged != null){
            bookingButton.setText("Admin");
            lapangButton.setText("Tentang");
            bookingButton.setOnAction(event -> {
                setPage("adminPanel");
            });
            lapangButton.setOnAction(event -> {
                setPage("tentang");
            });
            adminButton.setVisible(false);
            logoutButton.setVisible(true);
            tentangButton.setVisible(false);
            jadwalButton.setVisible(false);
        }else{
            bookingButton.setOnAction(event -> {
                setPage("reservasi");
            });

            adminButton.setOnAction(event -> {
                setPage("admin");
            });

            jadwalButton.setOnAction(event -> {
                setPage("jadwal");
            });

            lapangButton.setOnAction(event -> {
                setPage("lapang");
            });

            logoutButton.setVisible(false);
        }

        try{
            root = FXMLLoader.load(getClass().getResource("/pages/beranda.fxml/"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        konten.getChildren().setAll(root);

        berandaButton.setOnAction(event ->{
            setPage("beranda");
        });

        tentangButton.setOnAction(event -> {
            setPage("tentang");
        });

        logoutButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi");
            alert.setHeaderText(null);
            alert.setContentText("Yakin untuk logout?");
            ButtonType result = alert.showAndWait().get();
            if (result.getText().equals("OK")) {
                Main.admLogged = null;
                Main.refresh();
            }
        });
    }
}
