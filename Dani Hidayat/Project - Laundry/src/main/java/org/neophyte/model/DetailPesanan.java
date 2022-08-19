package org.neophyte.model;

public class DetailPesanan {
    private String idPelanggan;
    private String kodeBarang;
    private int jumlah;

    public DetailPesanan(String idPelanggan, String kodeBarang, int jumlah) {
        this.idPelanggan = idPelanggan;
        this.kodeBarang = kodeBarang;
        this.jumlah = jumlah;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
