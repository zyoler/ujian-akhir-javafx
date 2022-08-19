package org.neophyte.model;

public class Peminjaman {
    private int nomor;
    private int id;
    private String judulbuku;
    private int idanggota;
    private String nama;
    private String tgl_pinjam;
    private String tgl_kembali;

    public Peminjaman(int nomor, int id, String judulbuku, int idanggota, String nama, String tgl_pinjam, String tgl_kembali) {
        this.nomor = nomor;
        this.id = id;
        this.judulbuku = judulbuku;
        this.idanggota = idanggota;
        this.nama = nama;
        this.tgl_pinjam = tgl_pinjam;
        this.tgl_kembali = tgl_kembali;
    }

    public int getNomor() {
        return nomor;
    }

    public void setNomor(int nomor) {
        this.nomor = nomor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudulbuku() {
        return judulbuku;
    }

    public void setJudulbuku(String judulbuku) {
        this.judulbuku = judulbuku;
    }

    public int getIdanggota() {
        return idanggota;
    }

    public void setIdanggota(int idanggota) {
        this.idanggota = idanggota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTgl_pinjam() {
        return tgl_pinjam;
    }

    public void setTgl_pinjam(String tgl_pinjam) {
        this.tgl_pinjam = tgl_pinjam;
    }

    public String getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }
}
