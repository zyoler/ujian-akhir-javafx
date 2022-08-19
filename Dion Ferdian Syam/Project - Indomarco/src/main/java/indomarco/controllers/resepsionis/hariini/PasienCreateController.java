package indomarco.controllers.resepsionis.hariini;

import indomarco.controllers.resepsionis.ResepsionisController;
import indomarco.models.pasiens.PasienTerdaftar;
import indomarco.util.Pemberitahuan;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class PasienCreateController {
    public Label tanggalDaftar;
    public TextField nik;
    public TextField namaLengkap;
    public DatePicker tanggalLahir;

    public static Stage create;
    boolean sudahDaftar;

    public void initialize(){
        sudahDaftar = PasienHariIniController.daftar;
        System.out.println(sudahDaftar);
        String hariIni = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        //update combobox
//        ObservableList<String> item = FXCollections.observableArrayList("Rawat jalan / Poliklinik", "Rawat inap" ,"Gawat darurat");
//        layanan.setItems(item);
        create.setOnCloseRequest(e -> {
            PasienHariIniController.daftar = false;
        });
        tanggalDaftar.setText(hariIni);
    }
    public void store(){
        String nik = this.nik.getText();
        try {
            if(sudahDaftar){
                PasienTerdaftar pasienTerdaftar = new PasienTerdaftar(nik);
                pasienTerdaftar.storePendaftaran();
            }else {
                String namaLengkap = this.namaLengkap.getText();
                LocalDate tanggalLahir = this.tanggalLahir.getValue();
                PasienTerdaftar pasienBaru = new PasienTerdaftar(nik, namaLengkap, tanggalLahir);
                pasienBaru.store();
            }
            Pemberitahuan.berhasilDaftar();
            ResepsionisController.restart("pasien-hari-ini");
            create.close();
            PasienHariIniController.daftar = false;
        }catch (Exception e){
            System.out.println(e);
        }

    }
    public void change(){
        PasienHariIniController.daftar = !sudahDaftar;
        if(!sudahDaftar){
            setScene("create-pasien2");
        }else {
            setScene("create-pasien");
        }
    }
    public void setScene(String name){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/resepsionis/"+ name + ".fxml"));
        try{
            create.setScene(new Scene(loader.load()));
            create.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
