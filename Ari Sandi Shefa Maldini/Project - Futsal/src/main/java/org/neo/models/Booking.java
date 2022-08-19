package org.neo.models;

public class Booking {
    int id_booking;
    String nama;
    String no_hp;
    String tgl_pesan;
    String tgl_main;
    int lama;
    String pukul;
    int total;
    int id_lapang;
    String status;

    public Booking(int id_booking, String nama, String no_hp, String tgl_pesan, String tgl_main, int lama, String pukul, int total, int id_lapang, String status) {
        this.id_booking = id_booking;
        this.nama = nama;
        this.no_hp = no_hp;
        this.tgl_pesan = tgl_pesan;
        this.tgl_main = tgl_main;
        this.lama = lama;
        this.pukul = pukul;
        this.total = total;
        this.id_lapang = id_lapang;
        this.status = status;
    }

    public Booking(int id_booking, String nama, String no_hp, String tgl_pesan, String tgl_main, int lama, String pukul, int total, int id_lapang) {
        this.id_booking = id_booking;
        this.nama = nama;
        this.no_hp = no_hp;
        this.tgl_pesan = tgl_pesan;
        this.tgl_main = tgl_main;
        this.lama = lama;
        this.pukul = pukul;
        this.total = total;
        this.id_lapang = id_lapang;
    }

    public int getId_booking() {
        return id_booking;
    }

    public void setId_booking(int id_booking) {
        this.id_booking = id_booking;
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

    public String getTgl_pesan() {
        return tgl_pesan;
    }

    public void setTgl_pesan(String tgl_pesan) {
        this.tgl_pesan = tgl_pesan;
    }

    public String getTgl_main() {
        return tgl_main;
    }

    public void setTgl_main(String tgl_main) {
        this.tgl_main = tgl_main;
    }

    public int getLama() {
        return lama;
    }

    public void setLama(int lama) {
        this.lama = lama;
    }

    public String getPukul() {
        return pukul;
    }

    public void setPukul(String pukul) {
        this.pukul = pukul;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getId_lapang() {
        return id_lapang;
    }

    public void setId_lapang(int id_lapang) {
        this.id_lapang = id_lapang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
