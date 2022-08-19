package org.rental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.rental.model.Admin;
import org.rental.util.Database;

public class Main extends Application {
    public static Stage stage;
    public static Admin admin;
    public static Admin loggedInUser;

    public static void refresh(){

        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/main.fxml"));
            Scene scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.setTitle("Rental Mobil");
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void login () {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/login.fxml"));
            Scene sceneLogin = new Scene(root,700,500);
            stage.setScene(sceneLogin);
            stage.setTitle("Rental Mobil Login");
            stage.show();
        }catch(Exception e){
//            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        new Database();
        stage = primaryStage;
        login();


    }
}