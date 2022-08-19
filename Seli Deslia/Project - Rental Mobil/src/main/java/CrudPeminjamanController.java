import javafx.scene.control.TableView;
import org.rental.model.Mobil;
import org.rental.util.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class CrudPeminjamanController {

    public static TableView<Mobil> staticMobilTable;


    public static void loadData(){
        staticMobilTable.getItems().setAll();
        try {

            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from mobil");
            while (resultSet.next()) {

                staticMobilTable.getItems().add(new Mobil(resultSet.getString("id_mobil"), resultSet.getString("merk_mobil"), resultSet.getString("no_polisi"), resultSet.getInt("harga_sewa"), resultSet.getString("tipe_mobil"), resultSet.getString("tahun_pembuatan"), resultSet.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        loadData();
    }
}

