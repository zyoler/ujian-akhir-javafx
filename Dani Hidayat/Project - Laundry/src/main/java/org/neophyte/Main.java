package org.neophyte;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;

    public static void refresh() {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/pages/login_user.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("styles/style.css");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        new Database();
        stage = primaryStage;
        refresh();
    }
}
