package org.neophyte.model;

public class Buku {

    private int id;
    private String judulbuku;
    private String penulis;
    private String penerbit;
    private String tahunterbit;
    private String kategori;
    private int harga;

    public Buku(int id, String judulbuku, String penulis, String penerbit, String tahunterbit, String kategori, int harga) {
        this.id = id;
        this.judulbuku = judulbuku;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahunterbit = tahunterbit;
        this.kategori = kategori;
        this.harga = harga;
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

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getTahunterbit() {
        return tahunterbit;
    }

    public void setTahunterbit(String tahunterbit) {
        this.tahunterbit = tahunterbit;
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
}
