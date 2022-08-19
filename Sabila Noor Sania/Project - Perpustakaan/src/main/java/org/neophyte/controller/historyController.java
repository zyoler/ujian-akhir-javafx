package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.Pengembalian;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class historyController {
    public Button backButton;
    public TableView<Pengembalian> historyTable;
    public static TableView<Pengembalian> staticPengembalianTable;

    Stage stage = Main.getStage();

    public static void loadDataPengembalian() {
        staticPengembalianTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PENGEMBALIAN");
            while (resultSet.next()) {
                staticPengembalianTable.getItems().add(new Pengembalian(resultSet.getInt("no"),resultSet.getInt("nomor_peminjaman"), resultSet.getString("judul_buku"),resultSet.getString("nama"),resultSet.getString("tgl_pinjam"),resultSet.getString("tgl_kembali"),resultSet.getString("tgl_dikembalikan"),resultSet.getInt("denda_perhari"),resultSet.getInt("total_denda")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initialize() {
        staticPengembalianTable = historyTable;
        loadDataPengembalian();

        backButton.setOnAction(event -> {
            try{
                VBox vBox = FXMLLoader.load(getClass().getResource("/Pengembalian.fxml"));
                Scene scene = new Scene(vBox);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
