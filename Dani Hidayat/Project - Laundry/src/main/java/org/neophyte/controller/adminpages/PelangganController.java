package org.neophyte.controller.adminpages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.model.Pelanggan;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PelangganController {

    public static TableView<Pelanggan> staticUserTable;
    public static int nampungId;

    public Button tambahButton;
    public Button hapusButton;
    public TableView<Pelanggan> pelangganTable;

    Pelanggan selectedPelanggan;


    public static void loadData() {
        staticUserTable.getItems().setAll();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pelanggan");
            nampungId = 1;
            while (resultSet.next()){
                staticUserTable.getItems().add(new Pelanggan(resultSet.getString("id"),resultSet.getString("nama"),resultSet.getString("alamat"),resultSet.getString("no_telp"),resultSet.getDate("tgl_daftar"),resultSet.getString("proses_cucian")));
                nampungId = Integer.parseInt(resultSet.getString("id").substring(3)) + 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialize(){
        staticUserTable = pelangganTable;
        loadData();

        tambahButton.setOnAction(event -> {
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/pages/mainpages/tambah_data_pelanggan.fxml"));
                Scene tambahScene = new Scene(root);
                Stage tambahStage = new Stage();
                tambahScene.getStylesheets().add("styles/style.css");
                tambahStage.setScene(tambahScene);
                tambahStage.initOwner(pelangganTable.getScene().getWindow());
                tambahStage.initModality(Modality.WINDOW_MODAL);
                tambahStage.setResizable(false);
                tambahStage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        hapusButton.setOnAction(event -> {
            selectedPelanggan = pelangganTable.getSelectionModel().getSelectedItem();
            if(selectedPelanggan != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectedPelanggan.getNama() + '?');
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pelanggan WHERE id = ?");
                        preparedStatement.setString(1,selectedPelanggan.getId());
                        preparedStatement.executeUpdate();
                        loadData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
