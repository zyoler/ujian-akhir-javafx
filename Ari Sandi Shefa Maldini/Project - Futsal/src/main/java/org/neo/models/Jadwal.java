package org.neo.models;

public class Jadwal {
    int id_jadwal;
    String nama;
    String no_hp;
    String tgl_main;
    String pukul;
    String lapang;
    int id_booking;

    public Jadwal(int id_jadwal, String nama, String no_hp, String tgl_main, String pukul, String lapang, int id_booking) {
        this.id_jadwal = id_jadwal;
        this.nama = nama;
        this.no_hp = no_hp;
        this.tgl_main = tgl_main;
        this.pukul = pukul;
        this.lapang = lapang;
        this.id_booking = id_booking;
    }

    public int getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(int id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getTgl_main() {
        return tgl_main;
    }

    public void setTgl_main(String tgl_main) {
        this.tgl_main = tgl_main;
    }

    public String getPukul() {
        return pukul;
    }

    public void setPukul(String pukul) {
        this.pukul = pukul;
    }

    public String getLapang() {
        return lapang;
    }

    public void setLapang(String lapang) {
        this.lapang = lapang;
    }

    public int getId_booking() {
        return id_booking;
    }

    public void setId_booking(int id_booking) {
        this.id_booking = id_booking;
    }
}
