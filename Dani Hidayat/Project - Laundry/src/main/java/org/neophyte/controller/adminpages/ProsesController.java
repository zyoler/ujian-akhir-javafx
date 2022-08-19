package org.neophyte.controller.adminpages;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.neophyte.model.Pelanggan;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProsesController {

    public static TableView<Pelanggan> staticProsesTable;
    public static TableView<Pelanggan> staticSelesaiTable;

    public TableView<Pelanggan> prosesTable;
    public TableView<Pelanggan> selesaiTable;

    public Button saveButton;

    Pelanggan selectedProses;

    public static void loadData(){
        staticProsesTable.getItems().setAll();
        staticSelesaiTable.getItems().setAll();
        try{
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pelanggan WHERE proses_cucian = ?");
            preparedStatement.setString(1,"Proses");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                staticProsesTable.getItems().add(new Pelanggan(resultSet.getString("id"),resultSet.getString("nama"),resultSet.getString("alamat"),resultSet.getString("no_telp"),resultSet.getDate("tgl_daftar"),resultSet.getString("proses_cucian")));
            }
            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM pelanggan WHERE proses_cucian = ?");
            preparedStatement2.setString(1,"Selesai");
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()){
                staticSelesaiTable.getItems().add(new Pelanggan(resultSet2.getString("id"),resultSet2.getString("nama"),resultSet2.getString("alamat"),resultSet2.getString("no_telp"),resultSet2.getDate("tgl_daftar"),resultSet2.getString("proses_cucian")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialize() {
        staticProsesTable = prosesTable;
        staticSelesaiTable = selesaiTable;
        loadData();

        saveButton.setOnAction(event -> {
            selectedProses = prosesTable.getSelectionModel().getSelectedItem();
            if(selectedProses != null){
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE pelanggan SET proses_cucian = ? WHERE id = ?");
                    preparedStatement.setString(1, "Selesai");
                    preparedStatement.setString(2, selectedProses.getId());
                    preparedStatement.executeUpdate();
                    loadData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
