package org.rental.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.rental.Main;
import org.rental.model.Admin;
import org.rental.model.HistoryPeminjaman;
import org.rental.model.Mobil;
import org.rental.model.Transaksi;
import org.rental.util.Database;

import java.sql.*;
import java.util.Objects;

import static org.rental.Main.stage;

public class BayarController {
    public static TableView<Transaksi> staticPembayaranTable;
    public TableView<Transaksi> tablePembayaran;
    public TextField idTransaksiField;
    public TextField totalBayarField;
    public TextField inputUangField;
    public TextField kembalianField;
    public Button hitungButton;
    public String uang;
    public int totalBayar;
    public String total;
    public Button cetakButton;
    public Button kembaliButton;
    public static String id;
    public static String idm;
    public void loadData(){
        staticPembayaranTable.getItems().setAll();
        try {
            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from transaksi");
            while (resultSet.next()) {
                staticPembayaranTable.getItems().add(new Transaksi(resultSet.getString("id_transaksi"), resultSet.getString("nama_peminjam"), resultSet.getString("alamat"), resultSet.getString("tanggal_pinjam"), resultSet.getString("tanggal_kembali"), resultSet.getInt("lama_pinjam"), resultSet.getInt("total"),resultSet.getInt("denda"),resultSet.getString("id_mobil")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        inputUangField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputUangField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        idTransaksiField.setEditable(false);
        staticPembayaranTable = tablePembayaran;
        loadData();
        kembalianField.setEditable(false);
        tablePembayaran.setOnMouseClicked(event -> {
            Transaksi selectedTransaksi = tablePembayaran.getSelectionModel().getSelectedItem();
            idTransaksiField.setText(selectedTransaksi.getId_transaksi());
            id=idTransaksiField.getText();
            idm = selectedTransaksi.getId_mobil();
            int total = selectedTransaksi.getTotal();
            totalBayarField.setText(String.valueOf(total));
        });

        hitungButton.setOnAction(event -> {
            Transaksi selectedHistory = tablePembayaran.getSelectionModel().getSelectedItem();
            if(selectedHistory == null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("id transaksi belum diisi");
                alert.showAndWait();
            }else {

                uang = inputUangField.getText();
                total = totalBayarField.getText();
                totalBayar = Integer.parseInt(uang) - Integer.parseInt(total);


                if (totalBayar < 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Uang yang anda masukan kurang");
                    alert.show();
                } else {
                    kembalianField.setText(String.valueOf(totalBayar));

                }
            }


        });

        kembaliButton.setOnAction(event -> {
            Main.refresh();
        });

        cetakButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("/nota.fxml"));
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.setTitle("Rental Mobil");
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }


}
