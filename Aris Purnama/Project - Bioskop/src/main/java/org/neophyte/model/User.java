package org.neophyte.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String namaUser;
    private boolean admin;

    public User(int id, String username, String password, String namaUser, boolean admin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.namaUser = namaUser;
        this.admin = admin;
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

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
