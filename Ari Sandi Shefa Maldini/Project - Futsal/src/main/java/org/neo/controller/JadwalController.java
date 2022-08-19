package org.neo.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neo.main.Main;
import org.neo.models.Jadwal;
import org.neo.util.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JadwalController {
    public static TableView<Jadwal> jdView;

    public TableView<Jadwal> jadwalView;
    public Button konfirButton;
    public ImageView img;

    public static void loadData(){
        jdView.getItems().setAll();
        try{
            Connection connection = Database.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT j.ID_JADWAL,b.NAMA_PEMESAN,b.NO_HP,b.TGL_MAIN,b.PUKUL,l.NAMALPNG,b.ID_BOOKING FROM JADWAL j, BOOKING b, LAPANG l where j.ID_BOOKING=b.ID_BOOKING and b.ID_LAPANG=l.ID_LAPANG and b.STATUS IS NOT NULL ");
            while(resultSet.next()){
                jdView.getItems().add(new Jadwal(
                        resultSet.getInt("id_jadwal"),
                        resultSet.getString("nama_pemesan"),
                        resultSet.getString("no_hp"),
                        resultSet.getString("tgl_main"),
                        resultSet.getString("pukul"),
                        resultSet.getString("namalpng"),
                        resultSet.getInt("id_booking")
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void initialize(){
        img.setImage(new Image("/image/jadwal.png"));
        jdView = jadwalView;
        loadData();

        konfirButton.setOnAction(event -> {
            try {
                VBox vBox = FXMLLoader.load(getClass().getResource("/pages/pembayaran.fxml"));
                Scene sceneBayar = new Scene(vBox);
                Stage stageBayar = new Stage();
                stageBayar.setScene(sceneBayar);
                stageBayar.setResizable(false);
                stageBayar.setTitle("Pembayaran");
                stageBayar.initOwner(Main.getStage());
                stageBayar.initModality(Modality.WINDOW_MODAL);
                stageBayar.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
