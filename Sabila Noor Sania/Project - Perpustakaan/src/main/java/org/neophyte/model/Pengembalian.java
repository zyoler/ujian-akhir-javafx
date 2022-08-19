package org.neophyte.model;

import java.time.LocalDate;

public class Pengembalian {
    private int no;
    private int nomor_peminjaman;
    private String judul_buku;
    private String nama;
    private String tgl_pinjam;
    private String tgl_kembali;
    private String tgl_dikembalikan;
    private int denda_perhari;
    private int total_denda;

    public Pengembalian(int no, int nomor_peminjaman, String judul_buku, String nama, String tgl_pinjam, String tgl_kembali, String tgl_dikembalikan, int denda_perhari, int total_denda) {
        this.no = no;
        this.nomor_peminjaman = nomor_peminjaman;
        this.judul_buku = judul_buku;
        this.nama = nama;
        this.tgl_pinjam = tgl_pinjam;
        this.tgl_kembali = tgl_kembali;
        this.tgl_dikembalikan = tgl_dikembalikan;
        this.denda_perhari = denda_perhari;
        this.total_denda = total_denda;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getNomor_peminjaman() {
        return nomor_peminjaman;
    }

    public void setNomor_peminjaman(int nomor_peminjaman) {
        this.nomor_peminjaman = nomor_peminjaman;
    }

    public String getJudul_buku() {
        return judul_buku;
    }

    public void setJudul_buku(String judul_buku) {
        this.judul_buku = judul_buku;
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

    public String getTgl_dikembalikan() {
        return tgl_dikembalikan;
    }

    public void setTgl_dikembalikan(String tgl_dikembalikan) {
        this.tgl_dikembalikan = tgl_dikembalikan;
    }

    public int getDenda_perhari() {
        return denda_perhari;
    }

    public void setDenda_perhari(int denda_perhari) {
        this.denda_perhari = denda_perhari;
    }

    public int getTotal_denda() {
        return total_denda;
    }

    public void setTotal_denda(int total_denda) {
        this.total_denda = total_denda;
    }
}
