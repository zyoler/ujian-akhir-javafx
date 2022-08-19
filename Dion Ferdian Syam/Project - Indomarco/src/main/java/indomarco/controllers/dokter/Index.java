package indomarco.controllers.dokter;

import indomarco.models.dokter.Diagnosa;
import indomarco.models.pasiens.PasienTerdaftar;
import indomarco.util.Convert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.Period;

public class Index {
    public Label labelNIK;
    public Label labelNama;
    public Label labelLahir;
    public Label labelUsia;
    public Label labelLayanan;
    public Label labelSuhu;
    public Label labelTensi;

    public TextArea pesan;
    public TextField diagnosa;

    PasienTerdaftar pasien;

    public Index(PasienTerdaftar pasien) {
        this.pasien = pasien;
    }

    public void initialize(){
        this.labelNama.setText(pasien.getNamaPasien());
        this.labelNIK.setText(pasien.getNik());
        this.labelLahir.setText(Convert.formatTanggal(pasien.getTanggalLahir()));
        this.labelUsia.setText(String.valueOf(Period.between(pasien.getTanggalLahir(), LocalDate.now()).getYears()));
        this.labelLayanan.setText(pasien.getLayanan().getNamaLayanan());
        this.labelSuhu.setText(String.valueOf(pasien.getKesehatan().getSuhu()));
        this.labelTensi.setText(pasien.getKesehatan().getTensi());
    }

    public void simpanKeterangan(){
        String diagnosa = this.diagnosa.getText();
        String ketDiagnosa = this.pesan.getText();
        Diagnosa diagnosaClass = new Diagnosa(diagnosa, ketDiagnosa);
        this.pasien.setDiagnosa(diagnosaClass);
        this.pasien.storeDiagnosa();
    }
}
