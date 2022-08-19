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
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class CrudAdminController {
    public static TableView<User> staticUserTable;

    public Button createButton;
    public Button editButton;
    public Button deleteButton;
    public TableView<User> AdminTable;
    public Button Beranda;
    public Button Buku;
    public Button Peminjaman;
    public Button Admin;
    public Button Logout;

    Stage stage = Main.getStage();
    User selectedUser;

    public static void loadData() {
        staticUserTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USER");
            while (resultSet.next()) {
                staticUserTable.getItems().add(new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        staticUserTable = AdminTable;
        loadData();

        createButton.setOnAction(event -> {
            try {
                VBox vBox = FXMLLoader.load(getClass().getResource("/crud/create/CreateAdmin.fxml"));
                Scene createScene = new Scene(vBox);
                Stage createStage = new Stage();
                createStage.setScene(createScene);
                createStage.initOwner(AdminTable.getScene().getWindow());
                createStage.initModality(Modality.WINDOW_MODAL);
                createStage.setResizable(false);
                createStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
            selectedUser = AdminTable.getSelectionModel().getSelectedItem();

            if (selectedUser != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectedUser.getName() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
                        preparedStatement.setInt(1, selectedUser.getId());
                        preparedStatement.executeUpdate();
                        loadData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Beranda.setOnAction(event -> {
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

        Buku.setOnAction(event -> {
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

        Peminjaman.setOnAction(event -> {
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

        Admin.setOnAction(event -> {
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
        selectedUser = AdminTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                VBox vBox = fxmlLoader.load(getClass().getResource("/crud/create/CreateAdmin.fxml").openStream());
                Scene editScene = new Scene(vBox);
                Stage editStage = new Stage();
                editStage.setScene(editScene);
                editStage.initOwner(AdminTable.getScene().getWindow());
                editStage.initModality(Modality.WINDOW_MODAL);
                editStage.setResizable(false);

                CreateAdminController createAdminController = fxmlLoader.getController();
                createAdminController.fillForm(selectedUser);

                editStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
