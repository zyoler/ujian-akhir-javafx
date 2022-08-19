package org.neophyte.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.model.Obat;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.regex.Pattern;

public class CreateObatController {
    public TextField idObatField;
    public TextField namaObatField;
    public TextField hargaBeliField;
    public TextField hargaJualField;
    public TextField stokField;

    public Button saveObatButton;
    public Button cancelObatButton;

    Obat obat;

    public void validation() {
        Validasi.textOnly(namaObatField);
        Validasi.numericOnly(hargaBeliField);
        Validasi.numericOnly(hargaJualField);
        Validasi.numericOnly(stokField);
        Validasi.addTextLimiter(namaObatField,50);
        Validasi.addTextLimiter(stokField,3);
        Validasi.addTextLimiter(hargaBeliField,9);
        Validasi.addTextLimiter(hargaJualField,9);
    }

    public void close() {
        ((Stage)cancelObatButton.getScene().getWindow()).close();
    }
    public boolean isNumber(String in){
        return Pattern.matches("[0-9]+",in);
    }
    public void save() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if(Objects.equals(namaObatField.getText(),"")){
            alert.setContentText("Nama Obat belum diisi");
            alert.show();
        }else if(Objects.equals(hargaBeliField.getText(),"")){
            alert.setContentText("Harga Beli belum diisi");
            alert.show();
        }else if(Objects.equals(hargaJualField.getText(),"")){
            alert.setContentText("Harga Jual belum diisi");
            alert.show();
        }else if(Objects.equals(stokField.getText(),"")){
            alert.setContentText("Stok Obat belum diisi");
            alert.show();
        }else {
            int jual =Integer.parseInt(String.valueOf(hargaJualField.getText()));
            int beli = Integer.parseInt(String.valueOf(hargaBeliField.getText()));
            if(jual<beli){
                alert.setContentText("Harga Jual harus lebih besar dari Harga Beli");
                alert.show();
            }else {
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement;
                    if (obat == null) // Tambah obat
                        preparedStatement = connection.prepareStatement("INSERT INTO obat (idObat, namaObat, hargaBeli, hargaJual, STOK) VALUES (?, ?, ?, ?,?)");
                    else { // Edit obat
                        preparedStatement = connection.prepareStatement("UPDATE obat SET idObat = ?, namaObat = ?, hargaBeli = ?, hargaJual = ?, stok = ? WHERE idObat = ?");
                        preparedStatement.setString(6, obat.getIdObat());
                    }
                    preparedStatement.setString(1, idObatField.getText());
                    preparedStatement.setString(2, namaObatField.getText());
                    preparedStatement.setString(3, hargaBeliField.getText());
                    preparedStatement.setString(4, hargaJualField.getText());
                    preparedStatement.setString(5, stokField.getText());
                    preparedStatement.executeUpdate();
                    DataController.loadData();
                    close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void fillForm(Obat obat) {
        this.obat = obat;
        idObatField.setText(obat.getIdObat());
        namaObatField.setText(obat.getNamaObat());
        hargaBeliField.setText(Integer.toString(obat.getHargaBeli()));
        hargaJualField.setText(Integer.toString(obat.getHargaJual()));
        stokField.setText(Integer.toString(obat.getStok()));
    }

    public void cekId(int tamp){
        if(tamp < 10)
            idObatField.setText("B00" + tamp);
        else if(tamp < 100)
            idObatField.setText("B0" + tamp);
        idObatField.setEditable(false);

    }
    public void loadId(){
        int tamp = DataController.nampungId;
        cekId(tamp);
    }

    public void initialize() {
        validation();
        loadId();
    }
}
