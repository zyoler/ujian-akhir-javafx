package org.neophyte.model;

public class TampViewPj {
        private String idPenjualan;
        private String tanggalPenjualan;
        private String idObat;
        private int qty;
        private int total;
        private int totalBayar;

    public TampViewPj(String idPenjualan, String tanggalPenjualan, String idObat, int qty, int total, int totalBayar) {
        this.idPenjualan = idPenjualan;
        this.tanggalPenjualan = tanggalPenjualan;
        this.idObat = idObat;
        this.qty = qty;
        this.total = total;
        this.totalBayar = totalBayar;
    }

    public String getIdPenjualan() {
        return idPenjualan;
    }

    public void setIdPenjualan(String idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public String getTanggalPenjualan() {
        return tanggalPenjualan;
    }

    public void setTanggalPenjualan(String tanggalPenjualan) {
        this.tanggalPenjualan = tanggalPenjualan;
    }

    public String getIdObat() {
        return idObat;
    }

    public void setIdObat(String idObat) {
        this.idObat = idObat;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(int totalBayar) {
        this.totalBayar = totalBayar;
    }
}

