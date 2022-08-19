package org.neophyte.model;

public class Book {
    private int id;
    private String judul;
    private String pengarang;
    private String penerbit;
    private int tahun;
    private String gambar;
    private String deskripsi;
    private String kategori;
    private int harga;
    private int jumlah;

    public Book(int id, String judul, String pengarang, String penerbit, int tahun, String deskripsi, String kategori, int harga, int jumlah) {
        this.id = id;
        this.judul = judul;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.tahun = tahun;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public Book(int id, String judul, String pengarang, int tahun, String kategori, int harga, int jumlah) {
        this.id = id;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahun = tahun;
        this.kategori = kategori;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
