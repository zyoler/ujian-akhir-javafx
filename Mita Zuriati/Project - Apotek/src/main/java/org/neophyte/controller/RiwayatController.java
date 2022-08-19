package org.neophyte.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.Pembelian;
import org.neophyte.model.Penjualan;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RiwayatController {
    public static TableView<Penjualan> staticPenjTable;
    public TableView<Penjualan> penjualanTable;
    public Button hapusPenjButton;
    public Button kembaliPenjButton;

    Penjualan selectedPenjualan;

    public static TableView<Pembelian> staticPembTable;
    public TableView<Pembelian> pembelianTable;
    public Button hapusPembButton;
    public Button kembaliPembButton;

    Pembelian selectedPembelian;

    public static void loadDataPembelian() {
        staticPembTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pembelian ");
            while (resultSet.next()) {
                staticPembTable.getItems().add(new Pembelian(resultSet.getInt("id"),resultSet.getString("idPembelian"),resultSet.getString("tanggalPembelian"),resultSet.getString("idSupplier"), resultSet.getString("idObat"), resultSet.getInt("qty"), resultSet.getInt("total")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loadDataPenjualan() {
        staticPenjTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM penjualan ");
            while (resultSet.next()) {
                staticPenjTable.getItems().add(new Penjualan(resultSet.getInt("id"),resultSet.getString("idPenjualan"),resultSet.getString("tanggalPenjualan"), resultSet.getString("idObat"), resultSet.getInt("qty"), resultSet.getInt("total")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void forPenjualan(){
        staticPenjTable = penjualanTable;
        loadDataPenjualan();

        hapusPenjButton.setOnAction(event -> {
            selectedPenjualan = penjualanTable.getSelectionModel().getSelectedItem();
            System.out.println("penjualan bisa");

            if (selectedPenjualan != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus data Penjualan");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus data " + selectedPenjualan.getIdPenjualan() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PENJUALAN WHERE ID = ?");
                        //int tamp=(rs.getInt(""));
                        //System.out.println(id);
                        preparedStatement.setInt(1, selectedPenjualan.getId());
                        preparedStatement.executeUpdate();
                        loadDataPenjualan();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Pilih yang ingin di hapus");
                alert.show();
            }
        });
        kembaliPenjButton.setOnAction(event -> {
            ((Stage) kembaliPenjButton.getScene().getWindow()).close();
            Main.refresh();
        });
    }
    public void forPembelian(){
        staticPembTable = pembelianTable;
        loadDataPembelian();

        hapusPembButton.setOnAction(event -> {
            selectedPembelian = pembelianTable.getSelectionModel().getSelectedItem();

            if (selectedPembelian != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus data Pembelian");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus data" + selectedPembelian.getIdPembelian() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PEMBELIAN WHERE ID = ?");
                        preparedStatement.setInt(1, selectedPembelian.getId());
                        preparedStatement.executeUpdate();
                        loadDataPembelian();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Pilih yang ingin di hapus");
                alert.show();
            }
        });
        kembaliPembButton.setOnAction(event -> {
            ((Stage) kembaliPembButton.getScene().getWindow()).close();
            Main.refresh();
        });
    }
}
