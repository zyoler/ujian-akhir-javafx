package org.neophyte.model;

public class TampViewPb {
    private String idPembelian;
    private String idSupplier;
    private String tglPembelian;
    private String idObat;
    private int qty;
    private int total;
    private int totalBayar;

    public TampViewPb(String idPembelian, String idSupplier, String tglPembelian, String idObat, int qty, int total, int totalBayar) {
        this.idPembelian = idPembelian;
        this.idSupplier = idSupplier;
        this.tglPembelian = tglPembelian;
        this.idObat = idObat;
        this.qty = qty;
        this.total = total;
        this.totalBayar = totalBayar;
    }

    public String getIdPembelian() {
        return idPembelian;
    }

    public void setIdPembelian(String idPembelian) {
        this.idPembelian = idPembelian;
    }

    public String getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(String idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getTglPembelian() {
        return tglPembelian;
    }

    public void setTanggalPembelian(String tanggalPembelian) {
        this.tglPembelian = tanggalPembelian;
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

