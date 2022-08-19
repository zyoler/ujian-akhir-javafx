package org.rental.model;

public class Admin {
    private int id;
    private String username;
    private String password;
    private String nama_admin;

    public Admin(int id, String username, String password, String nama_admin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nama_admin = nama_admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama_admin() {
        return nama_admin;
    }

    public void setNama_admin(String nama_admin) {
        this.nama_admin = nama_admin;
    }
}
