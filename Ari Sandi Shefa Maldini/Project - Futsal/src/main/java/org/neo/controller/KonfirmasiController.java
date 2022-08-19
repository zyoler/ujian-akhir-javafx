package org.neo.controller;

import com.jfoenix.controls.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neo.main.Main;
import org.neo.models.Booking;
import org.neo.util.Database;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class KonfirmasiController {
    public JFXTextField txIDPesanan;
    public JFXTextField txNmaPengirim;
    public JFXComboBox<String> bankTujuan;
    public JFXDatePicker tgl;
    public JFXTimePicker jam;
    public JFXButton btnKonfir;
    public JFXButton btnCari;
    public JFXComboBox<String> cmbBankPengirim;
    public JFXTextField noHP;
    public JFXButton buttonRek;
    int maxLength = 13;
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.now();
    Connection conn = Database.connect();

    public void initialize(){

        txIDPesanan.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txIDPesanan.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txNmaPengirim.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txNmaPengirim.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        noHP.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                noHP.setText(newValue.replaceAll("[^\\d]", ""));
            }else if (noHP.getText().length() > maxLength) {
                String s = noHP.getText().substring(0, maxLength);
                noHP.setText(s);
            }
        });

        bankTujuan.getItems().addAll(
                "BRI","BCA","Mandiri"
        );

        cmbBankPengirim.getItems().addAll(
                "BRI","BCA","Mandiri"
        );

        btnCari.setOnAction(event -> {
            VBox vBox = null;
            try{
                 vBox = FXMLLoader.load(getClass().getResource("/datareservasi.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert vBox != null;
            Scene sceneLapang = new Scene(vBox);
            Stage stageData = new Stage();
            stageData.setScene(sceneLapang);
            stageData.setResizable(false);
            stageData.setTitle("Cari Reservasi");
            stageData.initOwner(Main.getStage());
            stageData.initModality(Modality.WINDOW_MODAL);
            stageData.show();
        });

        buttonRek.setOnAction(event -> {
            VBox vBox = null;
            try {
                vBox = FXMLLoader.load(getClass().getResource("/rekening.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert vBox != null;
            Scene scene = new Scene(vBox);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("No Rekening");
            stage.initOwner(Main.getStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        });

        btnKonfir.setOnAction(event -> {
            if(txIDPesanan.getText().trim().isEmpty() || txNmaPengirim.getText().trim().isEmpty() || bankTujuan.getSelectionModel().isEmpty() || jam.getValue() == null || tgl.getValue() == null || cmbBankPengirim.getSelectionModel().isEmpty() || noHP.getText().trim().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning!!");
                alert.setHeaderText(null);
                alert.setContentText("Harap isi data terlebih dahulu!");
                alert.show();
            }else if(noHP.getText().charAt(0) != '0' || noHP.getText().charAt(1) != '8'){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning!!");
                alert.setHeaderText(null);
                alert.setContentText("Isi no handphone yang sesuai!!");
                alert.show();
                noHP.isFocused();
            }else{
                int x=0;
                try{
                    Connection connection = Database.connect();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM BOOKING where STATUS is null");
                    while(resultSet.next()){
                       if(resultSet.getInt("id_booking") == Integer.parseInt(txIDPesanan.getText())){
                           x=1;
                       }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if(x==1){
                    LocalDate localDate = tgl.getValue();
                    LocalTime localTime = jam.getValue();
                    try {
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO KONFIRMASI (ID_BOOKING, NAMA_PENGIRIM, BANK_PENGIRIM, BANK_TUJUAN, TANGGAL, PUKUL, NO_HP) VALUES (?,?,?,?,?,?,?)");
                        ps.setString(1,txIDPesanan.getText());
                        ps.setString(2,txNmaPengirim.getText());
                        ps.setString(3,cmbBankPengirim.getValue());
                        ps.setString(4,bankTujuan.getValue());
                        ps.setString(5,localDate.toString());
                        ps.setString(6,localTime.toString());
                        ps.setString(7,noHP.getText());
                        ps.executeUpdate();
                        ps = conn.prepareStatement("INSERT INTO JADWAL (ID_BOOKING) VALUES ( ? )");
                        ps.setString(1,txIDPesanan.getText());
                        ps.executeUpdate();
                        ps = conn.prepareStatement("UPDATE BOOKING SET STATUS = 'Terkonfir' WHERE ID_BOOKING=?");
                        ps.setString(1,txIDPesanan.getText());
                        ps.executeUpdate();
                        Database.disconnect();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Informasi!");
                        alert.setHeaderText(null);
                        alert.setContentText("Berhasil mengkonfirmasi booking!");
                        alert.show();
                        JadwalController.loadData();
                        ((Stage) btnKonfir.getScene().getWindow()).close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning!!");
                    alert.setHeaderText(null);
                    alert.setContentText("ID Booking tidak ditemukan!!");
                    alert.show();
                }
            }
        });


    }
}
