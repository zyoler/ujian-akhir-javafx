package org.neo.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.neo.models.Jadwal;
import org.neo.models.Lapang;
import org.neo.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class CrudLapang {

    public Button saveButton;
    public Button cancelButton;
    public TextField namalpng;
    public TextField harga;
    public TextField jenislpng;

    Lapang lapang;

    public void initialize(){
        harga.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                harga.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        namalpng.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                namalpng.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        jenislpng.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                jenislpng.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        saveButton.setOnAction(event -> {
            if(namalpng.getText().trim().isEmpty() || jenislpng.getText().trim().isEmpty() || harga.getText().trim().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning!!");
                alert.setHeaderText(null);
                alert.setContentText("Harap isi data terlebih dahulu!");
                alert.show();
            }
            try {
                Connection connection = Database.connect();
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement("UPDATE LAPANG SET  NAMALPNG= ?, JENISLPNG = ?, HARGA=? WHERE ID_LAPANG = ?");
                preparedStatement.setInt(4, lapang.getIdlpng());
                preparedStatement.setString(1, namalpng.getText());
                preparedStatement.setString(2, jenislpng.getText());
                preparedStatement.setInt(3,Integer.parseInt(harga.getText()));
                preparedStatement.executeUpdate();
                AdminPanelController.loadDataLapang();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void fillForm(Lapang lapang) {
        this.lapang = lapang;
        namalpng.setText(lapang.getNamalpng());
        jenislpng.setText(lapang.getJenislpng());
        harga.setText(String.valueOf(lapang.getHarga()));
    }
}
