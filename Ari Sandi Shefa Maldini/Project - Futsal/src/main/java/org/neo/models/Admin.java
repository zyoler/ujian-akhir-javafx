package org.neo.models;

public class Admin {
    int id_admin;
    String fullName;
    String noHp;
    String username;
    String password;


    public Admin(String fullName, String username, String noHp) {
        this.fullName = fullName;
        this.username = username;
        this.noHp = noHp;
    }

    public Admin(int id_admin, String fullName, String noHp, String username, String password) {
        this.id_admin = id_admin;
        this.fullName = fullName;
        this.noHp = noHp;
        this.username = username;
        this.password = password;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
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
}
