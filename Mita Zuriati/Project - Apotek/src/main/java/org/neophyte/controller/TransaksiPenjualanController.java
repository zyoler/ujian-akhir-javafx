package org.neophyte.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.TampViewPj;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

public class TransaksiPenjualanController {

    public TextField idPenjualanField;
    public DatePicker tanggalPenjualanField;
    public TextField totalBayarField;
    public TextField pembayaranField;

    public Button batalButton;
    public  Button bayarButton;

    TampViewPj penjualan;
    public int harga;
    public int uang;
    public int kembali;
    public void validation() {
        Validasi.numericOnly(pembayaranField);
        Validasi.addTextLimiter(pembayaranField,9);
    }
    public void initialize(){
        validation();
    }
    public void close() {
        ((Stage)batalButton.getScene().getWindow()).close();
    }
    public void bayar() {
        if (Objects.equals(pembayaranField.getText(), "")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Pembayaran belum diisi.");
            alert.show();
        }else{

                uang = Integer.parseInt(String.valueOf(pembayaranField.getText()));

                Connection connection = Database.getConnection();

                if (uang == harga) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Uang Pas!");
                    alert.showAndWait();
                    if(alert.getResult() == ButtonType.OK){
                        ((Stage) penjualanController.staticPenjTable.getScene().getWindow()).close();
                        ((Stage) pembayaranField.getScene().getWindow()).close();
                        Main.refresh();
                    }try {
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TAMP WHERE IDP = ?");
                        preparedStatement.setString(1, idPenjualanField.getText());
                        preparedStatement.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (uang > harga) {
                    kembali = uang - harga;
                    System.out.println(kembali);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Kembalian :" + kembali);
                    alert.showAndWait();
                    if(alert.getResult() == ButtonType.OK){
                        ((Stage) penjualanController.staticPenjTable.getScene().getWindow()).close();
                        ((Stage) pembayaranField.getScene().getWindow()).close();
                        Main.refresh();
                    }
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TAMP WHERE IDP = ?");
                        preparedStatement.setString(1, idPenjualanField.getText());
                        preparedStatement.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (uang < harga) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Uang Kurang!");
                    alert.show();
                    pembayaranField.setText("");
                }

        }
    }
    public void fillForm(TampViewPj penjualan) {
        this.penjualan = penjualan;
        idPenjualanField.setText(penjualan.getIdPenjualan());
        idPenjualanField.setEditable(false);
        tanggalPenjualanField.setValue(LocalDate.parse(penjualan.getTanggalPenjualan()));
        tanggalPenjualanField.setEditable(false);
        //totalBayarField.setText(Integer.toString(penjualan.getTotalBayar()));
        totalBayarField.setText(Integer.toString(penjualanController.totalll));
        totalBayarField.setEditable(false);
        harga = Integer.parseInt(String.valueOf(totalBayarField.getText()));
    }
}
