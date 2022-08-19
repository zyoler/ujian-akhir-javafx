package org.neo.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neo.main.Main;
import org.neo.util.Database;

import java.awt.event.KeyEvent;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ReservasiController {
    public JFXTextField namaTx;
    public JFXTextField hpTx;
    public JFXComboBox<String> cllapangan;
    public JFXComboBox<String> cLapangan;
    public JFXDatePicker tglDt;
    public JFXComboBox<String> cPukul;
    public JFXComboBox<String> cLama;
    public Button pesanButton;
    public Button bersihButton;
    public Button tes;
    public ImageView img;

    LocalDate localDate = LocalDate.now();
    int maxLength = 13;

    public void initialize() throws SQLException {

        img.setImage(new Image("/image/booking.png"));

        hpTx.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                hpTx.setText(newValue.replaceAll("[^\\d]", ""));
            }else if (hpTx.getText().length() > maxLength) {
                String s = hpTx.getText().substring(0, maxLength);
                hpTx.setText(s);
            }
        });

        namaTx.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                namaTx.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        pesanButton.setOnAction(event -> {
            if(hpTx.getText().trim().isEmpty() || namaTx.getText().trim().isEmpty() || cllapangan.getSelectionModel().isEmpty() || cLapangan.getSelectionModel().isEmpty() || tglDt.getValue() == null || cLama.getSelectionModel().isEmpty() || cPukul.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning!!");
                alert.setHeaderText(null);
                alert.setContentText("Harap isi data terlebih dahulu!");
                alert.show();
                clear();
                namaTx.isFocused();
            }else if(tglDt.getValue().isBefore(localDate)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning!!");
                alert.setHeaderText(null);
                alert.setContentText("Isi tanggal yang sesuai!!");
                alert.show();
                clear();
                namaTx.isFocused();
            }else if(hpTx.getText().charAt(0) != '0' || hpTx.getText().charAt(1) != '8'){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning!!");
                alert.setHeaderText(null);
                alert.setContentText("Isi no handphone yang sesuai!!");
                alert.show();
                hpTx.isFocused();
            }else {
                int x=0;
                try{
                    Connection connection = Database.connect();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM LAPANG where NAMALPNG='"+cllapangan.getValue()+"' AND JENISLPNG='"+cLapangan.getValue()+"'");
                    String id = null;
                    while(resultSet.next()){
                            id = resultSet.getString("id_lapang");
                    }
                    resultSet = statement.executeQuery("SELECT * FROM BOOKING");
                    while(resultSet.next()){
                        System.out.println(resultSet.getString("tgl_main")+" dan "+tglDt.getValue());
                        System.out.println(resultSet.getString("pukul")+" dan "+cPukul.getValue());
                        System.out.println(resultSet.getString("id_lapang")+" dan "+id);
                        if(resultSet.getString("tgl_main").equals(tglDt.getValue().toString()) && resultSet.getString("pukul").equals(cPukul.getValue().toString()) && resultSet.getString("id_lapang").equals(id)){
                            System.out.println("Cek");
                            x=1;
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if(x==1){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning!!");
                    alert.setHeaderText(null);
                    alert.setContentText("Jadwal sudah ada!!");
                    alert.show();
                    clear();
                    namaTx.isFocused();
                }else {
                    String id = null;
                    int harga = 0;
                    try {
                        Connection conn = Database.connect();
                        PreparedStatement ps = conn.prepareStatement("select ID_LAPANG from LAPANG where NAMALPNG=? and JENISLPNG=?");
                        ps.setString(2, cLapangan.getValue());
                        ps.setString(1, cllapangan.getValue());
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            id = rs.getString("ID_LAPANG");
                        }
                        ps = conn.prepareStatement("select HARGA from LAPANG where NAMALPNG=? and JENISLPNG=?");
                        ps.setString(2, cLapangan.getValue());
                        ps.setString(1, cllapangan.getValue());
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            harga = rs.getInt("HARGA");
                        }
                        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO BOOKING (nama_pemesan,no_hp,tgl_pesan,tgl_main,lama,pukul,TOTAL,ID_LAPANG) VALUES (?,?,?,?,?,?,?,?)");
                        preparedStatement.setString(1, namaTx.getText());
                        preparedStatement.setString(2, hpTx.getText());
                        preparedStatement.setString(3, localDate.toString());
                        localDate = tglDt.getValue();
                        preparedStatement.setString(4, localDate.toString());
                        preparedStatement.setInt(5, Integer.parseInt(cLama.getValue()));
                        preparedStatement.setInt(7, harga * Integer.parseInt(cLama.getValue()));
                        preparedStatement.setString(6, cPukul.getValue());
                        preparedStatement.setString(8, id);
                        preparedStatement.executeUpdate();
                        clear();
                        Database.disconnect();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Informasi!");
                        alert.setHeaderText(null);
                        alert.setContentText("Berhasil Booking!");
                        ButtonType result = alert.showAndWait().get();
                        if (result.getText().equals("OK")) {
                            try {
                                VBox vBox = FXMLLoader.load(getClass().getResource("/viewpesanan.fxml"));
                                Scene bayarScene = new Scene(vBox);
                                Stage bayarStage = new Stage();
                                bayarStage.setScene(bayarScene);
                                bayarStage.setResizable(false);
                                bayarStage.setTitle("Pembayaran");
                                bayarStage.initOwner(Main.getStage());
                                bayarStage.initModality(Modality.WINDOW_MODAL);
                                bayarStage.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        for(int x=0; x<getLapang("namalpng").size(); x++){
            cllapangan.getItems().add(getLapang("namalpng").get(x));
        }

        cllapangan.setOnAction(event -> {
            cLapangan.getItems().clear();
            ArrayList<String> ne = null;
            try {
                ne = getLapang("jenislpng");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for(int x=0; x<ne.size(); x++){
                try {
                    cLapangan.getItems().add(getLapang("jenislpng").get(x));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        cPukul.getItems().addAll(
                "10","11","12","13","14","15","16","17","18","19","20","21"
        );

        cLama.getItems().addAll(
                "1","2","4"
        );

        bersihButton.setOnAction(event -> {
            clear();
        });
    }

    private void vbox(VBox vBox) {
        Scene sceneLapang = new Scene(vBox);
        Stage stageLapang = new Stage();
        stageLapang.setScene(sceneLapang);
        stageLapang.setResizable(false);
        stageLapang.setTitle("List Lapangan");
        stageLapang.initOwner(Main.getStage());
        stageLapang.initModality(Modality.WINDOW_MODAL);
        stageLapang.show();
    }

    public ArrayList<String> getLapang(String clm) throws SQLException {
        Connection conn = Database.connect();
        ArrayList<String> lp = new ArrayList<>();
        if(Objects.equals(clm, "jenislpng")) {
            String tes = cllapangan.getValue();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT distinct " + clm + " FROM LAPANG where namalpng='"+tes+"' order by " + clm);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                lp.add(rs.getString(clm));
            }
        }else{
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT distinct "+clm+" FROM LAPANG order by "+clm);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                lp.add(rs.getString(clm));
            }
        }
        return lp;
    }

    public void clear(){
        namaTx.setText("");
        hpTx.setText("");
        tglDt.setValue(null);
        cLapangan.setValue("");
        cllapangan.setValue("");
        cLama.setValue("");
        cPukul.setValue("");
    }
}
