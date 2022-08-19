package indomarco.controllers.layouts;

import indomarco.Main;
import indomarco.controllers.log.DaftarController;
import indomarco.controllers.log.MasukController;
import indomarco.models.User;
import indomarco.util.Pemberitahuan;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class HeaderController {
    public TextField pencarian;
    public Button user; // label pengguna
    public Button log; // masuk / keluar
    public static User userLogin = null;

    public void initialize(){
        if(userLogin != null){
            log.setText("Keluar");
            user.setText(userLogin.getNamaLengkap());
        }else {
            log.setText("Masuk");
            user.setText("Pengguna");
        }
    }
    public void logAksi() throws IOException {
        if(userLogin != null){
            if (Pemberitahuan.keluar()){
                userLogin = null;
                Main.restart();
            }
        }else {
            Stage masuk = new Stage();
            MasukController.log = masuk;
            DaftarController.log = masuk;
            masuk.initOwner(Main.window);
            masuk.initModality(Modality.WINDOW_MODAL);
            masuk.setResizable(false);
            masuk.setTitle("Silahkan masuk");
            Scene masukDaftarScene = new Scene(FXMLLoader.load(getClass().getResource("/views/masuk.fxml")));
            masuk.setScene(masukDaftarScene);
            masuk.show();
        }
    }

}
