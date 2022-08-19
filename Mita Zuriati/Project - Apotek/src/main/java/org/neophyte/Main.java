package org.neophyte;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.neophyte.model.User;
import org.neophyte.util.Database;


public class Main extends Application {

    public static User loggedInUser = null;
    private static Stage stage;


    public static void refresh() {
        try {
            VBox root = FXMLLoader.load(Main.class.getResource("/pages/home.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("style.css");
            stage.setScene(scene);
            stage.centerOnScreen();
            //primaryStage.initStyle(StageStyle.UNDECORATED);););
            stage.setTitle("Apotek");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        new Database();
        stage = primaryStage;
        HBox root = FXMLLoader.load(Main.class.getResource("/login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.centerOnScreen();
        //primaryStage.initStyle(StageStyle.UNDECORATED);););
        stage.setTitle("Apotek");
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}
