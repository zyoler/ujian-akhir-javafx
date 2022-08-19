package org.neophyte.Controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.model.JadwalTayang;
import org.neophyte.utils.DataBase;
import org.neophyte.validasi.ValidasiUmum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class CrudJadwalController {
    public TextField KDTayangTextField;
    public TextField tittleTextField;
    public TextField genreTextField;
    public TextField posterTextField;
    public JFXTimePicker timePicker;
    public JFXDatePicker datePicket;

    public Button saveBtn;
    public Button cancelBtn;

    JadwalTayang jadwalTayang;
    Connection conn = DataBase.getConnection();

    public void save(ActionEvent actionEvent) throws SQLException {
        LocalDate localDate = datePicket.getValue();
        PreparedStatement preparedStatement = null;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (Objects.equals(KDTayangTextField.getText(), "")) {
            alert.setContentText("Nama pengguna belum diisi.");
            alert.show();
        } else if (ValidasiUmum.cekKode(KDTayangTextField.getText()) == false) {
            alert.setContentText("Haya Berisis Huruf Kapital da Angka.");
            alert.show();
        } else if (Objects.equals(datePicket.getValue(), "")) {
            alert.setContentText("Tanggal belum diisi.");
            alert.show();
        } else if (Objects.equals(timePicker.getValue(), "")) {
            alert.setContentText("Waktu belum diisi.");
            alert.show();
        } else if (Objects.equals(tittleTextField.getText(), "")) {
            alert.setContentText("Tittle belum diisi.");
            alert.show();
        } else if (ValidasiUmum.tittle(tittleTextField.getText())==false) {
            alert.setContentText("Ada Kesalaha Penulisan Tittle!");
            alert.show();
        } else if (Objects.equals(genreTextField.getText(), "")) {
            alert.setContentText("Genre belum diisi.");
            alert.show();
        } else if (ValidasiUmum.genre(genreTextField.getText())==false) {
            alert.setContentText("Kesalaha Penulisa Dan Tidak Memuat Angka.");
            alert.show();
        } else if (Objects.equals(posterTextField.getText(), "")) {
            alert.setContentText("Poster belum diisi.");
            alert.show();
        } else {
            try {
                if (jadwalTayang == null) {
                    preparedStatement = conn.prepareStatement("INSERT INTO JADWAL_TAYANG (KODE_TAYANG, TANGGAL, JAM_TAYANG, TITTLE, GENRE, POSTER) VALUES ( ?,?,?,?,?,? )");
                    preparedStatement.setString(1, KDTayangTextField.getText());
                    preparedStatement.setObject(2, localDate.toString());
                    preparedStatement.setObject(3, timePicker.getValue());
                    preparedStatement.setString(4, tittleTextField.getText());
                    preparedStatement.setString(5, genreTextField.getText());
                    preparedStatement.setString(6, "/img/" + posterTextField.getText());
                    preparedStatement.executeUpdate();
                    JadwalTayangController.loadDataJadwal();
                    close();
                } else {
                    preparedStatement = conn.prepareStatement("UPDATE JADWAL_TAYANG SET TANGGAL = ? , JAM_TAYANG = ?, TITTLE = ?, GENRE = ?, POSTER = ? WHERE KODE_TAYANG = ?");
                    preparedStatement.setString(6, KDTayangTextField.getText());
                    preparedStatement.setObject(1, localDate.toString());
                    preparedStatement.setObject(2, timePicker.getValue());
                    preparedStatement.setString(3, tittleTextField.getText());
                    preparedStatement.setString(4, genreTextField.getText());
                    preparedStatement.setString(5, posterTextField.getText());
                    preparedStatement.executeUpdate();
                    JadwalTayangController.loadDataJadwal();
                    close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {

        ((Stage) cancelBtn.getScene().getWindow()).close();
    }

    public void fillForm(JadwalTayang jadwalTayang) {
        this.jadwalTayang = jadwalTayang;
        String date = jadwalTayang.getTanggal();
        KDTayangTextField.setText(jadwalTayang.getKodeTayang());
        datePicket.setValue(LocalDate.parse(date.toString()));
        String time = jadwalTayang.getJamTayang();
        timePicker.setValue(LocalTime.parse(time.toString()));
        tittleTextField.setText(jadwalTayang.getJudulMovie());
        genreTextField.setText(jadwalTayang.getJudulMovie());
        posterTextField.setText(jadwalTayang.getPoster());
    }
}
