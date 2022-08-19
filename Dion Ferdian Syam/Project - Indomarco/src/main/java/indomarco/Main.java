package indomarco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    public static Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/master.fxml"));
        Scene berandaScene = new Scene(loader.load());
        window.setTitle("Indomarco");
        window.setScene(berandaScene);
        window.show();
    }

    public static void restart(){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/master.fxml"));
            Scene berandaScene = new Scene(loader.load());
            window.setTitle("Indomarco");
            window.setScene(berandaScene);
            window.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
