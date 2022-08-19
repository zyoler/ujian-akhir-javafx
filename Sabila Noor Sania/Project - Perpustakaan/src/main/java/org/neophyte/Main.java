package org.neophyte;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.neophyte.model.User;
import org.neophyte.util.Database;


import java.io.IOException;

public class Main extends Application {
    public static User loggedInUser;
    //  public static User loggedInUser = null;
  private static Stage stage;

    public static void refresh() throws IOException {
        HBox root = FXMLLoader.load(Main.class.getResource("/MainLogin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Halaman Utama");
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Database();
        stage = primaryStage;
        refresh();

    }
    public static Stage getStage() {
        return stage;
    }
}
