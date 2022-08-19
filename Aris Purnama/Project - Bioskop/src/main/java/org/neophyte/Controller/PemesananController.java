package org.neophyte.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.Transaksi;
import org.neophyte.utils.DataBase;
import org.neophyte.validasi.ValidasiUmum;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;


public class PemesananController {
    public GridPane tittleTextFieldTayang;
    public static int tampIdTran;

    public JFXComboBox<String> KDTicketCb;
    public JFXComboBox<String> tittleTayangTextField;
    public TextField textFieldNamaOperator;
    public JFXTextField IDOperator;
    public TextField hargaTiket;
    public JFXTextField KDtayangCb;
    public JFXTimePicker jamTayangTimePicker;
    public JFXTextField textFieldJumlahBeliTiket;
    public TextField textFieldTotalBayar;
    public JFXTextField namaTextField;
    public JFXTextArea alamatTextField;
    public JFXTextField JKTextField;
    public JFXTextField noKontakTextField;


    public Button BtnBayar;
    public DatePicker tanggalDate;
    public JFXTextField jumlahUang;
    public TextField keteranganTextField;
    public Button BtnCancel;

    public static LocalDate date;
    public static String Judul;
    public static LocalTime time;
    public static String jumlah;
    public static int harga;

    Transaksi transaksi;
    Connection conn = DataBase.getConnection();

    //////////////////////////////////////// INITIALIZE ////////////////////////////////////////////////////////////////////
    public void initialize() throws SQLException {
        //variabel local
        String harga = textFieldJumlahBeliTiket.textProperty().getValue();
        String jml = hargaTiket.textProperty().getValue();
        // Value di comboBox Tiket
        for (int x = 0; x < getString().size(); x++) {
            KDTicketCb.getItems().add(getString().get(x));
        }
        // Value di comboBox Jadwal Tayang
        for (int x = 0; x < getTayang().size(); x++) {
            tittleTayangTextField.getItems().add(getTayang().get(x));
        }
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM USER WHERE ID = ?");
            preparedStatement.setInt(1, Main.logginInadmin.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                IDOperator.setText(String.valueOf(rs.getInt("id")));
                textFieldNamaOperator.setText(rs.getString("nama_user"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //on action
        KDTicketCb.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("SELECT DISTINCT * FROM TIKET WHERE KODE_TIKET = ?");
                    preparedStatement.setString(1, KDTicketCb.getValue());
                    ResultSet rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        hargaTiket.textProperty().setValue(String.valueOf(rs.getInt("harga")));
                        textFieldTotalBayar.setText(String.valueOf(Integer.parseInt(hargaTiket.getText()) * Integer.parseInt(textFieldJumlahBeliTiket.getText())));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        tittleTayangTextField.setOnAction(event -> {
            try {
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT DISTINCT * FROM JADWAL_TAYANG WHERE TITTLE = ?");
                preparedStatement.setString(1, tittleTayangTextField.getValue());
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    KDtayangCb.setText(rs.getString("kode_tayang"));
                    jamTayangTimePicker.setValue(rs.getTime("jam_tayang").toLocalTime());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        //tombol Button
        BtnBayar.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            LocalDate tanggal = LocalDate.now();
            if (Objects.equals(namaTextField.getText(), "") && Objects.equals(alamatTextField, "") && Objects.equals(JKTextField.getText(), "") && Objects.equals(noKontakTextField.getText(), "") && Objects.equals(tanggalDate.getValue(), "") && Objects.equals(textFieldJumlahBeliTiket.getText(), "") && Objects.equals(hargaTiket.getText(), "") && Objects.equals(KDtayangCb.getText(), "") && Objects.equals(tittleTayangTextField.getValue(), "") && Objects.equals(jumlahUang.getText(), "")) {
                alert.setContentText("Tolong Isi Formulir ini");
                alert.show();
            } else {
                if (Objects.equals(namaTextField.getText(), "")) {
                    alert.setContentText("Nama pengguna belum Diisi. Tidak Boleh Kosong!!!");
                    alert.show();
                } else if (namaTextField.getText().matches(".*\\d.*")) {
                    alert.setContentText("Nama Tidak Boleh Berisi Angka!!!");
                    alert.show();
                } else if (ValidasiUmum.nama2(namaTextField.getText()) == false) {
                    alert.setContentText("Nama tidak Kososng!!.");
                    alert.show();
                } else if (Objects.equals(alamatTextField, "")) {
                    alert.setContentText("Alamat Tidak Boleh Kosong");
                    alert.show();
                } else if (ValidasiUmum.alamat(alamatTextField.getText()) == false) {
                    alert.setContentText("Alamat Tidak Bole Berisi Karakter!!");
                    alert.show();
                } else if (Objects.equals(JKTextField.getText(), "")) {
                    alert.setContentText("Jenis Kelamin Tidak Boleh Kosong!!");
                    alert.show();
                } else if (ValidasiUmum.jenisKelamin(JKTextField.getText()) == false) {
                    alert.setContentText("Jenis Kelamin Hanya Menangkap Simbol (-)!!");
                    alert.show();
                } else if (Objects.equals(noKontakTextField.getText(), "")) {
                    alert.setContentText("Nomor Tidak Boleh Kosong!!");
                    alert.show();
                } else if (ValidasiUmum.number(noKontakTextField.getText()) == false) {
                    alert.setContentText("Number 12!!");
                    alert.show();
                }else if(noKontakTextField.getText().charAt(0) != '0' || noKontakTextField.getText().charAt(1) != '8'){
                    alert.setContentText("Number di Awali 08....");
                    alert.show();
                } else if (Objects.equals(tanggalDate.getValue(), "")) {
                    alert.setContentText("Tanggal Tidak Boleh Kosong!!!");
                    alert.show();
                } else if (Objects.equals(textFieldJumlahBeliTiket.getText(), "")) {
                    alert.setContentText("Tidak Boleh Kosong");
                    alert.show();
                } else if (ValidasiUmum.jumlahtiket(textFieldJumlahBeliTiket.getText()) == false) {
                    alert.setContentText("Tidak Boleh 0.");
                    alert.show();
                } else if (Objects.equals(KDTicketCb.getValue(), "")) {
                    alert.setContentText("Kodde Tiket Tidak Boleh Kosong!!!");
                    alert.show();
                } else if (Objects.equals(hargaTiket.getText(), "")) {
                    alert.setContentText(" Harga Tidak Boleh Kosong!!!");
                    alert.show();
                } else if (Objects.equals(KDtayangCb.getText(), "")) {
                    alert.setContentText("Kode Tayang Tidak Boleh Kosong!!!");
                    alert.show();
                } else if (Objects.equals(tittleTayangTextField.getValue(), "")) {
                    alert.setContentText("Tittle Tidak Boleh Kosong!!!");
                    alert.show();
                } else if (Objects.equals(jumlahUang.getText(), "")) {
                    alert.setContentText("Uang Tidak Boleh Kosong!!!");
                    alert.show();
                } else if (jumlahUang.getText().matches(".*\\d.*")==false) {
                    alert.setContentText("Uang Hanya Berisi Angka!!!");
                    alert.show();
                } else {
                    String uang;
                    String total;
                    String keterangan = null;
                    uang = jumlahUang.getText();
                    total = textFieldTotalBayar.getText();
                    if (Integer.parseInt(uang) >= Integer.parseInt(total)) {
                        keteranganTextField.setText("Lunas");
                        alert.setContentText("Berhasil>>" );
                        alert.show();
                        try {
                            LocalDate localDate = tanggalDate.getValue();
                            date = localDate;
                            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO TRANSAKSI (TANGGA, JUMLAH_TIKET, ID, NAMA_OPERATOR, KODE_TIKET, HARGA_TIKET, ALAMAT_CUSTOMER, NAMA_CUSTOMER, KODE_TAYANG, TITTLE, JAM_TAYANG, TOTAL, KETERANGAN) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,? )");
                            preparedStatement.setString(1, localDate.toString());
                            preparedStatement.setString(2, textFieldJumlahBeliTiket.getText());
                            jumlah = textFieldJumlahBeliTiket.getText();
                            preparedStatement.setInt(3, Integer.parseInt(IDOperator.getText()));
                            preparedStatement.setString(4, textFieldNamaOperator.getText());
                            preparedStatement.setString(5, KDTicketCb.getValue());
                            preparedStatement.setString(6, hargaTiket.getText());
                            preparedStatement.setString(7, alamatTextField.getText());
                            preparedStatement.setString(8, namaTextField.getText());
                            preparedStatement.setString(9, KDtayangCb.getText());
                            preparedStatement.setString(10, tittleTayangTextField.getValue());
                            Judul=tittleTayangTextField.getValue();
                            preparedStatement.setObject(11, jamTayangTimePicker.getValue());
                            time = jamTayangTimePicker.getValue();
                            preparedStatement.setString(12, textFieldTotalBayar.getText());
                            preparedStatement.setString(13, keteranganTextField.getText());
                            preparedStatement.executeUpdate();
                            PreparedStatement pr = conn.prepareStatement("INSERT INTO CUSTOMER (NAMA_CUSTOMER, ALAMAT, NO_KONTAK, JENIS_KELAMIN) VALUES ( ?,?,?,? )");
                            pr.setString(1, namaTextField.getText());
                            pr.setString(2, alamatTextField.getText());
                            pr.setString(3,noKontakTextField.getText());
                            pr.setString(4, JKTextField.getText());
                            pr.executeUpdate();
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/view/struk.fxml"));
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.initModality(Modality.WINDOW_MODAL);
                                stage.setResizable(false);
                                stage.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        alert.setContentText("Uang Kurang!!!");
                        alert.show();
                    }
                }
            }
        });
    }

    ///////////////////////////////// PUBLIC FUNCTION  /////////////////////////////////////////////////////////////////////
    // TAMPIL COMBO BOX
    public ArrayList<String> getString() throws SQLException {
        ArrayList<String> lp = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT DISTINCT * FROM TIKET");
        while (rs.next()) {
            lp.add(rs.getString("kode_tiket"));
        }
        return lp;
    }

    public ArrayList<String> getTayang() throws SQLException {
        ArrayList<String> lp = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT DISTINCT * FROM JADWAL_TAYANG");
        while (rs.next()) {
            lp.add(rs.getString("tittle"));
        }
        return lp;
    }

    public void close() {

        ((Stage) BtnCancel.getScene().getWindow()).close();
    }


}
