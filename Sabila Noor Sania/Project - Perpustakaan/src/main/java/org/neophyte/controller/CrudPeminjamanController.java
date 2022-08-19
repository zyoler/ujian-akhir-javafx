package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.Peminjaman;
import org.neophyte.util.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CrudPeminjamanController {

    public Button createButton;
    public Button editButton;
    public Button deleteButton;
    public Button backButton;

    public static TableView<Peminjaman> staticPeminjamanTable;
    public TableView<Peminjaman> peminjamanTable;

    Stage stage = Main.getStage();
    Peminjaman selectedPeminjaman;

    public static void loadData() {
        staticPeminjamanTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PEMINJAMAN");
            while (resultSet.next()) {
                staticPeminjamanTable.getItems().add(new Peminjaman(resultSet.getInt("nomor"), resultSet.getInt("id"), resultSet.getString("judulbuku"), resultSet.getInt("idanggota"), resultSet.getString("nama"), resultSet.getString("tgl_pinjam"), resultSet.getString("tgl_kembali")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        staticPeminjamanTable = peminjamanTable;
        loadData();

        createButton.setOnAction(event -> {
            try {
                VBox vBox = FXMLLoader.load(getClass().getResource("/crud/create/CreatePeminjaman.fxml"));
                Scene createScene = new Scene(vBox);
                Stage createStage = new Stage();
                createStage.setScene(createScene);
                createStage.initOwner(peminjamanTable.getScene().getWindow());
                createStage.initModality(Modality.WINDOW_MODAL);
                createStage.setResizable(false);
                createStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
            selectedPeminjaman = (Peminjaman) peminjamanTable.getSelectionModel().getSelectedItem();

            if (selectedPeminjaman != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda yakin ingin menghapus ?");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PEMINJAMAN WHERE NOMOR = ?");
                        preparedStatement.setInt(1, selectedPeminjaman.getNomor());
                        preparedStatement.executeUpdate();
                        loadData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        backButton.setOnAction(event -> {
            try {
                HBox hBox = FXMLLoader.load(getClass().getResource("/HalamanUtama.fxml"));
                Scene berandaa = new Scene(hBox);
                stage.setScene(berandaa);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void edit() {
        selectedPeminjaman = (Peminjaman) peminjamanTable.getSelectionModel().getSelectedItem();

        if (selectedPeminjaman != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                VBox vBox = fxmlLoader.load(getClass().getResource("/crud/create/CreatePeminjaman.fxml").openStream());
                Scene editScene = new Scene(vBox);
                Stage editStage = new Stage();
                editStage.setScene(editScene);
                editStage.initOwner(peminjamanTable.getScene().getWindow());
                editStage.initModality(Modality.WINDOW_MODAL);
                editStage.setResizable(false);

                CreatePeminjamanController createPeminjamanController = fxmlLoader.getController();
                createPeminjamanController.fillForm(selectedPeminjaman);

                editStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
