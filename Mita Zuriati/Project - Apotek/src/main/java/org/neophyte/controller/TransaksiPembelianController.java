package org.neophyte.controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.TampViewPb;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Objects;

public class TransaksiPembelianController {

    public TextField idPembelianField;
    public DatePicker tanggalPembelianField;
    public TextField totalBayarField;
    public TextField pembayaranField;

    public Button batalButton;
    public Button bayarButton;

    TampViewPb pembelian;
    public int harga;
    public int uang;
    public int kembali;

    public void validation(){
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
        } else{
                uang = Integer.parseInt(String.valueOf(pembayaranField.getText()));

                Connection connection = Database.getConnection();

                if (uang == harga) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Uang Pas!");
                    alert.showAndWait();
                    if(alert.getResult() == ButtonType.OK){
                        ((Stage) pembelianController.staticPembTable.getScene().getWindow()).close();
                        ((Stage) pembayaranField.getScene().getWindow()).close();
                        Main.refresh();
                    }
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TAMP WHERE IDP = ?");
                        preparedStatement.setString(1, idPembelianField.getText());
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
                        ((Stage) pembelianController.staticPembTable.getScene().getWindow()).close();
                        ((Stage) pembayaranField.getScene().getWindow()).close();
                        Main.refresh();
                    }try {
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TAMP WHERE IDP = ?");
                        preparedStatement.setString(1, idPembelianField.getText());
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
    public void fillForm(TampViewPb pembelian) {
        this.pembelian = pembelian;
        idPembelianField.setText(pembelian.getIdPembelian());
        idPembelianField.setEditable(false);
        tanggalPembelianField.setValue(LocalDate.parse(pembelian.getTglPembelian()));
        tanggalPembelianField.setEditable(false);
        //totalBayarField.setText(Integer.toString(pembelian.getTotalBayar()));
        totalBayarField.setText(Integer.toString(pembelianController.totalll));
        totalBayarField.setEditable(false);
        harga = Integer.parseInt(String.valueOf(totalBayarField.getText()));
    }
}
