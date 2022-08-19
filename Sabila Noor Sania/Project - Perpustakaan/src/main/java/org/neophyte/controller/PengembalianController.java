package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.Peminjaman;
import org.neophyte.model.Pengembalian;
import org.neophyte.util.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class PengembalianController {
    public static  TableView<Peminjaman> staticPeminjamanTable;
    public static  TableView<Pengembalian> staticPengembalianTable;

    public TableView<Peminjaman> peminjamanTable;
    public Button kembalikan;
    public DatePicker tglDikembalikan;

    public TableView<Pengembalian> PengembalianTable;
    public Button konfirmasi;
    public TextField totalDendaField;
    public Button backButton;
    public Button historyButton;

    Peminjaman selectedPeminjaman;
    Stage stage = Main.getStage();
    ////
    int denda = 1000;
    int totalDenda = 0;
    public String tampKembali;
    public int tampBalik;
    int a;

    public static void loadData() {
        staticPeminjamanTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PEMINJAMAN");
            while (resultSet.next()) {
                staticPeminjamanTable.getItems().add(new Peminjaman(resultSet.getInt("nomor"), resultSet.getInt("id"), resultSet.getString("judulbuku"), resultSet.getInt("idanggota"), resultSet.getString("nama"), resultSet.getString("tgl_pinjam"), resultSet.getString("tgl_kembali")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loadDataPengembalian() {
        staticPengembalianTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PENGEMBALIAN");
            while (resultSet.next()) {
                staticPengembalianTable.getItems().add(new Pengembalian(resultSet.getInt("no"),resultSet.getInt("nomor_peminjaman"), resultSet.getString("judul_buku"),resultSet.getString("nama"),resultSet.getString("tgl_pinjam"),resultSet.getString("tgl_kembali"),resultSet.getString("tgl_dikembalikan"),resultSet.getInt("denda_perhari"),resultSet.getInt("total_denda")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  void  Kembalikan(){
      //  kembalikan.setOnAction(event -> {
            selectedPeminjaman = peminjamanTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            if (selectedPeminjaman == null){
                alert.setContentText("Pilih Data untuk Menambahkan!");
                alert.show();
            }else if(Objects.equals(tglDikembalikan.getValue()," ")){
                alert.setContentText("Masutkan Tanggal Dikembalikan!");
                alert.show();
            }else{
                tampKembali = selectedPeminjaman.getTgl_kembali();
                LocalDate tglPinjam = LocalDate.parse(tampKembali);
                a = Integer.parseInt(String.valueOf(tglPinjam.getDayOfMonth()));
                LocalDate tglkembali = tglDikembalikan.getValue();
                tampBalik = Integer.parseInt(String.valueOf(tglkembali.getDayOfMonth()));

                if(Objects.equals(selectedPeminjaman.getTgl_kembali(),tglDikembalikan.getValue())){
                    totalDenda = 0;
                }else {
                    totalDenda = (tampBalik - a)*1000;
                }
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement  preparedStatement = connection.prepareStatement("INSERT into PENGEMBALIAN (NOMOR_PEMINJAMAN, JUDUL_BUKU, NAMA, TGL_PINJAM, TGL_KEMBALI, TGL_DIKEMBALIKAN, DENDA_PERHARI, TOTAL_DENDA) values ( ?,?,?,?,?,?,?,? )");
                    preparedStatement.setInt(1,selectedPeminjaman.getNomor());
                    preparedStatement.setString(2,selectedPeminjaman.getJudulbuku());
                    preparedStatement.setString(3,selectedPeminjaman.getNama());
                    preparedStatement.setString(4,selectedPeminjaman.getTgl_pinjam());
                    preparedStatement.setString(5, selectedPeminjaman.getTgl_kembali());
                    preparedStatement.setString(6, tglkembali.toString());
                    preparedStatement.setInt(7,denda);
                    preparedStatement.setInt(8,totalDenda);
                    preparedStatement.executeUpdate();
                    loadDataPengembalian();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PEMINJAMAN WHERE NOMOR = ?");
                    preparedStatement.setInt(1, selectedPeminjaman.getNomor());
                    preparedStatement.executeUpdate();
                    loadData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
      //  });
    }
    public void initialize() {
        staticPeminjamanTable = peminjamanTable;
        loadData();

        staticPengembalianTable = PengembalianTable;
        loadDataPengembalian();

        konfirmasi.setOnAction(event -> {
            totalDendaField.setText(String.valueOf(totalDenda));
        });

        backButton.setOnAction(event -> {
            try {
                HBox hBox = FXMLLoader.load(getClass().getResource("/HalamanUtama.fxml"));
                Scene berandaa = new Scene(hBox);
                stage.setScene(berandaa);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        historyButton.setOnAction(event -> {
            try {
                VBox vBox = FXMLLoader.load(getClass().getResource("/history.fxml"));
                Scene berandaa = new Scene(vBox);
                stage.setScene(berandaa);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}

