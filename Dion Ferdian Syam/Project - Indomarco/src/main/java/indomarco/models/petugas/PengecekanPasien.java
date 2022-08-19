package indomarco.models.petugas;

public class PengecekanPasien {
    int idCek;
    int suhu;
    String tensi;
    String keterangan;

    public int getIdCek() {
        return idCek;
    }

    public void setIdCek(int idCek) {
        this.idCek = idCek;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public PengecekanPasien(int idCek, int suhu, String tensi, String keterangan) {
        this.idCek = idCek;
        this.suhu = suhu;
        this.tensi = tensi;
        this.keterangan = keterangan;
    }

    public PengecekanPasien(int idCek, int suhu, String tensi) {
        this.idCek = idCek;
        this.suhu = suhu;
        this.tensi = tensi;
    }

    public PengecekanPasien(int suhu, String tensi) {
        this.suhu = suhu;
        this.tensi = tensi;
    }

    public int getSuhu() {
        return suhu;
    }

    public void setSuhu(int suhu) {
        this.suhu = suhu;
    }

    public String getTensi() {
        return tensi;
    }

    public void setTensi(String tensi) {
        this.tensi = tensi;
    }
}
