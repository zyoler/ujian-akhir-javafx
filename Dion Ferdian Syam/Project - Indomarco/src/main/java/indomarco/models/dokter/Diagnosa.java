package indomarco.models.dokter;

import java.time.LocalDate;

public class Diagnosa {
    String diagnosa;
    String keterangan;
    LocalDate waktu;

    public Diagnosa(String diagnosa, String keterangan, LocalDate waktu) {
        this.diagnosa = diagnosa;
        this.keterangan = keterangan;
        this.waktu = waktu;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Diagnosa(String diagnosa, String keterangan) {
        this.diagnosa = diagnosa;
        this.keterangan = keterangan;
    }
}
