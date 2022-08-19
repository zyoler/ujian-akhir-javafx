package indomarco.controllers.log;

import indomarco.Main;
import indomarco.controllers.layouts.HeaderController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import indomarco.models.User;

import java.sql.ResultSet;

public class MasukController {
    public static Stage log;
    public TextField namaPengguna;
    public PasswordField kataSandi;

    public void daftarView(){
        try{
        Scene daftarScene = new Scene(FXMLLoader.load(getClass().getResource("/views/daftar.fxml")));
        log.setScene(daftarScene);
        log.setTitle("Daftar akun");
        log.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void masukAksi(){
        String namaPengguna = this.namaPengguna.getText();
        String kataSandi = this.kataSandi.getText();

        User akunMasuk = new User();
        ResultSet akunLogin = akunMasuk.cekLogin(namaPengguna, kataSandi);
        if (akunLogin != null){
            log.close();
            akunMasuk.setDataResult(akunLogin);
            HeaderController.userLogin = akunMasuk;
            Main.restart();
        }
        resetForm();
    }
    public void resetForm(){
        namaPengguna.clear();
        kataSandi.clear();
    }
}
