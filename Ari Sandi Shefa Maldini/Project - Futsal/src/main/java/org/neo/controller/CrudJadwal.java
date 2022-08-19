package org.neo.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.neo.models.Admin;
import org.neo.models.Jadwal;
import org.neo.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class CrudJadwal {
    public DatePicker tglMain;
    public TextField pukul;
    public Button simpanButton;
    public Button batalButton;

    LocalDate lDate = LocalDate.now();
    Jadwal jadwal;

    public void initialize(){
        pukul.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                pukul.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        simpanButton.setOnAction(event -> {
            if(tglMain.getValue() == null || pukul.getText().trim().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning!!");
                alert.setHeaderText(null);
                alert.setContentText("Harap isi data terlebih dahulu!");
                alert.show();
            }else if(tglMain.getValue().isBefore(lDate)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning!!");
                alert.setHeaderText(null);
                alert.setContentText("Isi tanggal yang sesuai!!");
                alert.show();
            }else{
                try {
                    LocalDate localDate = tglMain.getValue();
                    Connection connection = Database.connect();
                    PreparedStatement preparedStatement;
                    preparedStatement = connection.prepareStatement("UPDATE BOOKING SET  TGL_MAIN= ?, PUKUL = ? WHERE ID_BOOKING = ?");
                    preparedStatement.setInt(3, jadwal.getId_booking());
                    preparedStatement.setString(1, localDate.toString());
                    preparedStatement.setString(2, pukul.getText());
                    preparedStatement.executeUpdate();
                    AdminPanelController.loadDataJadwal();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fillForm(Jadwal jadwal) {
        this.jadwal = jadwal;
        tglMain.setValue(LocalDate.parse(jadwal.getTgl_main()));
        pukul.setText(jadwal.getPukul());
    }
}
