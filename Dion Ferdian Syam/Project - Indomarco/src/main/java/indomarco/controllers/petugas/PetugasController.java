package indomarco.controllers.petugas;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;


public class PetugasController {
    public HBox main;

    public static String aktifMenu = "index";
    public static HBox staticMainResepsionis;

    public void initialize(){
        staticMainResepsionis = main;
        setMain();
    }

    public void antrian(){
        aktifMenu = "index";
        setMain();
    }
    public void sudahCek(){
        aktifMenu = "antrian";
        setMain();
    }

    public void setMain(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/petugas/konten/" + aktifMenu + ".fxml"));
            VBox vBox = loader.load();
            staticMainResepsionis.getChildren().setAll(vBox);
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}
