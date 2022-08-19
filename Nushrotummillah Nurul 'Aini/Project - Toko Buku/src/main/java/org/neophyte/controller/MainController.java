package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.util.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class MainController {
    public Button login;
    public Button user;
    static boolean log = false;
    public HBox books;
    public Button orderButton;
    public Button historyButton;

    public void initialize(){

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book where gambar is not null ");
            while (resultSet.next()) {
                VBox buku = new VBox();
                buku.getStyleClass().add("books");

                ImageView gambar = new ImageView(new Image(resultSet.getString("gambar")));
                gambar.setFitWidth(128);
                gambar.setFitHeight(196);
                int tahun = resultSet.getInt("tahun");
                Label Tahun = new Label(" (" + tahun + ')');
                Label kategori = new Label(resultSet.getString("kategori"));

                buku.getChildren().setAll(gambar, kategori, Tahun);

                books.getChildren().add(buku);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!log){
            login.setText("Login");
            orderButton.setDisable(true);
            historyButton.setDisable(true);
            user.setDisable(true);
        }else {
            login.setText("Logout");
        }

        login.setOnAction(event -> {
            if(!log){
                VBox vBox = null;
                try {
                    vBox = FXMLLoader.load(getClass().getResource("/login.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage window = new Stage();
                Scene loginScene = new Scene(vBox);
                window.setScene(loginScene);

                window.initOwner(Main.getStage());
                window.initModality(Modality.WINDOW_MODAL);
                window.setResizable(false);

                window.show();
            }else{
                log = false;
                refresh();
            }
        });

        user.setOnAction(event -> {
                try {
                    Stage window = Main.getStage();
                    VBox root = FXMLLoader.load(MainController.class.getResource("/crud_user.fxml"));
                    Scene scene = new Scene(root);
                    window.setScene(scene);
                    window.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    }

    public static void refresh(){
        try {
            Stage window = Main.getStage();
            VBox vBox = FXMLLoader.load(MainController.class.getResource("/main.fxml"));
            Scene scene = new Scene(vBox);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void productAction() {
        try {
            Stage window = Main.getStage();
            VBox root = FXMLLoader.load(MainController.class.getResource("/crud_product.fxml"));
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void orderAction() {
        try {
            Stage window = Main.getStage();
            VBox root = FXMLLoader.load(MainController.class.getResource("/crud_order.fxml"));
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void historyAction() {
        try {
            Stage window = Main.getStage();
            VBox root = FXMLLoader.load(MainController.class.getResource("/history.fxml"));
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
