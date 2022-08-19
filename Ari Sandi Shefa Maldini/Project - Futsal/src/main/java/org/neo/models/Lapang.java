package org.neo.models;

public class Lapang {
    int idlpng;
    String namalpng;
    String jenislpng;
    int harga;
    String keterangan;
    String gambar;

    public Lapang(int idlpng, String namalpng, String jenislpng, int harga, String keterangan, String gambar) {
        this.idlpng = idlpng;
        this.namalpng = namalpng;
        this.jenislpng = jenislpng;
        this.harga = harga;
        this.keterangan = keterangan;
        this.gambar = gambar;
    }

    public int getIdlpng() {
        return idlpng;
    }

    public void setIdlpng(int idlpng) {
        this.idlpng = idlpng;
    }

    public String getNamalpng() {
        return namalpng;
    }

    public void setNamalpng(String namalpng) {
        this.namalpng = namalpng;
    }

    public String getJenislpng() {
        return jenislpng;
    }

    public void setJenislpng(String jenislpng) {
        this.jenislpng = jenislpng;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
