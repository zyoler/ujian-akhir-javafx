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
import org.neophyte.model.Anggota;
import org.neophyte.util.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CrudAnggotaController {
    public static TableView<Anggota> staticAnggotaTable;

    public Button createButton;
    public Button editButton;
    public Button deleteButton;
    public Button beranda;
    public Button buku;
    public Button peminjaman;
    public Button admin;
    public TableView anggotaTable;
    public Button Logout;

    Stage stage = Main.getStage();
    Anggota selectedAnggota;

    public static void loadData() {
        staticAnggotaTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ANGGOTA");
            while (resultSet.next()) {
                staticAnggotaTable.getItems().add(new Anggota(resultSet.getInt("idanggota"), resultSet.getString("nama"), resultSet.getString("nim"), resultSet.getString("prodi"),resultSet.getInt("semester")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        staticAnggotaTable = anggotaTable;
        loadData();

        createButton.setOnAction(event -> {
            try {
                VBox vBox = FXMLLoader.load(getClass().getResource("/crud/create/CreateAnggota.fxml"));
                Scene createScene = new Scene(vBox);
                Stage createStage = new Stage();
                createStage.setScene(createScene);
                createStage.initOwner(anggotaTable.getScene().getWindow());
                createStage.initModality(Modality.WINDOW_MODAL);
                createStage.setResizable(false);
                createStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
           selectedAnggota = (Anggota) anggotaTable.getSelectionModel().getSelectedItem();

            if (selectedAnggota != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectedAnggota.getNama()+ '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ANGGOTA WHERE IDANGGOTA = ?");
                        preparedStatement.setInt(1, selectedAnggota.getIdanggota());
                        preparedStatement.executeUpdate();
                        loadData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        

        beranda.setOnAction(event -> {
            try {
                HBox hBox = FXMLLoader.load(getClass().getResource("/HalamanUtama.fxml"));
                Scene berandaa = new Scene(hBox);
                // Stage stage = new Stage();
                stage.setScene(berandaa);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        buku.setOnAction(event -> {
            try {
                HBox hBox = FXMLLoader.load(getClass().getResource("/crud/CrudBuku.fxml"));
                Scene bukuu = new Scene(hBox);
                // Stage stage = new Stage();
                stage.setScene(bukuu);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        peminjaman.setOnAction(event -> {
            try {
                VBox vBox = FXMLLoader.load(getClass().getResource("/crud/CrudPeminjaman.fxml"));
                Scene peminjamaan = new Scene(vBox);
                // Stage stage = new Stage();
                stage.setScene(peminjamaan);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        admin.setOnAction(event -> {
            try {
                HBox hBox = FXMLLoader.load(getClass().getResource("/crud/CrudAdmin.fxml"));
                Scene adminnn = new Scene(hBox);
                // Stage stage = new Stage();
                stage.setScene(adminnn);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Logout.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText(null);
            alert.setContentText("Apakah Anda ingin Keluar ?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                try {
                    Main.refresh();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void edit() {
        selectedAnggota = (Anggota) anggotaTable.getSelectionModel().getSelectedItem();

        if (selectedAnggota != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                VBox vBox = fxmlLoader.load(getClass().getResource("/crud/create/CreateAnggota.fxml").openStream());
                Scene editScene = new Scene(vBox);
                Stage editStage = new Stage();
                editStage.setScene(editScene);
                editStage.initOwner(anggotaTable.getScene().getWindow());
                editStage.initModality(Modality.WINDOW_MODAL);
                editStage.setResizable(false);

                CreateAnggotaController createAnggotaController = fxmlLoader.getController();
                createAnggotaController.fillForm(selectedAnggota);

                editStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
