package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.Penjualan;
import org.neophyte.model.TampViewPj;
import org.neophyte.util.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

public class penjualanController {
    public static TableView<TampViewPj> staticPenjTable;
    public TableView<TampViewPj> penjualanTable;
    public TextField idPenjualanField;
    public DatePicker tglPenjualanField;
    public  ComboBox idObatField;
    public TextField qtyField;

    public Button simpanPenjButton;
    public Button selesaiPenjButton;
    public Button kembaliPenjButton;

    Penjualan penjualan;
    TampViewPj selectedPenjualan;
    Connection connection = Database.getConnection();

    public int totall=0;
    public static int totalll=0;
    public int harga=0;
    public int jml=0;
    public int stokk;
    public int stokUpdate;
    public int stokObat;
    public int tampStok;
    public int cek=0;

    public static int nampungId;


    public void validation() {
        Validasi.numericOnly(qtyField);
        Validasi.addTextLimiter(qtyField,3);
    }
    public static void loadData() {
        staticPenjTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TAMP");
            while (resultSet.next()) {
                staticPenjTable.getItems().add(new TampViewPj(resultSet.getString("idp"),resultSet.getString("tgl"), resultSet.getString("ido"), resultSet.getInt("qty"), resultSet.getInt("total"), resultSet.getInt("totalBayar")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  void loadDataId(){
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PENJUALAN");
            while (resultSet.next()) {
                nampungId = Integer.parseInt(resultSet.getString("idPenjualan").substring(2)) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getIdObat() throws SQLException{
        ArrayList<String> id = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("Select distinct * from Obat");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            id.add(resultSet.getString("idObat"));
        }
        return id;
    }
    public void initialize(){
        staticPenjTable = penjualanTable;
        loadDataId();
        loadId();
        validation();

        LocalDate hariIni = LocalDate.now();
        tglPenjualanField.setValue(hariIni);
        tglPenjualanField.setEditable(false);


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
                    harga = rs.getInt("hargaJual");
                    stokk = rs.getInt("stok");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        kembaliPenjButton.setOnAction(event -> {
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
            System.out.println(cek);
            if(cek==0){
                ((Stage) kembaliPenjButton.getScene().getWindow()).close();
                Main.refresh();
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Yakin ingin keluar? Barang yang dijual akan dibatalkan.");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK) {
                    System.out.println("masuk pintu");
                    try{
                        System.out.println("masuk");
                        PreparedStatement ps = connection.prepareStatement("select * from tamp where IDP = ?");
                        ps.setString(1,idPenjualanField.getText());
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
                            stokUpdate = stokObat + tampStok;
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
                        preparedStatement.setString(1, idPenjualanField.getText());
                        preparedStatement.executeUpdate();

                        PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM PENJUALAN WHERE IDPENJUALAN = ?");
                        preparedStatement1.setString(1, idPenjualanField.getText());
                        preparedStatement1.executeUpdate();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ((Stage) kembaliPenjButton.getScene().getWindow()).close();
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
        if (Objects.equals(idPenjualanField.getText(), "")) {
            alert.setContentText("Id Penjualan belum diisi.");
            alert.show();
        }else if(idObatField.getValue() == null){
            alert.setContentText("id Obat belum diisi.");
            alert.show();
        }else if(Objects.equals(qtyField.getText(), "")){
            alert.setContentText("Jumlah obat belum diisi.");
            alert.show();
        }else{
            jml = Integer.parseInt(String.valueOf(qtyField.getText()));
                if(jml>stokk){
                    alert.setContentText("Stok kurang.");
                    alert.show();
                }else if(jml<=0){
                    alert.setContentText("Jumlah obat invalid.");
                    alert.show();
                }else{
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO penjualan (idpenjualan, tanggalpenjualan, idobat, qty, total, totalbayar) VALUES (?, ?, ?, ?,?,?)");

                        preparedStatement.setString(1, idPenjualanField.getText());
                        LocalDate tglPenj = tglPenjualanField.getValue();
                        preparedStatement.setString(2, tglPenj.toString());
                        preparedStatement.setString(3, (String) idObatField.getValue());
                        preparedStatement.setString(4, qtyField.getText());
                        jml = Integer.parseInt(String.valueOf(qtyField.getText()));
                        stokUpdate = stokk - jml;
                        totall = jml *harga;
                        totalll = 0;
                        PreparedStatement ps = connection.prepareStatement("select * from PENJUALAN where IDPENJUALAN=?");
                        ps.setString(1,idPenjualanField.getText());
                        ResultSet resultSet = ps.executeQuery();
                        while (resultSet.next()){
                            totalll=totalll+(resultSet.getInt("total"));
                        }
                        totalll= totalll + totall;
                        preparedStatement.setString(5,String.valueOf(totall));
                        preparedStatement.setString(6,String.valueOf(totalll));
                        preparedStatement.executeUpdate();

                        PreparedStatement preparedStatement3 = connection.prepareStatement("INSERT INTO tamp (idp, ids, tgl, ido, qty, total, totalbayar) VALUES (?, ?, ?, ?,?,?,?)");
                        preparedStatement3.setString(1, idPenjualanField.getText());
                        preparedStatement3.setString(2,"");
                        preparedStatement3.setString(3, tglPenj.toString());
                        preparedStatement3.setString(4, (String) idObatField.getValue());
                        preparedStatement3.setString(5, qtyField.getText());
                        preparedStatement3.setString(6,String.valueOf(totall));
                        preparedStatement3.setString(7,String.valueOf(totalll));
                        preparedStatement3.executeUpdate();

                        PreparedStatement ps1 = connection.prepareStatement("update PENJUALAN set TOTALBAYAR = ? where IDPENJUALAN = ?");
                        ps1.setInt(1,totalll);
                        ps1.setString(2,idPenjualanField.getText());
                        ps1.executeUpdate();

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
        selectedPenjualan =  penjualanTable.getSelectionModel().getSelectedItem();
        if (selectedPenjualan != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                VBox vBox = fxmlLoader.load(getClass().getResource("/pages/bayar_penjualan.fxml").openStream());
                Scene bayarScene = new Scene(vBox);
                Stage bayarStage = new Stage();
                bayarStage.setScene(bayarScene);
                bayarStage.initOwner(penjualanTable.getScene().getWindow());
                bayarStage.initModality(Modality.WINDOW_MODAL);
                bayarStage.setResizable(false);

                TransaksiPenjualanController transaksiPenjualanController = fxmlLoader.getController();
                transaksiPenjualanController.fillForm(selectedPenjualan);

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
            idPenjualanField.setText("J00" + tamp);
        else if(tamp < 100)
            idPenjualanField.setText("J0" + tamp);
        idPenjualanField.setEditable(false);
    }
    public void loadId(){
        int tamp = nampungId;
        cekId(tamp);
    }
}
