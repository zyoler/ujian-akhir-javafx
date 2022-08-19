package org.neophyte.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import org.neophyte.model.Customer;
import org.neophyte.model.History;
import org.neophyte.model.Transaksi;
import org.neophyte.utils.DataBase;

import java.sql.*;

public class DataTransaksi {
    public static TableView<Transaksi> staticDataTransaksi;
    public TableView<Transaksi> dataTransaksi;
    public static TableView<Customer> staticCustomerTable;
    public TableView<Customer> customerTable;
    public TableView<History> dataHistory;
    public static TableView<History> staticDataHistory;

    public TextField showSearchTransaksiTextfield;
    public Button editTransaksiButton;
    public Button deleteTransaksiButton;
    public TextField showSearchCustomerTextfield;
    public Button deleteCustomerButton;
    public TextField showSearchHistory;
    public Button deleteHistoryButton;


    Transaksi selectTransaksi;
    Customer selectCustomer;
    History selecthistory;

    public static void loadDataTransaksi() {
        staticDataTransaksi.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TRANSAKSI");
            while (resultSet.next()) {
                staticDataTransaksi.getItems().add(new Transaksi(resultSet.getInt("id_transaksi"), resultSet.getDate("tangga"), resultSet.getInt("jumlah_tiket"), resultSet.getInt("id"), resultSet.getString("nama_operator"), resultSet.getString("kode_tiket"), resultSet.getInt("harga_tiket"), resultSet.getString("alamat_customer"), resultSet.getString("nama_customer"), resultSet.getString("kode_tayang"), resultSet.getString("tittle"), resultSet.getTime("jam_tayang"), resultSet.getInt("total"), resultSet.getString("keterangan")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadDataHistory() {
        staticDataHistory.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM HISTORY");
            while (resultSet.next()) {
                staticDataHistory.getItems().add(new History(resultSet.getInt("id_transaksi"), resultSet.getDate("tanggal"), resultSet.getInt("jumlah_tiket"), resultSet.getInt("id_operator"), resultSet.getString("nama_operator"), resultSet.getString("kode_tiket"), resultSet.getInt("harga_tiket"), resultSet.getString("alamat_customer"), resultSet.getString("nama_customer"), resultSet.getString("kode_tayang"), resultSet.getString("tittle"), resultSet.getTime("jam_tayang"), resultSet.getInt("total"), resultSet.getString("keterangan")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saerchloadDataTransaksi() {
        staticDataTransaksi.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TRANSAKSI");
            while (resultSet.next()) {
                if ((resultSet.getString("id_transaksi").toLowerCase()).startsWith(showSearchTransaksiTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("tangga").toLowerCase()).contains(' ' + showSearchTransaksiTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("nama_operator").toLowerCase()).contains(' ' + showSearchTransaksiTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("nama_customer").toLowerCase()).contains(' ' + showSearchTransaksiTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("tittle").toLowerCase()).contains(' ' + showSearchTransaksiTextfield.getText().toLowerCase()))
                    staticDataTransaksi.getItems().add(new Transaksi(resultSet.getInt("id_transaksi"), resultSet.getDate("tangga"), resultSet.getInt("jumlah_tiket"), resultSet.getInt("id"), resultSet.getString("nama_operator"), resultSet.getString("kode_tiket"), resultSet.getInt("harga_tiket"), resultSet.getString("alamat_customer"), resultSet.getString("nama_customer"), resultSet.getString("kode_tayang"), resultSet.getString("tittle"), resultSet.getTime("jam_tayang"), resultSet.getInt("total"), resultSet.getString("keterangan")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchHistori() {
        staticDataHistory.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM HISTORY");
            while (resultSet.next()) {
                if ((resultSet.getString("id_transaksi").toLowerCase()).startsWith(showSearchTransaksiTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("tangga").toLowerCase()).contains(' ' + showSearchTransaksiTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("nama_operator").toLowerCase()).contains(' ' + showSearchTransaksiTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("nama_customer").toLowerCase()).contains(' ' + showSearchTransaksiTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("tittle").toLowerCase()).contains(' ' + showSearchTransaksiTextfield.getText().toLowerCase()))
                    staticDataHistory.getItems().add(new History(resultSet.getInt("id_transaksi"), resultSet.getDate("tanggal"), resultSet.getInt("jumlah_tiket"), resultSet.getInt("id"), resultSet.getString("nama_operator"), resultSet.getString("kode_tiket"), resultSet.getInt("harga_tiket"), resultSet.getString("alamat_customer"), resultSet.getString("nama_customer"), resultSet.getString("kode_tayang"), resultSet.getString("tittle"), resultSet.getTime("jam_tayang"), resultSet.getInt("total"), resultSet.getString("keterangan")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saerchCustomer() {
        staticCustomerTable.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CUSTOMER");
            while (resultSet.next()) {
                if ((resultSet.getString("id_customer").toLowerCase()).startsWith(showSearchCustomerTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("nama_customer").toLowerCase()).contains(' ' + showSearchCustomerTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("jenis_kelamin").toLowerCase()).contains(' ' + showSearchCustomerTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("alamat").toLowerCase()).contains(' ' + showSearchCustomerTextfield.getText().toLowerCase()))
                    staticCustomerTable.getItems().add(new Customer(resultSet.getInt("id_customer"), resultSet.getString("nama_customer"), resultSet.getString("alamat"), resultSet.getString("no_kontak"), resultSet.getString("jenis_kelamin")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadCustomer() {
        staticCustomerTable.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CUSTOMER");
            while (resultSet.next()) {
                staticCustomerTable.getItems().add(new Customer(resultSet.getInt("id_customer"), resultSet.getString("nama_customer"), resultSet.getString("alamat"), resultSet.getString("no_kontak"), resultSet.getString("jenis_kelamin")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        staticDataTransaksi = dataTransaksi;
        staticCustomerTable = customerTable;
        staticDataHistory = dataHistory;
        loadDataTransaksi();
        loadCustomer();
        loadDataHistory();

        showSearchCustomerTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                saerchCustomer();
            }
        });
        showSearchTransaksiTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                saerchloadDataTransaksi();
            }
        });
        showSearchHistory.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchHistori();
            }
        });
        deleteTransaksiButton.setOnAction(event -> {
            selectTransaksi = dataTransaksi.getSelectionModel().getSelectedItem();
            if (selectTransaksi != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectTransaksi.getNamaCustomer() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = DataBase.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TRANSAKSI WHERE ID_TRANSAKSI = ?");
                        preparedStatement.setInt(1, selectTransaksi.getIdTransaksi());
                        preparedStatement.executeUpdate();
                        loadDataTransaksi();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Connection conn = DataBase.getConnection();
                try {
                    Date localDate = selectTransaksi.getTanggal();
                    PreparedStatement pr = conn.prepareStatement("INSERT INTO HISTORY (ID_TRANSAKSI,TANGGAL , JUMLAH_TIKET, ID_OPERATOR, NAMA_OPERATOR, KODE_TIKET, HARGA_TIKET, ALAMAT_CUSTOMER, NAMA_CUSTOMER, KODE_TAYANG, TITTLE, JAM_TAYANG, TOTAL, KETERANGAN) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
                    pr.setInt(1, selectTransaksi.getIdTransaksi());
                    pr.setString(2, localDate.toString());
                    pr.setInt(3, selectTransaksi.getJumlahTiket());
                    pr.setInt(4, selectTransaksi.getIdUser());
                    pr.setString(5, selectTransaksi.getNamaUser());
                    pr.setString(6, selectTransaksi.getKdeTiket());
                    pr.setInt(7, selectTransaksi.getHarga());
                    pr.setString(8, selectTransaksi.getAlamatCustomer());
                    pr.setString(9, selectTransaksi.getNamaCustomer());
                    pr.setString(10, selectTransaksi.getKodeTayang());
                    pr.setString(11, selectTransaksi.getTittle());
                    pr.setObject(12, selectTransaksi.getJamTayang());
                    pr.setInt(13, selectTransaksi.getTotal());
                    pr.setString(14, selectTransaksi.getKeterangan());
                    pr.executeUpdate();
                    loadDataHistory();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Klik Data");
                alert.setHeaderText(null);
                alert.setContentText("Pilih Satu Data Untuk Di Hapus!!!");
                alert.show();
            }
        });
        deleteHistoryButton.setOnAction(event -> {
            selecthistory = dataHistory.getSelectionModel().getSelectedItem();
            if (selecthistory != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selecthistory.getNamaCustomer() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = DataBase.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM HISTORY WHERE ID_TRANSAKSI = ?");
                        preparedStatement.setInt(1, selecthistory.getIdTransaksi());
                        preparedStatement.executeUpdate();
                        loadDataHistory();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Klik Data");
                alert.setHeaderText(null);
                alert.setContentText("Pilih Satu Data Untuk Di Hapus!!!");
                alert.show();
            }
        });

        deleteCustomerButton.setOnAction(event -> {
            selectCustomer = customerTable.getSelectionModel().getSelectedItem();

            if (selectCustomer != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectCustomer.getNamaCustomer() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = DataBase.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CUSTOMER WHERE ID_CUSTOMER = ?");
                        preparedStatement.setInt(1, selectCustomer.getIdCustomer());
                        preparedStatement.executeUpdate();
                        loadCustomer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Klik Data");
                alert.setHeaderText(null);
                alert.setContentText("Pilih Satu Data Untuk Di Hapus!!!");
                alert.show();
            }
        });
    }

}
