package indomarco.models.pasiens;

import indomarco.models.dokter.Diagnosa;
import indomarco.models.petugas.LayananTerdaftar;
import indomarco.models.petugas.PengecekanPasien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class PasienTerdaftar extends Pasien {
    int no;
    int idPasien;
    String kodePasien;
    String nik;
    String namaPasien;
    String namaPendek;
    LocalDate tanggalLahir;
    LocalDate tanggalDaftar;
//    String layanan;
    String keterangan;

    PengecekanPasien kesehatan;
    LayananTerdaftar layanan;
    Diagnosa diagnosa;

    public Diagnosa getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(Diagnosa diagnosa) {
        this.diagnosa = diagnosa;
    }

    public PasienTerdaftar(int no, int idPasien, String nik, String namaPasien, LocalDate tanggalLahir, PengecekanPasien kesehatan, LayananTerdaftar layanan) {
        this.no = no;
        this.idPasien = idPasien;
        this.nik = nik;
        this.namaPasien = namaPasien;
        this.tanggalLahir = tanggalLahir;
        this.kesehatan = kesehatan;
        this.layanan = layanan;
        this.namaPendek =(namaPasien.contains(" ")) ? namaPasien.substring(0, namaPasien.indexOf(" ")) : namaPasien;
    }

    public String getNamaPendek() {
        return namaPendek;
    }

    public PasienTerdaftar(int no, int idPasien, String namaPasien, PengecekanPasien kesehatan, LayananTerdaftar layanan) {
        this.no = no;
        this.idPasien = idPasien;
        this.namaPasien = namaPasien;
        this.kesehatan = kesehatan;
        this.layanan = layanan;
    }

    public PasienTerdaftar() {
    }

    public PasienTerdaftar(String nik, String namaPasien, LocalDate tanggalLahir) {
        this.nik = nik;
        this.namaPasien = namaPasien;
        this.tanggalLahir = tanggalLahir;
    }

    public PasienTerdaftar(int no, int idPasien, String nik,String namaPasien, String keterangan) {
        this.no = no;
        this.idPasien = idPasien;
        this.nik = nik;
        this.namaPasien = namaPasien;
        this.keterangan = keterangan;
        this.kodePasien = "ID" + String.format("%1$4s", idPasien).replace(' ', '0');
    }

    public int num_rows(){
        int num = 0;
        try{
            String query = "SELECT count(*) as num FROM pendaftaran WHERE waktu = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            ResultSet result = stmt.executeQuery();
            result.next();
            num = result.getInt("num");
        }catch (Exception e){
            System.out.println("Banyak data");
        }
        return num;
    }

    public PengecekanPasien getKesehatan() {
        return kesehatan;
    }

    public void setKesehatan(PengecekanPasien kesehatan) {
        this.kesehatan = kesehatan;
    }

    public LayananTerdaftar getLayanan() {
        return layanan;
    }

    public void setLayanan(LayananTerdaftar layanan) {
        this.layanan = layanan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public PasienTerdaftar(int no, String nik, String namaPasien, LocalDate tanggalLahir, LocalDate tanggalDaftar) {
        this.no = no;
        this.nik = nik;
        this.namaPasien = namaPasien;
        this.tanggalLahir = tanggalLahir;
        this.tanggalDaftar = tanggalDaftar;
    }

    //crud

    public void destroy(){
        String query = "DELETE FROM " + table + " WHERE nik = ?";
        String query2 = "DELETE FROM pendaftaran WHERE nik = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            PreparedStatement statement2 = connection.prepareStatement(query2);
            statement.setString(1, this.nik);
            statement2.setString(1, this.nik);

            statement2.executeUpdate();
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void destroyPendaftaran(){
        try {
            String query2 = "DELETE FROM pendaftaran WHERE id_pasien = ?";
            PreparedStatement statement2 = connection.prepareStatement(query2);
            statement2.setInt(1, this.idPasien);
            statement2.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void store(){
        String query1 = "INSERT INTO pasiens (nik, nama_lengkap, tanggal_lahir) VALUES (?, ?, ?)";
        String query2 = "INSERT INTO pendaftaran (nik) VALUES (?)";
        try{
            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setString(1, this.nik);
            statement.setString(2, this.namaPasien);
            statement.setDate(3, Date.valueOf(this.tanggalLahir));
            statement.executeUpdate();
            try {
                statement = connection.prepareStatement(query2);
                statement.setString(1, this.nik);
                statement.executeUpdate();
            }catch (Exception e){
                System.out.println(e);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void storePendaftaran(){
        String query2 = "INSERT INTO pendaftaran (nik) VALUES (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query2);
            statement.setString(1, this.nik);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void storePasienCek(){
        try{
            String query = "INSERT INTO pasien_cek (id_pasien, suhu_pasien, tensi_pasien, id_layanan) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.getIdPasien());
            statement.setInt(2, this.getKesehatan().getSuhu());
            statement.setString(3, this.getKesehatan().getTensi());
            statement.setInt(4, this.layanan.getIdLayanan());
            statement.executeUpdate();
            try{
                String query1 = "UPDATE pendaftaran SET keterangan = ? WHERE id_pasien = ?";
                PreparedStatement statement1 = connection.prepareStatement(query1);
                statement1.setBoolean(1, true);
                statement1.setInt(2, this.getIdPasien());
                statement1.executeUpdate();
            }catch (Exception e){
                System.out.println(e);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void update(){
        String query = "UPDATE " + table + " SET nama_lengkap = ?, tanggal_lahir = ? WHERE nik = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, this.namaPasien);
            statement.setDate(2, Date.valueOf(this.tanggalLahir));
            statement.setString(3, this.nik);
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void storeDiagnosa(){
        int idCek = this.kesehatan.getIdCek();
        String diagnosa = this.diagnosa.getDiagnosa();
        String ketDiagnosa = this.diagnosa.getKeterangan();

        try {
            String query = "INSERT INTO detail_pasien (id_cek, diagnosa, keterangan) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idCek);
            stmt.setString(2, diagnosa);
            stmt.setString(3, ketDiagnosa);
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("gagal");
        }


    }

    public int getIdPasien(){
        try {
            String query = "SELECT id_pasien FROM pendaftaran WHERE nik = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, this.nik);
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt("id_pasien");
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public LocalDate getTanggalDaftar() {
        return tanggalDaftar;
    }
    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public PasienTerdaftar(String nik) {
        this.nik = nik;
    }
    public void setTanggalDaftar(LocalDate tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public void setNik(String nik) {
        this.nik = nik;
    }
    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }
    public int getNo() {
        return no;
    }
    public String getNik() {
        return nik;
    }
    public String getNamaPasien() {
        return namaPasien;
    }
    public String getKodePasien() {
        return kodePasien;
    }
    public ObservableList<PasienTerdaftar> allDiagnosa(){
        try {
            ObservableList<PasienTerdaftar> data = FXCollections.observableArrayList();
            String query = "SELECT dp.*, p.* FROM detail_pasien dp, pasien_cek pc, pendaftaran p WHERE p.nik = ? AND p.id_pasien = pc.id_pasien AND pc.id_cek = dp.id_cek";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, this.nik);
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                Diagnosa diagnosa = new Diagnosa(result.getString("diagnosa"), result.getString("keterangan"), result.getDate("dibenarkan").toLocalDate());
                setDiagnosa(diagnosa);
            }
        }catch (Exception e){
            System.out.println("Diagnosa error");
        }
        return null;
    }
}
