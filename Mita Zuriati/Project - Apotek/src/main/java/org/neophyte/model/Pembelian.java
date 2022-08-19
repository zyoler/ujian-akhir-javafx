package org.neophyte.model;

public class Pembelian {
    private int id;
    private String idPembelian;
    private String idSupplier;
    private String tglPembelian;
    private String idObat;
    private int qty;
    private int total;
    private int totalBayar;

    public Pembelian(int id, String idPembelian, String idSupplier, String tglPembelian, String idObat, int qty, int total) {
        this.id = id;
        this.idPembelian = idPembelian;
        this.idSupplier = idSupplier;
        this.tglPembelian = tglPembelian;
        this.idObat = idObat;
        this.qty = qty;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setTglPembelian(String tglPembelian) {
        this.tglPembelian = tglPembelian;
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
