package org.neophyte;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.neophyte.model.User;
import org.neophyte.utils.DataBase;

import java.io.IOException;

public class Main extends Application {
    public static Stage skunderStage;
    public static Stage stage;
    public static User logginInadmin = null;
    public static String cek = null;
    public  static void refresh() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/login.fxml"));
        Scene scene = new Scene(root);
        skunderStage.setScene(scene);
        skunderStage.setTitle("Login");
        skunderStage.setResizable(false);
        skunderStage.show();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        new DataBase();
        skunderStage=primaryStage;

        refresh();
    }

    public static Stage getMainStage() {
        return skunderStage;
    }

    public static void setSkunderStage(Stage skunderStage) {
        Main.skunderStage = skunderStage;
    }
}
