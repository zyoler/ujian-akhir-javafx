package org.neophyte.model;

public class Tiket {
    private String kdTiket;
    private String JenisTiket;
    private int harga;

    public Tiket(String kdTiket, String jenisTiket, int harga) {
        this.kdTiket = kdTiket;
        JenisTiket = jenisTiket;
        this.harga = harga;
    }

    public String getKdTiket() {
        return kdTiket;
    }

    public void setKdTiket(String kdTiket) {
        this.kdTiket = kdTiket;
    }

    public String getJenisTiket() {
        return JenisTiket;
    }

    public void setJenisTiket(String jenisTiket) {
        JenisTiket = jenisTiket;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

}
