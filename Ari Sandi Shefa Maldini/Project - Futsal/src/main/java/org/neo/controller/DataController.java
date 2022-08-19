package org.neo.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.neo.models.Booking;
import org.neo.util.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataController {
    public TextField cari;
    public TableView<Booking> viewBooking;
    public static TableView<Booking> viewBooking2;
    public Button t;

    public void initialize(){
        viewBooking2 = viewBooking;
        loadData("");
        cari.textProperty().addListener((observable, oldValue, newValue) -> loadData(newValue));
    }

    public static void loadData(String data){
        viewBooking2.getItems().setAll();
        try{
            Connection connection = Database.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM BOOKING where NAMA_PEMESAN like '%"+data+"%' and STATUS is null");
            while(resultSet.next()){
                viewBooking2.getItems().add(new Booking(resultSet.getInt("id_booking"),resultSet.getString("nama_pemesan"),resultSet.getString("no_hp"),resultSet.getString("tgl_pesan"),resultSet.getString("tgl_main"),resultSet.getInt("lama"),resultSet.getString("pukul"),resultSet.getInt("total"),resultSet.getInt("id_lapang")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
