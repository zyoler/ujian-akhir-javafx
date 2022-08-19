package org.neo.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.neo.models.Admin;

public class Main extends Application {
    private static Stage stage;
    public static Admin admLogged = null;

    public static void refresh(){
        try{
            HBox hBox = FXMLLoader.load(Main.class.getResource("/menu.fxml"));
            Scene scene = new Scene(hBox);
            stage.setTitle("Fun Futsal");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        refresh();
    }

    public static Stage getStage(){
        return stage;
    }
}
