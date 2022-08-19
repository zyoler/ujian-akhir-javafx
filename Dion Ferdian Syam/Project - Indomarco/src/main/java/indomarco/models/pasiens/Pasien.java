package indomarco.models.pasiens;

import indomarco.models.petugas.LayananTerdaftar;
import indomarco.models.petugas.PengecekanPasien;
import indomarco.util.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class Pasien {
    protected Connection connection = new Database().connection;
    protected String table = "pasiens";
    protected PreparedStatement statement;

    public ResultSet all() throws SQLException {
        String query = "SELECT * FROM pasiens";
        PreparedStatement stmt = connection.prepareStatement(query);
        return stmt.executeQuery();
    }
    public ResultSet allNow() throws SQLException {
        String query = "SELECT i.*, j.* FROM pasiens i, pendaftaran j WHERE i.nik = j.nik AND j.waktu = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setDate(1, Date.valueOf(LocalDate.now()));
        return stmt.executeQuery();
    }
    public ResultSet allNowProses() throws SQLException {
        String query = "SELECT i.*, j.* FROM pasiens i, pendaftaran j WHERE i.nik = j.nik AND j.waktu = ? AND j.keterangan = false";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setDate(1, Date.valueOf(LocalDate.now()));
        return stmt.executeQuery();
    }
    public ObservableList<PasienTerdaftar> allPasien(){
        try{
            ObservableList<PasienTerdaftar> data = FXCollections.observableArrayList();
            ResultSet result = all();
            int no = 0;
            while (result.next()){
                PasienTerdaftar terdaftar = new PasienTerdaftar(++no, result.getString("nik"), result.getString("nama_lengkap"), result.getDate("tanggal_lahir").toLocalDate(), result.getDate("tanggal_daftar").toLocalDate());
                data.add(terdaftar);
            }
            return data;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    public ObservableList<PasienTerdaftar> allNowPasien(){
        try{
            ObservableList<PasienTerdaftar> data = FXCollections.observableArrayList();
            ResultSet result = allNow();
            int no = 0;
            String ket;
            while (result.next()){
                ket = (result.getBoolean("keterangan")) ? "Diproses" : "Menunggu";
                PasienTerdaftar terdaftar = new PasienTerdaftar(++no, result.getInt("id_pasien"),result.getString("nik"), result.getString("nama_Lengkap"), ket);
                data.add(terdaftar);
            }
            return data;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    public ObservableList<PasienTerdaftar> allNowPasienProses(){
        try{
            ObservableList<PasienTerdaftar> data = FXCollections.observableArrayList();
            ResultSet result = allNowProses();
            int no = 0;
            String ket;
            while (result.next()){
                ket = (result.getBoolean("keterangan")) ? "Diproses" : "Menunggu";
                PasienTerdaftar terdaftar = new PasienTerdaftar(++no, result.getInt("id_pasien"),result.getString("nik"), result.getString("nama_Lengkap"), ket);
                data.add(terdaftar);
            }
            return data;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    public int pasienTerdaftar(){
        try {
            String query = "SELECT count (*) as result FROM pasiens";
            statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt("result");
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public int pasienBelumCek(){
        try {
            String query = "SELECT count (*) as result FROM pendaftaran WHERE keterangan = ? AND waktu = ?";
            statement = connection.prepareStatement(query);
            statement.setBoolean(1, false);
            statement.setDate(2, Date.valueOf(LocalDate.now()));
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt("result");
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    //pasienAfterCek
    public ResultSet pasienTungguPanggilanDokter(){
        try{
            String query = "SELECT i.*, j.*, k.*, l.* FROM pasien_cek i, pendaftaran j, pasiens k, layanan l WHERE i.id_pasien = j.id_pasien AND j.nik = k.nik AND i.id_layanan = l.id_layanan AND j.waktu = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            return stmt.executeQuery();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    public ObservableList<PasienTerdaftar> dataTablePasienTungguPanggilanDokter(){
        try{
            ObservableList<PasienTerdaftar> data = FXCollections.observableArrayList();
            ResultSet result = pasienTungguPanggilanDokter();
            int no = 0;
            while (result.next()){
                LayananTerdaftar layanan = new LayananTerdaftar(result.getInt("id_layanan"), result.getString("nama_layanan"), result.getInt("harga_layanan"));
                PengecekanPasien cek = new PengecekanPasien(result.getInt("id_cek"), result.getInt("suhu_pasien"), result.getString("tensi_pasien"), (result.getBoolean("keterangan") ? "Diproses.." : "Menunggu.."));
                PasienTerdaftar pasien = new PasienTerdaftar(++no ,result.getInt("id_pasien"), result.getString("nik"), result.getString("nama_lengkap"), result.getDate("tanggal_lahir").toLocalDate(), cek, layanan);
                data.add(pasien);
            }
            return data;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}
