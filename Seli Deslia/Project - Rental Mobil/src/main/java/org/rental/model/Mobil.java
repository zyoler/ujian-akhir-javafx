package org.rental.model;

public class Mobil {
    private String id_mobil;
    private String merk_mobil;
    private String no_polisi;
    private int harga_sewa;
    private String tipe_mobil;
    private String tahun_pembuatan;
    private String status;

    public Mobil(String id_mobil, String merk_mobil, String no_polisi, int harga_sewa, String tipe_mobil, String tahun_pembuatan, String status) {
        this.id_mobil = id_mobil;
        this.merk_mobil = merk_mobil;
        this.no_polisi = no_polisi;
        this.harga_sewa = harga_sewa;
        this.tipe_mobil = tipe_mobil;
        this.tahun_pembuatan = tahun_pembuatan;
        this.status = status;
    }

    public String getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(String id_mobil) {
        this.id_mobil = id_mobil;
    }

    public String getMerk_mobil() {
        return merk_mobil;
    }

    public void setMerk_mobil(String merk_mobil) {
        this.merk_mobil = merk_mobil;
    }

    public String getNo_polisi() {
        return no_polisi;
    }

    public void setNo_polisi(String no_polisi) {
        this.no_polisi = no_polisi;
    }

    public int getHarga_sewa() {
        return harga_sewa;
    }

    public void setHarga_sewa(int harga_sewa) {
        this.harga_sewa = harga_sewa;
    }

    public String getTipe_mobil() {
        return tipe_mobil;
    }

    public void setTipe_mobil(String tipe_mobil) {
        this.tipe_mobil = tipe_mobil;
    }

    public String  getTahun_pembuatan() {
        return tahun_pembuatan;
    }

    public void setTahun_pembuatan(String tahun_pembuatan) {
        this.tahun_pembuatan = tahun_pembuatan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
