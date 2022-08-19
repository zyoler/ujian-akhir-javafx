package org.neophyte.model;

public class Employee {
    private int idEmploye;
    private String namaEmploye;
    private String alamatEmploye;
    private String noKontak;
    private String jabatan;

    public Employee(int idEmploye, String namaEmploye, String alamatEmploye, String noKontak, String jabatan) {
        this.idEmploye = idEmploye;
        this.namaEmploye = namaEmploye;
        this.alamatEmploye = alamatEmploye;
        this.noKontak = noKontak;
        this.jabatan = jabatan;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getNamaEmploye() {
        return namaEmploye;
    }

    public void setNamaEmploye(String namaEmploye) {
        this.namaEmploye = namaEmploye;
    }

    public String getAlamatEmploye() {
        return alamatEmploye;
    }

    public void setAlamatEmploye(String alamatEmploye) {
        this.alamatEmploye = alamatEmploye;
    }

    public String getNoKontak() {
        return noKontak;
    }

    public void setNoKontak(String noKontak) {
        this.noKontak = noKontak;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}
