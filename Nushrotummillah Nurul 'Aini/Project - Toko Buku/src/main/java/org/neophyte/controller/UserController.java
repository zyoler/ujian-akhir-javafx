package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserController {

    public static TableView<User> staticUserTable;
    public TableView<User> userTable;

    public Button backButton;
    public static Button addButton;
    public static Button editButton;
    public static Button deleteButton;

    User selectedUser;

    private static String maskPass(String password) {
        String output = "";
        for(int i = 0; i < password.length(); i++) {
            output += "\u2022";
        }
        return output;
    }

    public static void loadData() {
        staticUserTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");


            while (resultSet.next()) {
                String pass = resultSet.getString("password");
                String hidePass = maskPass(pass);

                staticUserTable.getItems().add(new User(resultSet.getInt("id"), resultSet.getString("nama"), resultSet.getString("username"), hidePass, resultSet.getBoolean("admin")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        staticUserTable = userTable;
        loadData();

        if(!(MainController.log)){
            addButton.setDisable(true);
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }
    public void backButton() {
        MainController.refresh();
    }

    public void addButton() {
        try {
            VBox vBox = FXMLLoader.load(getClass().getResource("/create_user.fxml"));
            Scene addScene = new Scene(vBox);
            Stage addStage = new Stage();
            addStage.setScene(addScene);
            addStage.initOwner(userTable.getScene().getWindow());
            addStage.initModality(Modality.WINDOW_MODAL);
            addStage.setResizable(false);
            addStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editButton() {
        selectedUser = userTable.getSelectionModel().getSelectedItem();
        if(selectedUser != null){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                VBox vBox = fxmlLoader.load(getClass().getResource("/create_user.fxml").openStream());
                Scene editScene = new Scene(vBox);
                Stage editStage = new Stage();
                editStage.setScene(editScene);
                editStage.initOwner(userTable.getScene().getWindow());
                editStage.initModality(Modality.WINDOW_MODAL);
                editStage.setResizable(false);
                CreateUserController createUserController = fxmlLoader.getController();
                createUserController.fillForm(selectedUser);
                editStage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tidak ada data yang dipilih");
            alert.setHeaderText(null);
            alert.setContentText("Tidak ada data yang dipilih. Pilih data untuk diperbarui");
            alert.show();
        }
    }

    public void deleteButton() {
        selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus User");
            alert.setHeaderText(null);
            alert.setContentText("Anda yakin ingin menghapus " + selectedUser.getUsername() + "?");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
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
            alert.setTitle("Tidak ada data yang dipilih");
            alert.setHeaderText(null);
            alert.setContentText("Tidak ada data yang dipilih. Pilih data untuk dihapus.");
            alert.show();
        }
    }
}
