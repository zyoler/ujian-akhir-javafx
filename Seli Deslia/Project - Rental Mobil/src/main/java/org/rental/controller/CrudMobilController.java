package org.rental.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.rental.Main;
import org.rental.model.Admin;
import org.rental.model.Mobil;
import org.rental.util.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class CrudMobilController {
    public static TableView<Mobil> staticMobilTable;
    public TableView<Mobil> mobilTable;
    public Button tambahButton;
    public Button hapusButton;
    public Button editButton;
    public Button kembaliButton;


    public static void loadDAta() {
        staticMobilTable.getItems().setAll();
        try {

            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from mobil");
            while (resultSet.next()) {

                staticMobilTable.getItems().add(new Mobil(resultSet.getString("id_mobil"), resultSet.getString("merk_mobil"), resultSet.getString("no_polisi"), resultSet.getInt("harga_sewa"), resultSet.getString("tipe_mobil"), resultSet.getString("tahun_pembuatan"), resultSet.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        staticMobilTable = mobilTable;
        loadDAta();

        hapusButton.setOnAction(event -> {
            Mobil selectedMobil = mobilTable.getSelectionModel().getSelectedItem();

            if(selectedMobil != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus Mobil");
                alert.setHeaderText(null);
                alert.setContentText("Apakah anda ingin menghapus mobil dengan no polisi" + selectedMobil.getNo_polisi() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM mobil where id_mobil=?");
                        preparedStatement.setString(1, selectedMobil.getId_mobil());
                        preparedStatement.executeUpdate();
                        loadDAta();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        tambahButton.setOnAction(event -> {
            VBox vBox = null;
            try {
                vBox = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/createMobil.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene createShene = new Scene(vBox);
            Stage createStage = new Stage();
            createStage.setScene(createShene);
            createStage.initOwner(mobilTable.getScene().getWindow());
            createStage.initModality(Modality.WINDOW_MODAL);
            createStage.show();
        });

        kembaliButton.setOnAction(event -> {
            Main.refresh();
        });
        editButton.setOnAction(event -> {
            Mobil selectedMobil = mobilTable.getSelectionModel().getSelectedItem();
            if(selectedMobil != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    VBox vBox = fxmlLoader.load(getClass().getResource("/createMobil.fxml").openStream());
                    Scene editScene = new Scene(vBox);
                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    editStage.initOwner(mobilTable.getScene().getWindow());
                    editStage.initModality(Modality.WINDOW_MODAL);

                    CreateMobilController createMobilController = fxmlLoader.getController();
                    createMobilController.fillForm(selectedMobil);
                    editStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
