package org.neophyte.model;

import java.sql.Date;

public class RiwayatTransaksi {
    private String id;
    private String idPelanggan;
    private Date tglTransaksi;
    private Date tglAmbil;
    private int totalHarga;
    private int bayar;
    private int kembalian;

    public RiwayatTransaksi(String id, String idPelanggan, Date tglTransaksi, Date tglAmbil, int totalHarga, int bayar, int kembalian) {
        this.id = id;
        this.idPelanggan = idPelanggan;
        this.tglTransaksi = tglTransaksi;
        this.tglAmbil = tglAmbil;
        this.totalHarga = totalHarga;
        this.bayar = bayar;
        this.kembalian = kembalian;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public Date getTglTransaksi() {
        return tglTransaksi;
    }

    public void setTglTransaksi(Date tglTransaksi) {
        this.tglTransaksi = tglTransaksi;
    }

    public Date getTglAmbil() {
        return tglAmbil;
    }

    public void setTglAmbil(Date tglAmbil) {
        this.tglAmbil = tglAmbil;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public int getBayar() {
        return bayar;
    }

    public void setBayar(int bayar) {
        this.bayar = bayar;
    }

    public int getKembalian() {
        return kembalian;
    }

    public void setKembalian(int kembalian) {
        this.kembalian = kembalian;
    }
}
