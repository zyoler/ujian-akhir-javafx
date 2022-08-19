package org.rental.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.rental.Main;
import org.rental.model.Admin;
import org.rental.util.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class CrudAdminController {

    public static TableView<Admin> staticAdminTable;
    public TableView<Admin> adminTable;
    public Button tambahButton;
    public Button hapusButton;
    public Button editButton;
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField nameField;
    public Button kembaliButton;


    public static void loadDAta() {
        staticAdminTable.getItems().setAll();
        try {

            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from admin");
            while (resultSet.next()) {

                staticAdminTable.getItems().add(new Admin(resultSet.getInt("id_admin"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("nama_admin")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        staticAdminTable = adminTable;
        loadDAta();

        hapusButton.setOnAction(event -> {
            Admin selectedAdmin = adminTable.getSelectionModel().getSelectedItem();

            if(selectedAdmin != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus Pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah anda ingin menghapus " + selectedAdmin.getNama_admin() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM admin where id=?");
                        preparedStatement.setInt(1, selectedAdmin.getId());
                        preparedStatement.executeUpdate();
                        loadDAta();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        tambahButton.setOnAction(event -> {
            VBox  vBox = null;
            try {
                vBox = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/createAdmin.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene createShene = new Scene(vBox);
            Stage createStage = new Stage();
            createStage.setScene(createShene);
            createStage.initOwner(adminTable.getScene().getWindow());
            createStage.initModality(Modality.WINDOW_MODAL);
            createStage.show();
        });

        kembaliButton.setOnAction(event -> {
            Main.refresh();
        });

        editButton.setOnAction(event -> {
            Admin selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
            if(selectedAdmin != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    VBox vBox = fxmlLoader.load(getClass().getResource("/createAdmin.fxml").openStream());
                    Scene editScene = new Scene(vBox);
                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    editStage.initOwner(adminTable.getScene().getWindow());
                    editStage.initModality(Modality.WINDOW_MODAL);

                    CreateAdminController createAdminController = fxmlLoader.getController();
                    createAdminController.fillForm(selectedAdmin);
                    editStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
