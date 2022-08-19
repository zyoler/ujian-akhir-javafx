package indomarco.models;

import indomarco.util.Database;
import indomarco.util.Pemberitahuan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class User {
    Connection User;
    String namaLengkap;
    String namaPengguna;
    String kataSandi;
    String tipe;
    private final String table = "users";
    public User(){
        Database userModel = new Database();
        User = userModel.connection;
    }
    public void setData(String namaLengkap, String namaPengguna, String kataSandi, String tipe){
        this.namaLengkap = namaLengkap;
        this.namaPengguna = namaPengguna;
        this.kataSandi = kataSandi;
        this.tipe = tipe;
    }
    public void simpanData() throws SQLException {
            String query = "INSERT INTO " + this.table + " (nama_pengguna, kata_sandi, tipe, nama_lengkap) VALUES (?,?,?,?)";
            PreparedStatement pstmt = User.prepareStatement(query);
            pstmt.setString(1, namaPengguna);
            pstmt.setString(2, kataSandi);
            pstmt.setString(3, tipe);
            pstmt.setString(4, namaLengkap);
            pstmt.execute();
    }
    public ResultSet cekLogin(String namaPengguna, String kataSandi){
        try{
            String query = "SELECT * FROM " + this.table + " WHERE nama_pengguna = ?";
            PreparedStatement pstmt = User.prepareStatement(query);
            pstmt.setString(1, namaPengguna);
            ResultSet result = pstmt.executeQuery();
            result.next();
            if(result.getRow() == 1){
                if(Objects.equals(result.getString("kata_sandi"), kataSandi)){
                    Pemberitahuan.berhasilLogin(result.getString("nama_lengkap"));
                    return result;
                }else {
                    Pemberitahuan.gagalLogin("Kata sandi anda salah!");
                    return null;
                }
            }
            else {
                Pemberitahuan.gagalLogin("Akun anda tidak terdaftar!");
                return null;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getTipe() {
        return tipe;
    }

    public void setDataResult(ResultSet result){
        try {
            String namaLengkap = result.getString("nama_lengkap");
            String namaPengguna = result.getString("nama_pengguna");
            String kataSandi = result.getString("kata_sandi");
            String tipe = result.getString("tipe");

            this.namaLengkap = namaLengkap;
            this.namaPengguna = namaPengguna;
            this.kataSandi = kataSandi;
            this.tipe = tipe;
        }catch (Exception e){
            System.out.println(e);
            return;
        }
    }

}
