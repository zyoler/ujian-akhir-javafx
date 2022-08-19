package org.rental.model;

public class Transaksi {
    private String id_transaksi;
    private String nama_peminjam;
    private String alamat;
    private String tanggal_pinjam;
    private String tanggal_kembali;
    private int lama_pinjam;
    private int total;
    private int denda;
    private String id_mobil;

    public Transaksi(String id_transaksi, String nama_peminjam, String alamat, String tanggal_pinjam, String tanggal_kembali, int lama_pinjam, int total, int denda, String id_mobil) {
        this.id_transaksi = id_transaksi;
        this.nama_peminjam = nama_peminjam;
        this.alamat = alamat;
        this.tanggal_pinjam = tanggal_pinjam;
        this.tanggal_kembali = tanggal_kembali;
        this.lama_pinjam = lama_pinjam;
        this.total = total;
        this.denda = denda;
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

    public String getTanggal_pinjam() {
        return tanggal_pinjam;
    }

    public void setTanggal_pinjam(String tanggal_pinjam) {
        this.tanggal_pinjam = tanggal_pinjam;
    }

    public String getTanggal_kembali() {
        return tanggal_kembali;
    }

    public void setTanggal_kembali(String tanggal_kembali) {
        this.tanggal_kembali = tanggal_kembali;
    }

    public int getLama_pinjam() {
        return lama_pinjam;
    }

    public void setLama_pinjam(int lama_pinjam) {
        this.lama_pinjam = lama_pinjam;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDenda() {
        return denda;
    }

    public void setDenda(int denda) {
        this.denda = denda;
    }

    public String getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(String id_mobil) {
        this.id_mobil = id_mobil;
    }


}
