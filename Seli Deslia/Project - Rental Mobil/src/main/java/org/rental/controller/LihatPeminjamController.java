package org.rental.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import org.rental.model.Mobil;
import org.rental.model.Peminjam;
import org.rental.util.Database;
import org.rental.model.Peminjam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

import static org.rental.Main.stage;

public class LihatPeminjamController {
    public TableView peminjamTable;
    public Button hapusButton;
    public Button kembaliButton;
    Peminjam peminjam;
    public static TableView<Peminjam> staticPeminjamView;


    public static void loadDAta() {
        staticPeminjamView.getItems().setAll();
        try {

            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from peminjaman");
            while (resultSet.next()) {
                staticPeminjamView.getItems().add(new Peminjam(resultSet.getString("id_transaksi"), resultSet.getString("nama_peminjam"), resultSet.getString("alamat"), resultSet.getString("no_telp"), resultSet.getString("email"), resultSet.getString("tanggal_pinjam"), resultSet.getString("tanggal_kembali"),resultSet.getInt("lama_pinjam"),resultSet.getInt("harga"),resultSet.getString("id_mobil")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){

        staticPeminjamView = peminjamTable;
        loadDAta();

        hapusButton.setOnAction(event -> {
            Peminjam selectedPeminjam = (Peminjam) peminjamTable.getSelectionModel().getSelectedItem();

            if(selectedPeminjam != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus Mobil");
                alert.setHeaderText(null);
                alert.setContentText("Apakah anda ingin menghapus mobil dengan no polisi" + selectedPeminjam.getId_transaksi() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM peminjaman where id_transaksi=?");
                        preparedStatement.setString(1, selectedPeminjam.getId_transaksi());
                        preparedStatement.executeUpdate();
                        loadDAta();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        kembaliButton.setOnAction(event -> {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/crudPeminjaman.fxml"));
                Scene scene = new Scene(root,700,500);
                stage.setScene(scene);
                stage.setTitle("Rental Mobil");
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
