package org.neo.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neo.main.Main;
import org.neo.util.Database;

import java.sql.*;

public class ViewPesananController {
    public Label idBooking;
    public Label nama;
    public Label noHp;
    public Label tglBoooking;
    public Label tglMain;
    public Label jamMain;
    public Label lamaMain;
    public Label lapang;
    public Label jenisLapang;
    public Label total;
    public JFXButton konfirmasi;
    public JFXButton nanti;
    Connection conn = Database.connect();

    public void initialize(){
        try{
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select b.id_booking,b.nama_pemesan,b.no_hp,b.tgl_pesan,b.tgl_main,b.pukul,b.lama,l.namalpng,l.jenislpng,(l.harga*b.lama) as total from LAPANG l, BOOKING b where l.ID_LAPANG=b.ID_LAPANG order by ID_BOOKING desc limit 01");
            while(resultSet.next()){
                idBooking.setText(":            "+resultSet.getString(1));
                nama.setText(":            "+resultSet.getString(2));
                noHp.setText(":            "+resultSet.getString(3));
                tglBoooking.setText(":            "+resultSet.getString(4));
                tglMain.setText(":            "+resultSet.getString(5));
                jamMain.setText(":            "+resultSet.getString(6));
                lamaMain.setText(":            "+resultSet.getString(7));
                lapang.setText(":            "+resultSet.getString(8));
                jenisLapang.setText(":            "+resultSet.getString(9));
                total.setText(":            Rp. "+resultSet.getString(10));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        konfirmasi.setOnAction(event -> {
            ((Stage) konfirmasi.getScene().getWindow()).close();
            try {
                VBox vBox = FXMLLoader.load(getClass().getResource("/pages/pembayaran.fxml"));
                Scene scene = new Scene(vBox);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Pembayaran");
                stage.initOwner(Main.getStage());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        nanti.setOnAction(event -> {
            ((Stage) nanti.getScene().getWindow()).close();
        });
    }
}
