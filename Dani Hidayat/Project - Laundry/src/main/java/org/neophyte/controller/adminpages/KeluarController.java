package org.neophyte.controller.adminpages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.neophyte.controller.Adminmastercontroller;
import org.neophyte.controller.Validation;
import org.neophyte.model.DetailPesanan;
import org.neophyte.model.Pelanggan;
import org.neophyte.util.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class KeluarController {
    public static TableView<Pelanggan> staticSelesaiTable;
    public static TableView<DetailPesanan> staticDetailTable;
    public TableView<Pelanggan> selesaiTable;
    public TableView<DetailPesanan> detailTable;

    public TextField namaPlgField;
    public TextField alamatPlgField;
    public TextField telpPlgField;
    public TextField idTrsField;
    public TextField totalTrsField;
    public TextField bayarField;

    public Button saveButton;
    public Button batalButton;
    public Button bayarButton;

    public Label kembalianLabel;

    public ComboBox<String> pelangganBox;

    public DatePicker masukPlgDate;
    public DatePicker transaksiTrsDate;

    public static String staticSelectedItem = null;

    public void loadData() {
        staticSelesaiTable.getItems().setAll();
        staticDetailTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pelanggan WHERE proses_cucian = ?");
            preparedStatement.setString(1,"Selesai");
            ResultSet rsSelesai = preparedStatement.executeQuery();
            while (rsSelesai.next()){
                staticSelesaiTable.getItems().add(new Pelanggan(rsSelesai.getString("id"),rsSelesai.getString("nama"),rsSelesai.getString("alamat"),rsSelesai.getString("no_telp"),rsSelesai.getDate("tgl_daftar"),rsSelesai.getString("proses_cucian")));
            }
            PreparedStatement preparedStatement3 = connection.prepareStatement("SELECT * FROM detail_pesanan where id_pelanggan = ?");
            preparedStatement3.setString(1, staticSelectedItem);
            ResultSet rsDetail = preparedStatement3.executeQuery();
            while (rsDetail.next()){
                staticDetailTable.getItems().add(new DetailPesanan(rsDetail.getString("id_pelanggan"),rsDetail.getString("kode_brg"),rsDetail.getInt("jumlah")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void buildData() {
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pelanggan WHERE proses_cucian = ?");
            preparedStatement.setString(1, "Selesai");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                data.add(resultSet.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pelangganBox.setItems(data);
    }

    public void initialize() {
        validation();
        staticSelesaiTable = selesaiTable;
        staticDetailTable = detailTable;
        staticSelectedItem = null;
        loadData();buildData();

        pelangganBox.setOnAction(event -> {
            String selectedItem = pelangganBox.getSelectionModel().getSelectedItem();
            loadData();
            if(selectedItem!=null){
                try{
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pelanggan WHERE id = ?");
                    preparedStatement.setString(1, selectedItem);
                    ResultSet rs = preparedStatement.executeQuery();
                    while (rs.next()){
                        namaPlgField.setText(rs.getString("nama"));
                        alamatPlgField.setText(rs.getString("alamat"));
                        telpPlgField.setText(rs.getString("no_telp"));
                        masukPlgDate.setValue(LocalDate.parse(String.valueOf(rs.getDate("tgl_daftar"))));
                    }
                    PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT t.* from transaksi t, pelanggan p where t.id_pelanggan = p.id AND t.id_pelanggan = ?");
                    preparedStatement2.setString(1, selectedItem);
                    ResultSet resultSet = preparedStatement2.executeQuery();
                    while (resultSet.next()){
                        idTrsField.setText(resultSet.getString("id"));
                        transaksiTrsDate.setValue(LocalDate.parse(String.valueOf(resultSet.getDate("tgl_transaksi"))));
                        totalTrsField.setText(String.valueOf(resultSet.getInt("total_harga")));
                    }
                    staticSelectedItem = selectedItem;
                    loadData();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        bayarButton.setOnAction(event -> {
            if(!Objects.equals(bayarField.getText(), "") && !Objects.equals(totalTrsField.getText(), "")){
                int bayar = Integer.parseInt(bayarField.getText());
                int harga = Integer.parseInt(totalTrsField.getText());
                if(bayar < harga){
                    bayarField.setText("");
                }else{
                    int kembalian = bayar - harga;
                    kembalianLabel.setText(String.valueOf(kembalian));
                }
            }
        });
        saveButton.setOnAction(event -> {
            if(!Objects.equals(kembalianLabel.getText(), "")){
                LocalDate hariIni = LocalDate.now();
                try{
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE transaksi SET tgl_ambil = ?, bayar = ?, kembalian = ? WHERE id = ?");
                    preparedStatement.setDate(1, Date.valueOf(hariIni));
                    preparedStatement.setInt(2,Integer.parseInt(bayarField.getText()));
                    preparedStatement.setInt(3,Integer.parseInt(kembalianLabel.getText()));
                    preparedStatement.setString(4,idTrsField.getText());
                    preparedStatement.executeUpdate();

                    PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO riwayat_transaksi SELECT * FROM transaksi WHERE id = ?");
                    preparedStatement1.setString(1,idTrsField.getText());
                    preparedStatement1.executeUpdate();Adminmastercontroller.loadData();

                    PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE pelanggan SET proses_cucian = ? WHERE id = ?");
                    preparedStatement2.setString(1, "Kosong");
                    preparedStatement2.setString(2, pelangganBox.getValue());
                    preparedStatement2.executeUpdate();

                    PreparedStatement preparedStatement3 = connection.prepareStatement("DELETE FROM detail_pesanan WHERE id_pelanggan = ?");
                    preparedStatement3.setString(1, pelangganBox.getValue());
                    preparedStatement3.executeUpdate();

                    PreparedStatement preparedStatement4 = connection.prepareStatement("DELETE FROM transaksi WHERE id = ?");
                    preparedStatement4.setString(1, idTrsField.getText());
                    preparedStatement4.executeUpdate();

                    PreparedStatement preparedStatement5 = connection.prepareStatement("INSERT INTO transaksi(id) VALUES(?)");
                    preparedStatement5.setString(1, idTrsField.getText());
                    preparedStatement5.executeUpdate();



                    hapus();loadData();buildData();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        batalButton.setOnAction(event -> {
            staticSelectedItem = null;
            hapus();loadData();buildData();
        });
    }

    public void hapus(){
        pelangganBox.setValue(null);masukPlgDate.setValue(null);transaksiTrsDate.setValue(null);
        namaPlgField.setText("");alamatPlgField.setText("");telpPlgField.setText("");
        idTrsField.setText("");totalTrsField.setText("");bayarField.setText("");
        kembalianLabel.setText("");
    }

    public void validation() {
        Validation.numericOnly(bayarField);
        Validation.addTextLimiter(bayarField,8);
    }
}
