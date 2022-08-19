package org.rental.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.rental.Main;
import org.rental.model.Mobil;
import org.rental.model.Peminjam;
import org.rental.util.Database;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import static org.rental.Main.stage;

public class CrudPeminjamController {
    public static TableView<Mobil> staticMobilView;
    public TableView<Mobil> tableMobil;
    public Button kembaliButton;
    public TextField transaksi;
    public TextField nama;
    public TextField alamat;
    public TextField notelp;
    public TextField email;
    public TextField id_mobil;
    public DatePicker tglpinjamDatePicker;
    public DatePicker tglkembaliDatePicker;
    public Button simpanButton;
    public Button bersihkanButton;
    public Button lihatButton;
    public int tamp=0;
    public TextField harga;
    Peminjam peminjam;
    Mobil mobil;


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

    public void initialize() {
        notelp.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                notelp.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        staticMobilView = tableMobil;
        loadDAta();

        harga.setEditable(false);
        id_mobil.setEditable(false);


        tableMobil.setOnMouseClicked(event -> {
            Mobil selectedMobil = tableMobil.getSelectionModel().getSelectedItem();
            id_mobil.setText(selectedMobil.getId_mobil());
            int hasil = selectedMobil.getHarga_sewa();
            harga.setText(String.valueOf(hasil));
        });

        kembaliButton.setOnAction(event -> {
            Main.refresh();
        });

        bersihkanButton.setOnAction(event -> {
            transaksi.setText("");
            nama.setText("");
            alamat.setText("");
            notelp.setText("");
            email.setText("");
            tglpinjamDatePicker.setValue(null);
            tglkembaliDatePicker.setValue(null);
            harga.setText("");
            id_mobil.setText("");
        });

        lihatButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/lihatPeminjam.fxml"));
                Scene scene = new Scene(root, 700, 500);
                stage.setScene(scene);
                stage.setTitle("Rental Mobil");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        simpanButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            notelp.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    notelp.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
            if (Objects.equals(transaksi.getText(), "")) {
                alert.setContentText("ID Transaksi belum diisi");
                alert.show();
            } else if (Objects.equals(nama.getText(), "")) {
                alert.setContentText("Nama Peminjam belum diisi");
                alert.show();
            } else if (Objects.equals(alamat.getText(), "")) {
                alert.setContentText("alamat belum diisi");
                alert.show();
            } else if (Objects.equals(notelp.getText(), "")) {
                alert.setContentText("No telp belum diisi");
                alert.show();
            } else if (Objects.equals(email.getText(), "")) {
                alert.setContentText("email belum diisi");
                alert.show();
            } else if (Objects.equals(tglpinjamDatePicker.getValue(), "")) {
                alert.setContentText("Tanggal belum diisi");
                alert.show();
            } else if (Objects.equals(tglkembaliDatePicker.getValue(), "")) {
                alert.setContentText("Tanggal belum diisi");
                alert.show();
            } else {
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = null;
                    if (peminjam == null) {
                            preparedStatement = connection.prepareStatement("INSERT INTO peminjaman(id_transaksi,nama_peminjam,alamat,no_telp,email,tanggal_pinjam,tanggal_kembali,lama_pinjam,harga,id_mobil) VALUES(?,?,?,?,?,?,?,?,?,?)");
                    } else {
                        preparedStatement = connection.prepareStatement("UPDATE peminjaman SET id_transaksi = ? ,nama_peminjam = ?,alamat=?,no_telp=?,email = ?,tanggal_pinjam=?,tanggal_kembali=?,lama_pinjam=?,harga=?,id_mobil=? WHERE id_transaksi=?");
                        preparedStatement.setString(11, peminjam.getId_mobil());
                    }
                    preparedStatement.setString(1, transaksi.getText());
                    preparedStatement.setString(2, nama.getText());
                    preparedStatement.setString(3, alamat.getText());
                    preparedStatement.setString(4, notelp.getText());
                    preparedStatement.setString(5, email.getText());
                    LocalDate tglpinjam = tglpinjamDatePicker.getValue();
                    preparedStatement.setString(6, tglpinjam.toString());
                    LocalDate tglkembali = tglkembaliDatePicker.getValue();
                    preparedStatement.setString(7, tglkembali.toString());
                    int lama = Integer.parseInt(String.valueOf(tglpinjam.getDayOfMonth()));
                    int lama1 = Integer.parseInt(String.valueOf(tglkembali.getDayOfMonth()));
                    int hasil = lama1 - lama;
                    preparedStatement.setString(8, String.valueOf(hasil));
                    preparedStatement.setString(9,harga.getText());
                    preparedStatement.setString(10, id_mobil.getText());
                    preparedStatement.executeUpdate();
                    preparedStatement = connection.prepareStatement("update mobil set status = 'kosong' where id_mobil = ?");
                    preparedStatement.setString(1,id_mobil.getText());

                    preparedStatement.executeUpdate();
                    alert.setContentText("Data Berhasil disimpan !!!");
                    alert.show();
                } catch (Exception e) {
                    Alert a =new Alert(Alert.AlertType.ERROR);
                    a.setContentText("id Transaksi sudah ada");
                    a.show();
                }
            }
        });

    }

}
