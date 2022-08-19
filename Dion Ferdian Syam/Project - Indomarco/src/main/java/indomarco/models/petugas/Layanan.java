package indomarco.models.petugas;

import indomarco.models.pasiens.PasienTerdaftar;
import indomarco.util.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Layanan {
    protected Connection connection = new Database().connection;
    protected String table = "layanan";
    protected PreparedStatement statement;

    public ResultSet all(){
        try{
            String query = "SELECT * FROM " + this.table;
            statement = connection.prepareStatement(query);
            return statement.executeQuery();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    public ObservableList<LayananTerdaftar> allLayanan(){
        try{
            ObservableList<LayananTerdaftar> data = FXCollections.observableArrayList();
            ResultSet result = all();
            while (result.next()){
                LayananTerdaftar terdaftar = new LayananTerdaftar(result.getInt("id_layanan"), result.getString("nama_layanan"), result.getInt("harga_layanan"));
                data.add(terdaftar);
            }
            return data;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}
