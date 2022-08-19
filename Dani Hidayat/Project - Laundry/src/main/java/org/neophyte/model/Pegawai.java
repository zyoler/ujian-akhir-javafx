package org.neophyte.model;

public class Pegawai {
    private String id;
    private String nama;
    private String alamat;
    private String usia;
    private String jenisKelamin;
    private String noTelp;

    public Pegawai(String id, String nama, String alamat, String usia, String jenisKelamin, String noTelp) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.usia = usia;
        this.jenisKelamin = jenisKelamin;
        this.noTelp = noTelp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getUsia() {
        return usia;
    }

    public void setUsia(String usia) {
        this.usia = usia;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
}
