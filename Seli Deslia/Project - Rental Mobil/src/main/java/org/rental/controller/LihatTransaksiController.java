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
import org.rental.model.Transaksi;
import org.rental.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.rental.Main.stage;

public class LihatTransaksiController {
    public Button kembaliButton;
    public Button hapusButton;
    public TableView<Transaksi> tableTransaksi;
    public static TableView<Transaksi> staticTransaksiTable;


    public static void loadDAta() {
        staticTransaksiTable.getItems().setAll();
        try {

            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from transaksi");
            while (resultSet.next()) {
                staticTransaksiTable.getItems().add(new Transaksi(resultSet.getString("id_transaksi"), resultSet.getString("nama_peminjam"), resultSet.getString("alamat"), resultSet.getString("tanggal_pinjam"), resultSet.getString("tanggal_kembali"), resultSet.getInt("lama_pinjam"), resultSet.getInt("total"),resultSet.getInt("denda"),resultSet.getString("id_mobil")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        staticTransaksiTable = tableTransaksi;
        loadDAta();

        hapusButton.setOnAction(event -> {
            Transaksi selectedMobil = tableTransaksi.getSelectionModel().getSelectedItem();

            if(selectedMobil != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus Transaksi");
                alert.setHeaderText(null);
                alert.setContentText("Apakah anda ingin menghapus transaksi dengan id " + selectedMobil.getId_transaksi() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM transaksi where id_transaksi=?");
                        preparedStatement.setString(1, selectedMobil.getId_transaksi());
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
                Parent root = FXMLLoader.load(getClass().getResource("/pengembalianMobil.fxml"));
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
