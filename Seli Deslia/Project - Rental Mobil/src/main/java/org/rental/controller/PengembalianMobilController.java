package org.rental.controller;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.rental.Main;
import org.rental.model.Mobil;
import org.rental.model.Transaksi;
import org.rental.util.Database;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static org.rental.Main.stage;

public class PengembalianMobilController<HistoryPeminjaman> {

    public DatePicker tanggal_pinjam;
    public DatePicker tanggal_kembali;
    public TextField denda;
    public ComboBox id_transaksi;
    public TextField nama_peminjam;
    public TextField alamat;
    public TextField no_telp;
    public TextField email;
    public TextField id_mobil;
    public TextField merk_mobil;
    public TextField no_polisi;
    public TextField harga_sewa;
    public TextField tipe_mobil;
    public TextField tahun_pembuatan;
    public TextField total;
     public TextField idMobil;
    public Button hitung;
    public Button simpannButton;
    public Button kembaliButton;
    public Button lihatTableButton;
    public Button bersihkanButton;
    public Button bayarButton;
    Transaksi transaksi;
    Connection connection = Database.getConnection();
    public Integer lama=0;
    public Integer harga=0;
    HistoryPeminjaman history;


    public void initialize() {
        ArrayList<String> isi = null;
        try {
            isi = getId_Transaksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int a = 0; a < isi.size(); a++) {
            id_transaksi.getItems().add(isi.get(a));
        }


        nama_peminjam.setEditable(false);
        tanggal_pinjam.setEditable(false);
        id_mobil.setEditable(false);
        email.setEditable(false);
        alamat.setEditable(false);
        no_telp.setEditable(false);
        tahun_pembuatan.setEditable(false);
        tipe_mobil.setEditable(false);
        harga_sewa.setEditable(false);
        no_polisi.setEditable(false);
        merk_mobil.setEditable(false);
        idMobil.setEditable(false);

   lihatTableButton.setOnAction(event -> {
       try {
           Parent root = FXMLLoader.load(getClass().getResource("/lihatTransaksi.fxml"));
           Scene scene = new Scene(root, 700, 500);
           stage.setScene(scene);
           stage.setTitle("Rental Mobil");
           stage.show();
       } catch (Exception e) {
           e.printStackTrace();
       }
   });

        bersihkanButton.setOnAction(event -> {
            nama_peminjam.setText("");
            alamat.setText("");
            no_telp.setText("");
            email.setText("");
            id_mobil.setText("");
            idMobil.setText("");
            merk_mobil.setText("");
            no_polisi.setText("");
            harga_sewa.setText("");
            tipe_mobil.setText("");
            tahun_pembuatan.setText("");
            tanggal_pinjam.setValue(null);
            tanggal_kembali.setValue(null);
            denda.setText("");
            total.setText("");
        });

    kembaliButton.setOnAction(event -> {
        Main.refresh();
    });

    tanggal_kembali.setOnAction(event -> {

        LocalDate tglPinjam = tanggal_pinjam.getValue();
        LocalDate tglKembali = tanggal_kembali.getValue();
        int lama1 = Integer.parseInt(String.valueOf(tglPinjam.getDayOfMonth()));
        int lama2 = Integer.parseInt(String.valueOf(tglKembali.getDayOfMonth()));
        int hasill = lama2 - lama1;
        int hasilll= lama*harga;

        System.out.println(hasill);
        int bayarDenda = ((hasill - lama) * harga);
        if (bayarDenda >= 0) {
            denda.setText(String.valueOf(bayarDenda));
            int jumlah = hasilll+bayarDenda;
            total.setText(String.valueOf(jumlah));
        }else{
            denda.setText(String.valueOf(0));
            total.setText(String.valueOf(hasilll));
        }
    });

        id_transaksi.setOnAction(event -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT DISTINCT * FROM peminjaman WHERE id_transaksi = ?");
                preparedStatement.setString(1, (String) id_transaksi.getValue());
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    nama_peminjam.setText(rs.getString("nama_peminjam"));

                    alamat.setText(rs.getString("alamat"));

                    no_telp.setText(rs.getString("no_telp"));

                    email.setText(rs.getString("email"));

                    id_mobil.setText(rs.getString("id_mobil"));

                    tanggal_pinjam.setValue(LocalDate.parse(rs.getString("tanggal_pinjam")));

                    lama = rs.getInt("lama_pinjam");
                    System.out.println(lama);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT DISTINCT * FROM mobil WHERE id_mobil = ?");
                preparedStatement.setString(1, id_mobil.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    idMobil.setText(resultSet.getString("id_mobil"));

                    merk_mobil.setText(resultSet.getString("merk_mobil"));

                    no_polisi.setText(resultSet.getString("no_polisi"));

                    harga_sewa.setText(resultSet.getString("harga_sewa"));

                    tipe_mobil.setText(resultSet.getString("tipe_mobil"));

                    tahun_pembuatan.setText(resultSet.getString("tahun_pembuatan"));

                    harga = resultSet.getInt("harga_sewa");
                    System.out.println(harga);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public ArrayList<String> getId_Transaksi() throws SQLException {
        ArrayList<String> id = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("Select distinct * from Peminjaman");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            id.add(resultSet.getString("id_transaksi"));
        }
        return id;
    }

    public void simpanButton() {

        if(id_transaksi.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("id transaksi belum diisi");
            alert.showAndWait();
        }else {
            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement;
                PreparedStatement prepstat;
                if (transaksi == null) {
                    preparedStatement = connection.prepareStatement("INSERT INTO transaksi(id_transaksi,nama_peminjam,alamat,tanggal_pinjam,tanggal_kembali,lama_pinjam,total,denda,id_mobil) VALUES(?,?,?,?,?,?,?,?,?)");

                } else {
                    preparedStatement = connection.prepareStatement("UPDATE transaksi SET id_transaksi = ? ,nama_peminjam = ?,alamat = ? ,tanggal_pinjam=?,tanggal_kembali=?,lama_pinjam=?,total = ?,denda =?,id_mobil=? WHERE id_transaksi=?");
                    preparedStatement.setString(10, transaksi.getId_transaksi());
                }
                preparedStatement.setString(1, (String) id_transaksi.getValue());
                preparedStatement.setString(2, nama_peminjam.getText());
                preparedStatement.setString(3, alamat.getText());
                LocalDate tglpinjam = tanggal_pinjam.getValue();
                preparedStatement.setString(4, tglpinjam.toString());
                LocalDate tglkembali = tanggal_kembali.getValue();
                preparedStatement.setString(5, tglkembali.toString());
                int lama = Integer.parseInt(String.valueOf(tglpinjam.getDayOfMonth()));
                int lama1 = Integer.parseInt(String.valueOf(tglkembali.getDayOfMonth()));
                int hasil = lama1 - lama;
                preparedStatement.setString(6, String.valueOf(hasil));
                preparedStatement.setString(7, total.getText());
                preparedStatement.setString(8, denda.getText());
                preparedStatement.setString(9, id_mobil.getText());
                preparedStatement.executeUpdate();

                if (history == null) {
                    preparedStatement = connection.prepareStatement("INSERT INTO history_peminjaman(id_transaksi,nama_peminjam,alamat,tanggal_pinjam,tanggal_kembali,total,denda,id_mobil,lama_pinjam) VALUES(?,?,?,?,?,?,?,?,?)");
                } else {
                    preparedStatement = connection.prepareStatement("UPDATE transaksi SET id_transaksi = ? ,nama_peminjam = ?,alamat = ? ,tanggal_pinjam=?,tanggal_kembali=?,lama_pinjam=?,total = ?,denda =?,id_mobil=? WHERE id_transaksi=?");
                    preparedStatement.setString(10, transaksi.getId_transaksi());
                }
                preparedStatement.setString(1, (String) id_transaksi.getValue());
                preparedStatement.setString(2, nama_peminjam.getText());
                preparedStatement.setString(3, alamat.getText());
                LocalDate tglpinjamm = tanggal_pinjam.getValue();
                preparedStatement.setString(4, tglpinjamm.toString());
                LocalDate tglkembalii = tanggal_kembali.getValue();
                preparedStatement.setString(5, tglkembalii.toString());
                int lamaa = Integer.parseInt(String.valueOf(tglpinjamm.getDayOfMonth()));
                int lamaa1 = Integer.parseInt(String.valueOf(tglkembalii.getDayOfMonth()));
                int hasill = lamaa1 - lamaa;
                preparedStatement.setString(9, String.valueOf(hasill));
                preparedStatement.setString(6, total.getText());
                preparedStatement.setString(7, denda.getText());
                preparedStatement.setString(8, id_mobil.getText());
                preparedStatement.executeUpdate();


                preparedStatement = connection.prepareStatement("update mobil set status = 'tersedia' where id_mobil = ?");
                preparedStatement.setString(1, id_mobil.getText());
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement("Delete from peminjaman where id_transaksi =?");
                preparedStatement.setString(1, (String) id_transaksi.getValue());
                preparedStatement.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Data berhasil disimpan");
                alert.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
