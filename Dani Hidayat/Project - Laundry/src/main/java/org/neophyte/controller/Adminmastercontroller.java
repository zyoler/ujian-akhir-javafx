package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.model.RiwayatTransaksi;
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.io.IOException;
import java.sql.*;

public class Adminmastercontroller {

    public static TableView<User> staticUserTable;
    public static TableView<RiwayatTransaksi> staticRiwayatTable;

    public Button editButton;
    public Button closeButton;
    public TableView<User> userTable;
    public TableView<RiwayatTransaksi> riwayatTable;

    User selectedUser;

    public static void loadData() {
        staticUserTable.getItems().setAll();
        staticRiwayatTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                staticUserTable.getItems().add(new User(resultSet.getInt("id"),resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("nama"),resultSet.getBoolean("active")));
            }
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM riwayat_transaksi");
            while (resultSet2.next()) {
                staticRiwayatTable.getItems().add(new RiwayatTransaksi(resultSet2.getString("id"),resultSet2.getString("id_pelanggan"), resultSet2.getDate("tgl_transaksi"), resultSet2.getDate("tgl_ambil"),resultSet2.getInt("total_harga"),resultSet2.getInt("bayar"),resultSet2.getInt("kembalian")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialize() {
        staticUserTable = userTable;
        staticRiwayatTable = riwayatTable;
        loadData();

        closeButton.setOnAction(event -> {
            ((Stage) closeButton.getScene().getWindow()).setIconified(true);
        });
    }

    public void edit() throws IOException {
        selectedUser = userTable.getSelectionModel().getSelectedItem();
        if(selectedUser != null) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            VBox vBox = fxmlLoader.load(getClass().getResource("/crud/create_user.fxml").openStream());
            Scene editScene = new Scene(vBox);
            Stage editStage = new Stage();
            editStage.setScene(editScene);
            editStage.initOwner(userTable.getScene().getWindow());
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.setResizable(false);
            CreateUserController createUserController = fxmlLoader.getController();
            createUserController.fillForm(selectedUser);
            editStage.show();
        }
    }
}
