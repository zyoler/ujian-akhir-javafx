package indomarco.controllers.resepsionis.hariini;

import indomarco.Main;
import indomarco.models.pasiens.Pasien;
import indomarco.models.pasiens.PasienTerdaftar;
import indomarco.util.Pemberitahuan;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


public class PasienHariIniController {
    public TableView<PasienTerdaftar> tablePendaftar;
    public TableColumn<PasienTerdaftar, Integer> no;
    public TableColumn<PasienTerdaftar, String> kodePasien;
    public TableColumn<PasienTerdaftar, String> namaLengkap;
    public TableColumn<PasienTerdaftar, String> keterangan;

    PasienTerdaftar pasien = new PasienTerdaftar();
    public static TableView<PasienTerdaftar> staticTablePendaftar;

    public Label sub;
    public Label pasienHariIni;
    public static Boolean daftar = false;

    public void initialize(){
        String sub = "Data pasien masuk hari ini, " + LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        this.sub.setText(sub);
        no.setCellValueFactory(new PropertyValueFactory<>("no"));
        kodePasien.setCellValueFactory(new PropertyValueFactory<>("kodePasien"));
        namaLengkap.setCellValueFactory(new PropertyValueFactory<>("namaPasien"));
        keterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
        try{
            resetData();
            staticTablePendaftar = tablePendaftar;
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void create(){
        try {
            Stage pasienStage = new Stage();
            PasienCreateController.create = pasienStage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/resepsionis/create-pasien.fxml"));
            pasienStage.setScene(new Scene(loader.load()));
            pasienStage.initOwner(Main.window);
            pasienStage.initModality(Modality.WINDOW_MODAL);
            pasienStage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void destroy(){
        pasien = tablePendaftar.getSelectionModel().getSelectedItem();
        if (pasien != null) {
            if(Pemberitahuan.hapusData(pasien.getNamaPasien())) {
                try {
                    pasien.destroyPendaftaran();
                    System.out.println("Berhasil");
                    resetData();
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }else {
            System.out.println("kosong");
        }
    }
    public void resetData(){
        this.pasienHariIni.setText("Pasien daftar hari ini : "+ pasien.num_rows() + " Orang");
        tablePendaftar.setItems(new Pasien().allNowPasien());
    }
}
