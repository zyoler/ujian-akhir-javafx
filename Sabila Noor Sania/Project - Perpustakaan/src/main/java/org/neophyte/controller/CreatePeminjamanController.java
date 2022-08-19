package org.neophyte.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.model.Anggota;
import org.neophyte.model.Peminjaman;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class CreatePeminjamanController {
    public TextField judulBukuField;
    public TextField nameField;
    public DatePicker tglPinjamField;
    public DatePicker tglKembaliField;
    public Button cancelButton;

    Peminjaman peminjaman;

    public void save() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (judulBukuField.getText().matches(".*\\d.*")){
            alert.setContentText("Judul Buku Tidak Boleh Berisi Angka");
            alert.show();
        }else if (nameField.getText().matches(".*\\d.*")){
            alert.setContentText("Nama Tidak Boleh Berisi Angka");
            alert.show();
        }else{
            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement;
                if (peminjaman == null)
                    preparedStatement = connection.prepareStatement("INSERT INTO PEMINJAMAN (id,JUDULBUKU,IDANGGOTA,NAMA,TGL_PINJAM,TGL_KEMBALI) VALUES (?, ?, ?,?,?,?)");
                else {
                    preparedStatement = connection.prepareStatement("UPDATE PEMINJAMAN SET JUDULBUKU =?,NAMA = ?, TGL_PINJAM= ?, TGL_KEMBALI= ? WHERE NOMOR = ?");
                    preparedStatement.setInt(5, peminjaman.getNomor());
                }
                preparedStatement.setString(1,judulBukuField.getText());
                preparedStatement.setString(2,nameField.getText());
                LocalDate tglPinjam = tglPinjamField.getValue();
                preparedStatement.setString(3,tglPinjam.toString());
                LocalDate tglKembali = tglKembaliField.getValue();
                preparedStatement.setString(4,tglKembali.toString());
                preparedStatement.executeUpdate();
                CrudPeminjamanController.loadData();
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void close() {

        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public void fillForm(Peminjaman peminjaman) {
        this.peminjaman = peminjaman;
        judulBukuField.setText(peminjaman.getJudulbuku());
        nameField.setText(peminjaman.getNama());
        tglPinjamField.setValue(LocalDate.parse(peminjaman.getTgl_pinjam()));
        tglKembaliField.setValue(LocalDate.parse(peminjaman.getTgl_kembali()));
    }
}
