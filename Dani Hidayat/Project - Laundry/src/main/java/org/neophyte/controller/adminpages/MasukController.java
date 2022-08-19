package org.neophyte.controller.adminpages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.neophyte.controller.Validation;
import org.neophyte.model.Barang;
import org.neophyte.model.DetailPesanan;
import org.neophyte.model.Pelanggan;
import org.neophyte.util.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class MasukController {

    public static TableView<Pelanggan> staticPelangganTable;
    public static TableView<Barang> staticBarangTable;
    public static TableView<DetailPesanan> staticDetailTable;

    public TableView<Pelanggan> pelangganTable;
    public TableView<Barang> barangTable;
    public TableView<DetailPesanan> detailTable;

    public TextField namaPlgField;
    public TextField alamatPlgField;
    public TextField telpPlgField;
    public TextField namaBrgField;
    public TextField hargaBrgField;
    public TextField jumlahField;
    public TextField totalHargaField;

    public Button pilihButton;
    public Button simpanButton;
    public Button batalButton;

    public static int nampungId;
    public static String cekPelangganBox = null;
    public static String cekIdTransaksi = null;

    public ComboBox<String> pelangganBox;
    public ComboBox<String> barangBox;
    public DatePicker tglPlgDate;

    public static void loadData() {
        staticBarangTable.getItems().setAll();
        staticDetailTable.getItems().setAll();
        staticPelangganTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rsBarang = statement.executeQuery("SELECT * FROM barang");
            while (rsBarang.next()){
                staticBarangTable.getItems().add(new Barang(rsBarang.getString("kode"),rsBarang.getString("nama"),rsBarang.getInt("harga")));
            }
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pelanggan WHERE proses_cucian = ?");
            preparedStatement.setString(1,"Kosong");
            ResultSet rsPelanggan = preparedStatement.executeQuery();
            while (rsPelanggan.next()){
                staticPelangganTable.getItems().add(new Pelanggan(rsPelanggan.getString("id"),rsPelanggan.getString("nama"),rsPelanggan.getString("alamat"),rsPelanggan.getString("no_telp"),rsPelanggan.getDate("tgl_daftar"),rsPelanggan.getString("proses_cucian")));
            }
            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM detail_pesanan where id_pelanggan = ?");
            preparedStatement2.setString(1,cekPelangganBox);
            ResultSet rsDetail = preparedStatement2.executeQuery();
            while (rsDetail.next()){
                staticDetailTable.getItems().add(new DetailPesanan(rsDetail.getString("id_pelanggan"),rsDetail.getString("kode_brg"),rsDetail.getInt("jumlah")));
            }
            Statement statement3 = connection.createStatement();
            ResultSet resultSet = statement3.executeQuery("SELECT id FROM transaksi");
            nampungId = 1;
            while (resultSet.next()){
                nampungId = Integer.parseInt(resultSet.getString("id").substring(3)) + 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buildData() {
        ObservableList<String> data = FXCollections.observableArrayList();
        ObservableList<String> data2 = FXCollections.observableArrayList();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pelanggan WHERE proses_cucian = ?");
            preparedStatement.setString(1, "Kosong");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                data.add(resultSet.getString("id"));
            }
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM barang");
            while (resultSet2.next()) {
                data2.add(resultSet2.getString("kode"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pelangganBox.setItems(data);
        barangBox.setItems(data2);
    }

    public void initialize(){
        validation();
        cekPelangganBox = null;
        staticPelangganTable = pelangganTable;
        staticBarangTable = barangTable;
        staticDetailTable = detailTable;
        hapusFieldPelanggan();hapusFieldBarang();
        loadData();buildData();cekID();

        LocalDate localDate = LocalDate.now();
        tglPlgDate.setValue(localDate);

        pelangganBox.setOnAction(event -> {
            String selectedItem = pelangganBox.getSelectionModel().getSelectedItem();
            cekPelangganBox = selectedItem;
            loadData();
            try{
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pelanggan WHERE id = ?");
                preparedStatement.setString(1, selectedItem);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()){
                    namaPlgField.setText(rs.getString("nama"));
                    alamatPlgField.setText(rs.getString("alamat"));
                    telpPlgField.setText(rs.getString("no_telp"));
                }

                PreparedStatement preparedStatement3 = connection.prepareStatement("SELECT (d.jumlah*b.harga) AS jumlah FROM detail_pesanan d, barang b WHERE d.kode_brg = b.kode AND d.id_pelanggan = ?");
                preparedStatement3.setString(1, selectedItem);
                ResultSet resultSet = preparedStatement3.executeQuery();
                int nampungTotal = 0;
                while (resultSet.next()){
                    nampungTotal += resultSet.getInt("jumlah");
                }
                totalHargaField.setText(String.valueOf(nampungTotal));
                
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        barangBox.setOnAction(event -> {
            String selectedItem = barangBox.getSelectionModel().getSelectedItem();
            try{
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM barang WHERE kode = ?");
                preparedStatement.setString(1, selectedItem);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()){
                    namaBrgField.setText(rs.getString("nama"));
                    hargaBrgField.setText(String.valueOf(rs.getInt("harga")));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        totalHargaField.setText("0");
        pilihButton.setOnAction(event -> {
            if(!Objects.equals(barangBox.getValue(), "") && !Objects.equals(pelangganBox.getValue(), "") && (!Objects.equals(jumlahField.getText(), ""))){
                totalHargaField.setText(String.valueOf(Integer.parseInt(totalHargaField.getText()) + Integer.parseInt(jumlahField.getText()) * Integer.parseInt(hargaBrgField.getText())));
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO detail_pesanan VALUES (?,?,?)");
                    preparedStatement.setString(1, pelangganBox.getValue());
                    preparedStatement.setString(2, barangBox.getValue());
                    preparedStatement.setInt(3, Integer.parseInt(jumlahField.getText()));
                    preparedStatement.executeUpdate();
                    loadData();hapusFieldBarang();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        simpanButton.setOnAction(event -> {
            LocalDate hariIni = LocalDate.now();
            if(Objects.equals(pelangganBox.getValue(), "") || !Objects.equals(barangBox.getValue(), "") || Objects.equals(totalHargaField.getText(), "0")){
                hapusFieldBarang();
            }else{
                try{
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transaksi (id, id_pelanggan, tgl_transaksi,total_harga) VALUES (?,?,?,?)");
                    preparedStatement.setString(1, cekIdTransaksi);
                    preparedStatement.setString(2, pelangganBox.getValue());
                    preparedStatement.setDate(3, Date.valueOf(hariIni));
                    preparedStatement.setInt(4, Integer.parseInt(totalHargaField.getText()));
                    preparedStatement.executeUpdate();

                    PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE pelanggan SET proses_cucian = ? WHERE id = ?");
                    preparedStatement1.setString(1,"Proses");
                    preparedStatement1.setString(2,pelangganBox.getValue());
                    preparedStatement1.executeUpdate();

                    loadData();cekID();buildData();hapusFieldPelanggan();hapusFieldBarang();totalHargaField.setText("0");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        batalButton.setOnAction(event -> {
            hapusFieldPelanggan();hapusFieldBarang();
        });
    }

    public void cekID(){
        if(nampungId < 10)
            cekIdTransaksi = "TRS00" + nampungId;
        else if(nampungId < 100)
            cekIdTransaksi = "TRS0" + nampungId;
        else
            cekIdTransaksi =  "TRS" + nampungId;
    }

    public void validation() {
        Validation.numericOnly(jumlahField);
        Validation.addTextLimiter(jumlahField,4);
    }

    public void hapusFieldBarang(){
        jumlahField.setText("");barangBox.setValue("");hargaBrgField.setText("");namaBrgField.setText("");
    }

    public void hapusFieldPelanggan(){
        pelangganBox.setValue("");namaPlgField.setText("");alamatPlgField.setText("");telpPlgField.setText("");
    }
}
