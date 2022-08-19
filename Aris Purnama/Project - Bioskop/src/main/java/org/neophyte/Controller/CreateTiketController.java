package org.neophyte.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.neophyte.model.Tiket;
import org.neophyte.utils.DataBase;
import org.neophyte.validasi.ValidasiUmum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class CreateTiketController {
    public JFXTextField KDTiket;
    public JFXTextField jenisTiket;
    public JFXTextField hargaTiket;
    public Button BtnSave;
    public Button BtnCancel;
    Connection connection = DataBase.getConnection();
    PreparedStatement preparedStatement;
    Tiket tiket;
    public void close() {
        ((Stage) BtnCancel.getScene().getWindow()).close();
    }

    public void save(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if (Objects.equals(KDTiket.getText(),"")) {
            alert.setContentText("Kode Tidak Boleh Kososng.");
            alert.show();
        }else if (ValidasiUmum.cekKode(KDTiket.getText())==false){
            alert.setContentText("Kode Haya Huruf Kapital dan Angka.");
            alert.show();
        }else if (Objects.equals(jenisTiket.getText(),"")){
            alert.setContentText("Jenis Tiket Tidak Boleh Kosong");
            alert.show();
        }else if (ValidasiUmum.genre(jenisTiket.getText())==false){
            alert.setContentText("Haya Huruf Kapital dan Huruf Non-Kapital.");
            alert.show();
        }else if (Objects.equals(hargaTiket.getText(),"")){
            alert.setContentText("Harga Tidak Boleh Kosong");
            alert.show();
        }else if (ValidasiUmum.cekharga(hargaTiket.getText())==false){
            alert.setContentText("Hanya Berisi Angka. HArga Di Atas 15.000");
            alert.show();
        }else {
            try {
                if (tiket == null) {// Tambah user
                    preparedStatement = connection.prepareStatement("INSERT INTO TIKET (KODE_TIKET, JENIS_TIKET, HARGA) VALUES ( ?,?,? )");
                    preparedStatement.setString(1, KDTiket.getText());
                    preparedStatement.setString(2, jenisTiket.getText());
                    preparedStatement.setString(3, hargaTiket.getText());
                    preparedStatement.executeUpdate();
                    KelolaStudioController.getAllDatatiket();
                    alert.setContentText("Berhasil.");
                    alert.show();
                    close();
                } else {
                    preparedStatement = connection.prepareStatement("UPDATE TIKET SET JENIS_TIKET = ?, HARGA = ? WHERE KODE_TIKET = ?");
                    preparedStatement.setString(3, tiket.getKdTiket());
                    preparedStatement.setString(1, jenisTiket.getText());
                    preparedStatement.setString(2, hargaTiket.getText());
                    preparedStatement.executeUpdate();
                    KelolaStudioController.getAllDatatiket();
                    close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillForm(Tiket tiket) {
        this.tiket = tiket;
        KDTiket.setText(tiket.getKdTiket());
        jenisTiket.setText(tiket.getJenisTiket());
        hargaTiket.setText(String.valueOf(tiket.getHarga()));
    }
}
