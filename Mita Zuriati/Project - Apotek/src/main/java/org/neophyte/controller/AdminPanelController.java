package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminPanelController {


    public static TableView<User> staticUserTable;

    public TableView<User> userTable;
    public Button createButton;
    public Button editButton;
    public Button deleteButton;
    public Button kembaliButton;
    User selectedUser;

    public static void loadData() {
        staticUserTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                staticUserTable.getItems().add(new User(resultSet.getInt("id"),  resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("name"), resultSet.getBoolean("admin")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void initialize() {
        staticUserTable = userTable;
        loadData();

        createButton.setOnAction(event -> {
            try {
                Parent vBox = FXMLLoader.load(getClass().getResource("/crud/create_user.fxml"));
                Scene createScene = new Scene(vBox);
                Stage createStage = new Stage();
                createStage.setScene(createScene);
                createStage.initOwner(userTable.getScene().getWindow());
                createStage.initModality(Modality.WINDOW_MODAL);
                createStage.setResizable(false);
                createStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnAction(event -> {
            selectedUser = userTable.getSelectionModel().getSelectedItem();

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
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Pilih yang ingin di hapus");
                alert.show();
            }
        });
        kembaliButton.setOnAction(event -> {
            ((Stage) kembaliButton.getScene().getWindow()).close();
            Main.refresh();
        });
    }

    public void edit() {
        selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            try {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Pilih yang ingin di edit");
            alert.show();
        }
    }
}
