package indomarco.models.petugas;

import java.sql.ResultSet;

import static indomarco.util.Convert.rupiah;

public class LayananTerdaftar extends Layanan{
    int idLayanan;
    String namaLayanan;
    int hargaLayanan;

    public LayananTerdaftar(int idLayanan) {
        this.idLayanan = idLayanan;
        try{
            String query = "SELECT * FROM layanan WHERE id_layanan = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, idLayanan);
            ResultSet result = statement.executeQuery();
            result.next();
            this.hargaLayanan = result.getInt("harga_layanan");
            this.namaLayanan = result.getString("nama_layanan");
        }catch (Exception e){
            System.out.println(e);
        }
    }


    public int getIdLayanan() {
        return idLayanan;
    }

    public void setIdLayanan(int idLayanan) {
        this.idLayanan = idLayanan;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public int getHargaLayanan() {
        return hargaLayanan;
    }

    public void setHargaLayanan(int hargaLayanan) {
        this.hargaLayanan = hargaLayanan;
    }

    @Override
    public String toString() {
        return idLayanan + " - " + namaLayanan + " - " + rupiah(hargaLayanan);
    }

    public LayananTerdaftar() {
    }

    public LayananTerdaftar(int idLayanan, String namaLayanan, int hargaLayanan) {
        this.idLayanan = idLayanan;
        this.namaLayanan = namaLayanan;
        this.hargaLayanan = hargaLayanan;
    }
}
