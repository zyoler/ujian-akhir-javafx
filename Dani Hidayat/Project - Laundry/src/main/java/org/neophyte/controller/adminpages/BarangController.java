package org.neophyte.controller.adminpages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.controller.Validation;
import org.neophyte.model.Barang;
import org.neophyte.model.Pegawai;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BarangController {
    public static TableView<Barang> staticUserTable;
    public static int nampungId;
    public TableView<Barang> barangTable;
    public Button deleteButton;
    public Button cancelButton;
    public Button editButton;
    public Button saveButton;
    public TextField idField;
    public TextField namaField;
    public TextField hargaField;
    Barang selectedBarang;

    public static void loadData() {
        staticUserTable.getItems().setAll();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM barang");
            nampungId = 1;
            while (resultSet.next()){
                staticUserTable.getItems().add(new Barang(resultSet.getString("kode"),resultSet.getString("nama"),resultSet.getInt("harga")));
                nampungId = Integer.parseInt(resultSet.getString("kode").substring(3)) + 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialize() {
        validation();
        staticUserTable = barangTable;
        loadData();cekID();

        saveButton.setOnAction(event -> {
            if(!terisiGa()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Pastikan data terisi semua");
                alert.show();
            }else if(Integer.parseInt(idField.getText().substring(3)) != nampungId){
                cekID();hapusField();
                System.out.println(Integer.parseInt(idField.getText().substring(3))+1);
                System.out.println(nampungId);
            }else{
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO barang VALUES (?,?,?)");
                    preparedStatement.setString(1, idField.getText());
                    preparedStatement.setString(2,namaField.getText());
                    preparedStatement.setInt(3, Integer.parseInt(hargaField.getText()));
                    preparedStatement.executeUpdate();
                    loadData();cekID();hapusField();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        barangTable.setOnMouseClicked(event -> {
            selectedBarang = barangTable.getSelectionModel().getSelectedItem();
            idField.setText(selectedBarang.getKode());
            namaField.setText(selectedBarang.getNama());
            hargaField.setText(String.valueOf(selectedBarang.getHarga()));
        });
        editButton.setOnAction(event -> {
            selectedBarang = barangTable.getSelectionModel().getSelectedItem();
            if(!terisiGa()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Pastikan data terisi semua");
                alert.show();
            }else if(selectedBarang != null) {
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE barang SET nama = ?, harga = ? WHERE kode = ?");
                    preparedStatement.setString(1,namaField.getText());
                    preparedStatement.setInt(2, Integer.parseInt(hargaField.getText()));
                    preparedStatement.setString(3,idField.getText());
                    preparedStatement.executeUpdate();
                    loadData();hapusField();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        deleteButton.setOnAction(event -> {
            selectedBarang = barangTable.getSelectionModel().getSelectedItem();
            if(selectedBarang != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectedBarang.getNama() + '?');
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM barang WHERE kode = ?");
                        preparedStatement.setString(1,selectedBarang.getKode());
                        preparedStatement.executeUpdate();
                        loadData();cekID();hapusField();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        cancelButton.setOnAction(event -> {
            hapusField();cekID();
        });
    }
    public void cekID(){
        if(nampungId < 10)
            idField.setText("BRG00" + nampungId);
        else if(nampungId < 100)
            idField.setText("BRG0" + nampungId);
        else
            idField.setText("BRG" + nampungId);
    }

    public void validation() {
        Validation.numericOnly(hargaField);
        Validation.addTextLimiter(hargaField,6);
    }
    public boolean terisiGa() {
        ArrayList<Object> cek = new ArrayList<>();
        cek.add(namaField.getText());cek.add(hargaField.getText());
        if(cek.contains("")){
            return false;
        }else{
            return true;
        }
    }
    public void hapusField(){
        namaField.setText("");hargaField.setText("");
    }
}
