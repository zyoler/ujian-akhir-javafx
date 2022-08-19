package indomarco.controllers;

import indomarco.Main;
import indomarco.controllers.layouts.HeaderController;
import indomarco.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    Stage baseStage;
    public VBox konten;
    User userLogin;

    public void initialize(){
        baseStage = Main.window;
        userLogin = HeaderController.userLogin;
        if (userLogin != null){
            try{
                setKonten(userLogin.getTipe());
            }catch (Exception e){
                System.out.println(e.getLocalizedMessage());
            }
        }else {
            System.out.println("Coming soon");
        }
    }
    public void setKonten(String tipe) throws IOException {
        String url;
        if (tipe.equals("resepsionis")){
            url = "/resepsionis/resepsionis.fxml";
        }else if(tipe.equals("dokter")){
            url = "/dokter/dokter.fxml";
        }else{
            url = "/petugas/petugas.fxml";
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views" + url));
        HBox load = loader.load();
        konten.setVgrow(load, Priority.ALWAYS);
        konten.getChildren().setAll(load);
    }
}
