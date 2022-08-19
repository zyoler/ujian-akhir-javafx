package indomarco.controllers.resepsionis.datapasien;

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


public class DataPasienController {

    public TableView<PasienTerdaftar> pasienTableView;
    public TableColumn<PasienTerdaftar, Integer> no;
    public TableColumn<PasienTerdaftar, String> nik;
    public TableColumn<PasienTerdaftar, String> namaLengkap;
    public TableColumn<PasienTerdaftar, LocalDate> tanggalDaftar;
    public TableColumn<PasienTerdaftar, LocalDate> tanggalLahir;
    public Label pasienTerdaftar;
    public static Label staticPasienTerdaftar;

    public static Stage showPasienStage;
    public static TableView<PasienTerdaftar> staticTable;
    PasienTerdaftar pasien = new PasienTerdaftar();

    public void initialize(){
        staticTable = pasienTableView;
        staticPasienTerdaftar = pasienTerdaftar;
        no.setCellValueFactory(new PropertyValueFactory<PasienTerdaftar, Integer>("no"));
        nik.setCellValueFactory(new PropertyValueFactory<PasienTerdaftar, String>("nik"));
        namaLengkap.setCellValueFactory(new PropertyValueFactory<PasienTerdaftar, String>("namaPasien"));
        tanggalDaftar.setCellValueFactory(new PropertyValueFactory<PasienTerdaftar, LocalDate>("tanggalDaftar"));
        tanggalLahir.setCellValueFactory(new PropertyValueFactory<PasienTerdaftar, LocalDate>("tanggalLahir"));
        pasienTableView.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2){
                pasien = pasienTableView.getSelectionModel().getSelectedItem();
                show(pasien);
            }
        });
        restartData();
    }
    public static void restartData(){
        Pasien pasienModel = new Pasien();
        staticTable.getItems().setAll(pasienModel.allPasien());
        staticPasienTerdaftar.setText("Total seluruh pasien terdaftar : " + String.valueOf(pasienModel.pasienTerdaftar()) + " Orang");
    }
    public void show(PasienTerdaftar pasien){
        if (pasien != null) {
        showPasienStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/resepsionis/show-pasien.fxml"));
            try{
                PasienShowController.pasien = pasien;
                showPasienStage.setTitle("Pasien detail");
                showPasienStage.setResizable(false);
                showPasienStage.initOwner(Main.window);
                showPasienStage.initModality(Modality.WINDOW_MODAL);
                showPasienStage.setScene(new Scene(loader.load()));
                showPasienStage.show();

            }catch (Exception e){
                System.out.println(e.getLocalizedMessage());
            }
        }else {
            System.out.println("kosong");
        }
    }
}
