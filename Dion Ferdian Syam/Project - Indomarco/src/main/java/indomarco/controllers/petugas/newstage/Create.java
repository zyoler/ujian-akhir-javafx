package indomarco.controllers.petugas.newstage;

import indomarco.controllers.petugas.konten.Index;
import indomarco.models.pasiens.PasienTerdaftar;
import indomarco.models.petugas.Layanan;
import indomarco.models.petugas.LayananTerdaftar;
import indomarco.models.petugas.PengecekanPasien;
import indomarco.util.Pemberitahuan;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Create {
    public TextField nik;
    public TextField namaPasien;
    public TextField suhu;
    public TextField tensi;
    public ComboBox<LayananTerdaftar> layanan;

    public PasienTerdaftar pasien;
    public static Stage create;

    public Create(PasienTerdaftar pasien) {
        this.pasien = pasien;
    }

    public void initialize(){
        loadLayanan();
    }

    public void loadLayanan(){
        LayananTerdaftar layanan = new LayananTerdaftar();
        this.layanan.setItems(layanan.allLayanan());

        namaPasien.setText(pasien.getNamaPasien());
        nik.setText(pasien.getNik());
    }
    public void store(){
        LayananTerdaftar layananDipilih = layanan.getSelectionModel().getSelectedItem();
        if (layananDipilih != null){
            int idLayanan = Character.getNumericValue(layananDipilih.toString().charAt(0));
            LayananTerdaftar layananTerdaftar = new LayananTerdaftar(idLayanan);
            int suhu = Integer.parseInt(this.suhu.getText());
            String tensi = this.tensi.getText();
            PengecekanPasien cek = new PengecekanPasien(suhu, tensi);
            pasien.setLayanan(layananTerdaftar);
            pasien.setKesehatan(cek);
            pasien.storePasienCek();
            Pemberitahuan.berhasilDaftar();
            Index.staticReload();
            create.close();
        }
    }
}
