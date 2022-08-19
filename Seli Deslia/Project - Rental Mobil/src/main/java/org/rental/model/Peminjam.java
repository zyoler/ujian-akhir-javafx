package org.rental.model;

import java.util.Date;

public class Peminjam {
    private String id_transaksi;
    private String nama_peminjam;
    private String alamat;
    private String no_telp;
    private String email;
    private final String tanggal_pinjam;
    private final String tanggal_kembali;
    private int lama_pinjam;
    private int harga;
    private String id_mobil;

    public Peminjam(String id_transaksi, String nama_peminjam, String alamat, String no_telp, String email, String tanggal_pinjam, String tanggal_kembali, int lama_pinjam, int total, String id_mobil) {
        this.id_transaksi = id_transaksi;
        this.nama_peminjam = nama_peminjam;
        this.alamat = alamat;
        this.no_telp = no_telp;
        this.email = email;
        this.tanggal_pinjam = tanggal_pinjam;
        this.tanggal_kembali = tanggal_kembali;
        this.lama_pinjam = lama_pinjam;
        this.harga = total;
        this.id_mobil = id_mobil;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getNama_peminjam() {
        return nama_peminjam;
    }

    public void setNama_peminjam(String nama_peminjam) {
        this.nama_peminjam = nama_peminjam;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTanggal_pinjam() {
        return tanggal_pinjam;
    }



    public String getTanggal_kembali() {
        return tanggal_kembali;
    }


    public int getLama_pinjam() {
        return lama_pinjam;
    }

    public void setLama_pinjam(int lama_pinjam) {
        this.lama_pinjam = lama_pinjam;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int total) {
        this.harga = total;
    }

    public String getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(String id_mobil) {
        this.id_mobil = id_mobil;
    }
}
