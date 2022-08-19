package org.rental.controller;


import org.rental.Main;
import org.rental.model.Mobil;

import javafx.scene.control.*;
import org.rental.util.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReportMobilController {

    public static TableView<Mobil> staticMobilView;
    public TableView<Mobil> mobiltable;
    public Button kembaliButton;


    public static void loadDAta() {
        staticMobilView.getItems().setAll();
        try {

            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from mobil");
            while (resultSet.next()) {

                staticMobilView.getItems().add(new Mobil(resultSet.getString("id_mobil"), resultSet.getString("merk_mobil"), resultSet.getString("no_polisi"), resultSet.getInt("harga_sewa"), resultSet.getString("tipe_mobil"), resultSet.getString("tahun_pembuatan"), resultSet.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        staticMobilView = mobiltable;
        loadDAta();

        kembaliButton.setOnAction(event -> {
            Main.refresh();
        });
    }
}
