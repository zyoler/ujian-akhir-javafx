package org.neophyte.model;

public class History {
    String id;
    String judul;
    int harga;
    int jumlah;
    int total;

    public History(String id, String judul, int harga, int jumlah, int total) {
        this.id = id;
        this.judul = judul;
        this.harga = harga;
        this.jumlah = jumlah;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
