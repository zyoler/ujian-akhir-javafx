package indomarco.controllers.log;

import indomarco.util.Validasi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import indomarco.models.User;
import indomarco.util.Pemberitahuan;

public class DaftarController {
    public static Stage log;
    public TextField namaLengkap;
    public TextField namaPengguna;
    public PasswordField kataSandi;
    public ComboBox<String> tipe = new ComboBox<>();
    public void initialize(){
        ObservableList<String> dataAkun = FXCollections.observableArrayList("resepsionis", "petugas", "dokter");
        tipe.setItems(dataAkun);
    }
    public void masukView(){
        try{
            Scene daftarScene = new Scene(FXMLLoader.load(getClass().getResource("/views/masuk.fxml")));
            log.setScene(daftarScene);
            log.setTitle("Silahkan masuk");
            log.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void daftar(){
        String namaLengkap = this.namaLengkap.getText();
        String namaPengguna = this.namaPengguna.getText();
        String kataSandi = this.kataSandi.getText();
        String tipe = this.tipe.getSelectionModel().getSelectedItem();
        try {
            User akunBaru = new User();
            if (Validasi.empty(namaLengkap)|Validasi.empty(namaPengguna)|Validasi.empty(kataSandi)){
                throw new Exception();
            }
            akunBaru.setData(namaLengkap, namaPengguna, kataSandi, tipe);
            try {
                akunBaru.simpanData();
                Pemberitahuan.berhasilDaftar();
                masukView();
            }catch (Exception e){
                Pemberitahuan.gagalDaftar();
            }
        }catch (Exception e){
            Pemberitahuan.gagalDaftar();
        }
    }
}
