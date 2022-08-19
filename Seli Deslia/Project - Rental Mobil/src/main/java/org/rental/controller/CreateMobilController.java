package org.rental.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.rental.model.Admin;
import org.rental.model.Mobil;
import org.rental.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class CreateMobilController {

    public TextField mobilId;
    public TextField merkMobil;
    public TextField noPolisi;
    public TextField hargaSewa;
    public TextField tipeMobil;
    public TextField tahunPembuatan;
    public TextField status;
    public Button saveButton;
    public Button cancelButton;
    Mobil mobil;


    public void close(){
        ((Stage) merkMobil.getScene().getWindow()).close();
    }

    public void initialize() {
        saveButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            if (Objects.equals(mobilId.getText(), "")) {
                alert.setContentText("ID Mobil belum diisi");
                alert.show();
            } else if (Objects.equals(merkMobil.getText(), "")) {
                alert.setContentText("Merk Mobil belum diisi");
                alert.show();
            } else if (Objects.equals(noPolisi.getText(), "")) {
                alert.setContentText("No Polisi belum diisi");
                alert.show();
            } else if (Objects.equals(hargaSewa.getText(), "")) {
                alert.setContentText("Harga Sewa belum diisi");
                alert.show();
            } else if (Objects.equals(tipeMobil.getText(), "")) {
                alert.setContentText("Tipe Mobil belum diisi");
                alert.show();
            } else if (Objects.equals(tahunPembuatan.getText(), "")) {
                alert.setContentText("Tahun pembuatan belum diisi");
                alert.show();
            } else if (Objects.equals(status.getText(), "")) {
                alert.setContentText("Status belum diisi");
                alert.show();
            } else if (merkMobil.getText().matches(".*\\d.*")) {
                alert.setContentText("Merk Mobil tidak boleh berisi angka");
                alert.show();
            }  else if (tipeMobil.getText().matches(".*\\d.*")) {
                alert.setContentText("Tipe Mobil tidak boleh berisi angka");
                alert.show();
            } else if (status.getText().matches(".*\\d.*")) {
                alert.setContentText("Status tidak boleh berisi angka");
                alert.show();
            } else {
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement;
                    if (mobil == null) {
                        preparedStatement = connection.prepareStatement("INSERT INTO mobil(id_mobil,merk_mobil,no_polisi,harga_sewa,tipe_mobil,tahun_pembuatan,status) VALUES(?,?,?,?,?,?,?)");

                    } else {
                        preparedStatement = connection.prepareStatement("UPDATE mobil SET id_mobil = ? ,merk_mobil = ?,no_polisi = ? ,harga_sewa=?,tipe_mobil=?,tahun_pembuatan=?,status = ? WHERE id_mobil=?");
                        preparedStatement.setString(8, mobil.getId_mobil());
                    }
                    preparedStatement.setString(1, mobilId.getText());
                    preparedStatement.setString(2, merkMobil.getText());
                    preparedStatement.setString(3, noPolisi.getText());
                    preparedStatement.setString(4, hargaSewa.getText());
                    preparedStatement.setString(5, tipeMobil.getText());
                    preparedStatement.setString(6, tahunPembuatan.getText());
                    preparedStatement.setString(7, status.getText());
                    preparedStatement.executeUpdate();
                    CrudMobilController.loadDAta();
                    ((Stage) merkMobil.getScene().getWindow()).close();
                } catch (Exception e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText(null);
                    a.setContentText("Id yang anda masukan sudah ada");
                }
            }
        });

        cancelButton.setOnAction(event -> {
            close();
        });

    }

    public void fillForm(Mobil mobil){
        this.mobil = mobil;
        mobilId.setText(mobil.getId_mobil());
        merkMobil.setText(mobil.getMerk_mobil());
        noPolisi.setText(mobil.getNo_polisi());
        hargaSewa.setText(Integer.toString(mobil.getHarga_sewa()));
        tipeMobil.setText(mobil.getTipe_mobil());
        tahunPembuatan.setText(mobil.getTahun_pembuatan());
        status.setText(mobil.getStatus());

    }

}
