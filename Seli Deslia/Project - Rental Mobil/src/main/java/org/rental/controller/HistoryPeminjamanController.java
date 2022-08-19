package org.rental.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import org.rental.Main;
import org.rental.model.HistoryPeminjaman;
import org.rental.model.Transaksi;
import org.rental.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.rental.Main.stage;

public class HistoryPeminjamanController {
    public Button kembaliButton;
    public Button hapusButton;
    public TableView<HistoryPeminjaman> tableHistory;
    public static TableView<HistoryPeminjaman> staticHistoryTable;


    public static void loadDAta() {
        staticHistoryTable.getItems().setAll();
        try {

            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from history_peminjaman");
            while (resultSet.next()) {
                staticHistoryTable.getItems().add(new HistoryPeminjaman(resultSet.getString("id_transaksi"), resultSet.getString("nama_peminjam"), resultSet.getString("alamat"), resultSet.getString("tanggal_pinjam"), resultSet.getString("tanggal_kembali"), resultSet.getInt("lama_pinjam"), resultSet.getInt("total"),resultSet.getInt("denda"),resultSet.getString("id_mobil")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        staticHistoryTable = tableHistory;
        loadDAta();

        hapusButton.setOnAction(event -> {
            HistoryPeminjaman selectedHistory = tableHistory.getSelectionModel().getSelectedItem();

            if(selectedHistory != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus Transaksi");
                alert.setHeaderText(null);
                alert.setContentText("Apakah anda ingin menghapus transaksi dengan id " + selectedHistory.getId_transaksi() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM history_peminjaman where id_transaksi=?");
                        preparedStatement.setString(1, selectedHistory.getId_transaksi());
                        preparedStatement.executeUpdate();
                        loadDAta();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        kembaliButton.setOnAction(event -> {
            Main.refresh();
        });
    }
}
