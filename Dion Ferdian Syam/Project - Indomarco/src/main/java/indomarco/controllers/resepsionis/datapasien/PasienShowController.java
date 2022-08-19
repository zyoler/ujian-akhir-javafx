package indomarco.controllers.resepsionis.datapasien;

import indomarco.models.pasiens.PasienTerdaftar;
import indomarco.util.Pemberitahuan;
import javafx.scene.control.*;

import java.time.LocalDate;

import static indomarco.util.Convert.formatTanggal;

public class PasienShowController {
    public Label tanggalDaftar;
    public TextField nik;
    public TextField namaLengkap;
    public DatePicker tanggalLahir;

    String isNamaLengkap;
    LocalDate isTanggalLahir;

    public Button btnHapus;
    public Button btnUpdate;
    public static PasienTerdaftar pasien;

    boolean update = false;

    public TableView<PasienTerdaftar> tableDiagnosa;
    public TableColumn<PasienTerdaftar, String> tanggalMasuk;
    public TableColumn<PasienTerdaftar, String> diagnosa;


    public void initialize(){

        tanggalLahir.setValue(pasien.getTanggalLahir());
        tanggalDaftar.setText(formatTanggal(pasien.getTanggalDaftar()));
        namaLengkap.setText(pasien.getNamaPasien());
        nik.setText(pasien.getNik());
        refresh();
    }

    public void destroy(){
        if (!update){
            if(Pemberitahuan.hapusData(pasien.getNamaPasien())) {
                System.out.println("Berhasil");
                pasien.destroy();
                DataPasienController.restartData();
                DataPasienController.showPasienStage.close();
                Pemberitahuan.berhasilDestroy();
            }
        }else {
            tanggalLahir.setValue(isTanggalLahir);
            namaLengkap.setText(isNamaLengkap);
            setBtn();
        }
    }
    public void update(){
        if (update){
            updateAksi();
            DataPasienController.restartData();
        }else {
            enableEdit();
        }
        setBtn();
    }
    public void enableEdit(){
        namaLengkap.setEditable(true);
        namaLengkap.setPromptText(namaLengkap.getText());
        namaLengkap.clear();
        tanggalLahir.setEditable(true);
        tanggalLahir.setPromptText(tanggalLahir.getValue().toString());
    }
    public void setBtn(){
        if (update){
            btnHapus.setText("Hapus data");
            tanggalLahir.setEditable(false);
            namaLengkap.setEditable(false);
        }else {
            btnHapus.setText("Kembali");
        }
        update = !update;
    }

    public void updateAksi(){
        if (!this.namaLengkap.getText().equals("")){
            pasien.setNamaPasien(this.namaLengkap.getText());
        }
        pasien.setTanggalLahir(this.tanggalLahir.getValue());
        pasien.update();
        refresh();
        Pemberitahuan.berhasilUpdate();
    }
    public void refresh(){
        tanggalLahir.setEditable(false);
        namaLengkap.setEditable(false);
        tanggalDaftar.setText(formatTanggal(pasien.getTanggalDaftar()));
        namaLengkap.setText(pasien.getNamaPasien());
        isNamaLengkap = pasien.getNamaPasien();
        isTanggalLahir = pasien.getTanggalLahir();
    }

}
