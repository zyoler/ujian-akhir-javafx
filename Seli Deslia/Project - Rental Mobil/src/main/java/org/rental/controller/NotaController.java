package org.rental.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.rental.Main;
import org.rental.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class NotaController {
    public TextField namaTextField;
    public TextField alamatTextField;
    public TextField tglPinjamTextField;
    public TextField tglKembaliTextField;
    public TextField lamaTextField;
    public TextField tipeTextField;
    public TextField polisiTextField;
    public TextField hargaTextField;
    public TextField dendaTextField;
    public TextField totalBayarTextField;
    public Button kembaliButton;
    Connection connection = Database.getConnection();
    BayarController not;
    public void initialize(){
        kembaliButton.setOnAction(event -> {
            Main.refresh();
        });
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT DISTINCT * FROM transaksi WHERE id_transaksi = ?");
            System.out.println(BayarController.id);
            preparedStatement.setString(1, BayarController.id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                namaTextField.setText(rs.getString("nama_peminjam"));
                namaTextField.setEditable(false);
                alamatTextField.setText(rs.getString("alamat"));
                alamatTextField.setEditable(false);
                tglPinjamTextField.setText(String.valueOf(LocalDate.parse(rs.getString("tanggal_pinjam"))));
                tglPinjamTextField.setEditable(false);
                tglKembaliTextField.setText(String.valueOf(LocalDate.parse(rs.getString("tanggal_kembali"))));
                tglKembaliTextField.setEditable(false);
                lamaTextField.setText(rs.getString("lama_pinjam"));
                dendaTextField.setText(rs.getString("denda"));
                totalBayarTextField.setText(rs .getString("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT DISTINCT * FROM mobil WHERE id_mobil = ?");
            preparedStatement.setString(1, BayarController.idm);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tipeTextField.setText(resultSet.getString("tipe_mobil"));
                tipeTextField.setEditable(false);
                polisiTextField.setText(resultSet.getString("no_polisi"));
                polisiTextField.setEditable(false);
                hargaTextField.setText(resultSet.getString("harga_sewa"));
                hargaTextField.setEditable(false);
            }
            preparedStatement = connection.prepareStatement("DELETE FROM transaksi where id_transaksi=?");
           preparedStatement.setString(1,BayarController.id );
            System.out.println(BayarController.id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
  }
}
