package org.neophyte.model;

import java.sql.Date;
import java.sql.Time;

public class Transaksi {
    private int idTransaksi;
    private Date tanggal;
    private int jumlahTiket;
    private int idUser;
    private String namaUser;
    private String kdeTiket;
    private int harga;
    private String alamatCustomer;
    private String namaCustomer;
    private String kodeTayang;
    private String tittle;
    private Time jamTayang;
    private int total;
    private String keterangan;

    public Transaksi(int idTransaksi, Date tanggal, int jumlahTiket, int idUser, String namaUser, String kdeTiket, int harga, String alamatCustomer, String namaCustomer, String kodeTayang, String tittle, Time jamTayang, int total, String keterangan) {
        this.idTransaksi = idTransaksi;
        this.tanggal = tanggal;
        this.jumlahTiket = jumlahTiket;
        this.idUser = idUser;
        this.namaUser = namaUser;
        this.kdeTiket = kdeTiket;
        this.harga = harga;
        this.alamatCustomer = alamatCustomer;
        this.namaCustomer = namaCustomer;
        this.kodeTayang = kodeTayang;
        this.tittle = tittle;
        this.jamTayang = jamTayang;
        this.total = total;
        this.keterangan = keterangan;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(int jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getKdeTiket() {
        return kdeTiket;
    }

    public void setKdeTiket(String kdeTiket) {
        this.kdeTiket = kdeTiket;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getAlamatCustomer() {
        return alamatCustomer;
    }

    public void setAlamatCustomer(String alamatCustomer) {
        this.alamatCustomer = alamatCustomer;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public String getKodeTayang() {
        return kodeTayang;
    }

    public void setKodeTayang(String kodeTayang) {
        this.kodeTayang = kodeTayang;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Time getJamTayang() {
        return jamTayang;
    }

    public void setJamTayang(Time jamTayang) {
        this.jamTayang = jamTayang;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
