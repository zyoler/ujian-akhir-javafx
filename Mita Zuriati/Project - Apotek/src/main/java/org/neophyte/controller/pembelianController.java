package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.Pembelian;
import org.neophyte.model.TampViewPb;
import org.neophyte.util.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

public class pembelianController {
    public static TableView<TampViewPb> staticPembTable;
    public TableView<TampViewPb> pembelianTable;
    public TextField idPembelianField;
    public ComboBox idSupplierField;
    public DatePicker tglPembelianField;
    public ComboBox idObatField;
    public TextField qtyField;

    public Button simpanPembButton;
    public Button selesaiPembButton;
    public Button kembaliPembButton;
    Pembelian pembelian;
    TampViewPb selectedPembelian;

    Connection connection = Database.getConnection();

    public int totall=0;
    public static int totalll=0;
    public int harga=0;
    public int jml=0;
    public int stokk;
    public int stokUpdate;
    public int stokObat;
    public int tampStok;
    public static int nampungId;
    public int cek=0;

    public void validation() {
        Validasi.numericOnly(qtyField);
        Validasi.addTextLimiter(qtyField,3);
    }
    public static void loadData() {
        staticPembTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TAMP ");
            while (resultSet.next()) {
                staticPembTable.getItems().add(new TampViewPb(resultSet.getString("idp"),resultSet.getString("ids"),resultSet.getString("tgl"), resultSet.getString("ido"), resultSet.getInt("qty"), resultSet.getInt("total"), resultSet.getInt("totalBayar")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  void loadDataId(){
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PEMBELIAN");
            while (resultSet.next()) {
                nampungId = Integer.parseInt(resultSet.getString("idPembelian").substring(2)) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getIdObat() throws SQLException {
        ArrayList<String> id = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("Select distinct * from Obat");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            id.add(resultSet.getString("idObat"));
        }
        return id;
    }
    public ArrayList<String> getIdSupplier() throws SQLException {
        ArrayList<String> id = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("Select distinct * from Supplier");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            id.add(resultSet.getString("idSupplier"));
        }
        return id;
    }
    public void initialize(){
        staticPembTable = pembelianTable;
        loadDataId();
        loadId();
        validation();

        LocalDate hariIni = LocalDate.now();
        tglPembelianField.setValue(hariIni);
        tglPembelianField.setEditable(false);

        ArrayList<String> isii = null;
        try{
            isii = getIdSupplier();
        }catch(Exception e){
            e.printStackTrace();
        }
        for(int a = 0;a<isii.size();a++){
            idSupplierField.getItems().add(isii.get(a));
        }

        ArrayList<String> isi = null;
        try{
            isi = getIdObat();
        }catch(Exception e){
            e.printStackTrace();
        }
        for(int a = 0;a<isi.size();a++){
            idObatField.getItems().add(isi.get(a));
        }
        idObatField.setOnAction(event ->{
            try{
                PreparedStatement preparedStatement = connection.prepareStatement("select distinct * from OBAT where idObat = ?");
                preparedStatement.setString(1,(String) idObatField.getValue());
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    harga = rs.getInt("hargaBeli");
                    stokk = rs.getInt("stok");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        kembaliPembButton.setOnAction(event -> {
            try {
                Connection connection = Database.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM TAMP");
                while (resultSet.next()) {
                    cek=cek+1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(cek==0){
                ((Stage) kembaliPembButton.getScene().getWindow()).close();
                Main.refresh();
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Yakin ingin keluar? Barang yang dibeli akan dibatalkan.");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK) {
                    System.out.println("masuk pintu");
                    try{
                        System.out.println("masuk");
                        PreparedStatement ps = connection.prepareStatement("select * from tamp where IDP = ?");
                        ps.setString(1,idPembelianField.getText());
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()){
                            String tampIdo = (rs.getString("ido"));
                            tampStok=(rs.getInt("qty"));
                            System.out.println(tampIdo +" + "+ tampStok);
                            try{
                                PreparedStatement ps1 = connection.prepareStatement("select * from obat where IDOBAT=?");
                                ps1.setString(1,tampIdo);
                                ResultSet rs1 = ps1.executeQuery();
                                while (rs1.next()){
                                    stokObat = (rs1.getInt("stok"));
                                    System.out.println(stokObat);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            stokUpdate = stokObat - tampStok;
                            PreparedStatement preparedStatement2;
                            System.out.println(stokUpdate);
                            preparedStatement2 = connection.prepareStatement("update obat set stok = ? where IDOBAT=?");
                            preparedStatement2.setInt(1,stokUpdate);
                            preparedStatement2.setString(2, tampIdo);
                            preparedStatement2.executeUpdate();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TAMP WHERE IDP = ?");
                        preparedStatement.setString(1, idPembelianField.getText());
                        preparedStatement.executeUpdate();

                        PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM PEMBELIAN WHERE IDPEMBELIAN = ?");
                        preparedStatement1.setString(1, idPembelianField.getText());
                        preparedStatement1.executeUpdate();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ((Stage) kembaliPembButton.getScene().getWindow()).close();
                    Main.refresh();
                }else{
                    System.out.println("gamasuk");
                }
            }
        });
    }
    public void clear(){
        qtyField.setText("");

    }
    public void save() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (Objects.equals(idPembelianField.getText(), "")) {
            alert.setContentText("Id Pembelian belum diisi.");
            alert.show();
        }else if(idSupplierField.getValue() == null){
            alert.setContentText("id Supplier belum diisi.");
            alert.show();
        }else if(idObatField.getValue() == null){
            alert.setContentText("id Obat belum diisi.");
            alert.show();
        } else if(Objects.equals(qtyField.getText(), "")){
            alert.setContentText("Jumlah obat belum diisi.");
            alert.show();
        }else {

                jml = Integer.parseInt(String.valueOf(qtyField.getText()));
                if(jml<=0){
                    alert.setContentText("Jumlah obat invalid.");
                    alert.show();
                }else {
                    try {
                        PreparedStatement preparedStatement;
                        preparedStatement = connection.prepareStatement("INSERT INTO pembelian (idpembelian, idSupplier, tanggalPembelian, idobat, qty, total, totalbayar) VALUES (?, ?, ?, ?,?,?,?)");

                        preparedStatement.setString(1, idPembelianField.getText());
                        preparedStatement.setString(2,(String) idSupplierField.getValue());
                        LocalDate tglPemb = tglPembelianField.getValue();
                        preparedStatement.setString(3, tglPemb.toString());
                        preparedStatement.setString(4, (String) idObatField.getValue());
                        preparedStatement.setString(5, qtyField.getText());
                        jml = Integer.parseInt(String.valueOf(qtyField.getText()));
                        stokUpdate = stokk + jml;
                        totall = jml *harga;
                        totalll=0;
                        PreparedStatement ps = connection.prepareStatement("select * from PEMBELIAN where IDPEMBELIAN=?");
                        ps.setString(1,idPembelianField.getText());
                        ResultSet resultSet = ps.executeQuery();
                        while (resultSet.next()){
                            totalll=totalll+(resultSet.getInt("total"));
                        }
                        totalll= totalll + totall;
                        preparedStatement.setString(6,String.valueOf(totall));
                        preparedStatement.setString(7,String.valueOf(totalll));
                        preparedStatement.executeUpdate();

                        PreparedStatement ps1 = connection.prepareStatement("update PEMBELIAN set TOTALBAYAR = ? where IDPEMBELIAN = ?");
                        ps1.setInt(1,totalll);
                        ps1.setString(2,idPembelianField.getText());
                        ps1.executeUpdate();

                        PreparedStatement preparedStatement3 = connection.prepareStatement("INSERT INTO tamp (idp, ids,tgl, ido, qty, total, totalbayar) VALUES (?, ?, ?, ?,?,?,?)");
                        preparedStatement3.setString(1, idPembelianField.getText());
                        preparedStatement3.setString(2,(String) idSupplierField.getValue());
                        preparedStatement3.setString(3, tglPemb.toString());
                        preparedStatement3.setString(4, (String) idObatField.getValue());
                        preparedStatement3.setString(5, qtyField.getText());
                        preparedStatement3.setString(6,String.valueOf(totall));
                        preparedStatement3.setString(7,String.valueOf(totalll));
                        preparedStatement3.executeUpdate();

                        PreparedStatement preparedStatement2;
                        preparedStatement2 = connection.prepareStatement("update obat set stok = ? where IDOBAT=?");
                        preparedStatement2.setInt(1,stokUpdate);
                        preparedStatement2.setString(2, (String) idObatField.getValue());
                        preparedStatement2.executeUpdate();

                        Alert alertt = new Alert(Alert.AlertType.INFORMATION);
                        alertt.setHeaderText(null);
                        alertt.setContentText("Data berhasil disimpan");
                        alertt.show();
                        loadData();
                        clear();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        }
    }
    public void pembayaran(){
        selectedPembelian =  pembelianTable.getSelectionModel().getSelectedItem();
        if (selectedPembelian != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                VBox vBox = fxmlLoader.load(getClass().getResource("/pages/bayar_pembelian.fxml").openStream());
                Scene bayarScene = new Scene(vBox);
                Stage bayarStage = new Stage();
                bayarStage.setScene(bayarScene);
                bayarStage.initOwner(pembelianTable.getScene().getWindow());
                bayarStage.initModality(Modality.WINDOW_MODAL);
                bayarStage.setResizable(false);

                TransaksiPembelianController transaksiPembelianController = fxmlLoader.getController();
                transaksiPembelianController.fillForm(selectedPembelian);

                bayarStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Alert alertt = new Alert(Alert.AlertType.INFORMATION);
            alertt.setHeaderText(null);
            alertt.setContentText("Pilih data yang ingin dibayar terlebih dulu");
            alertt.show();
        }
    }
    public void cekId(int tamp){
        if(tamp < 10)
            idPembelianField.setText("P00" + tamp);
        else if(tamp < 100)
            idPembelianField.setText("P0" + tamp);
        idPembelianField.setEditable(false);
    }
    public void loadId(){
        int tamp = nampungId;
        cekId(tamp);
    }
}