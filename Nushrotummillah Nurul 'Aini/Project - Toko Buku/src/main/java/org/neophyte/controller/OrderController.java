package org.neophyte.controller;

import javafx.scene.control.*;
import org.neophyte.model.Book;
import org.neophyte.model.Order;
import org.neophyte.util.Database;

import java.sql.*;
import java.util.Objects;

public class OrderController {

    public TableView<Book> orderBookTable;
    public static TableView<Book> staticOrderBookTable;
    public TableView<Order> orderTable;
    public static TableView<Order> staticOrderTable;
    public TextField totalBayar;

    Book selectedOrderBook;
    public TextField jml;
    public TextField transaksiId;
    Order selectedOrder;

    int totalByr=0;
    int total;
    int sisa;
    int tamp;
    String tampJudul;

    public static void loadDataOrderBuku() {
        staticOrderBookTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book");
            while (resultSet.next()) {
                staticOrderBookTable.getItems().add(new Book(resultSet.getInt("id"), resultSet.getString("judul"), resultSet.getString("pengarang"), resultSet.getInt("tahun"), resultSet.getString("kategori"), resultSet.getInt("harga"), resultSet.getInt("jumlah")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadDataOrder() {
        staticOrderTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ordertable ");
            while (resultSet.next()) {
                staticOrderTable.getItems().add(new Order(resultSet.getInt("id"), resultSet.getString("judul"), resultSet.getInt("harga"), resultSet.getInt("jumlah"), resultSet.getInt("total")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        staticOrderBookTable = orderBookTable;
        loadDataOrderBuku();
        staticOrderTable = orderTable;
        loadDataOrder();
    }

    public void backButton() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Kembali");
            alert.setHeaderText(null);
            alert.setContentText("Yakin untuk kembali? Item yang dipilih akan otomatis disimpan di history transaksi.");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                Connection connection = Database.getConnection();
                try {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM ordertable");
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                MainController.refresh();
            }
    }

    public void tambahkanAksi() {
        selectedOrderBook = orderBookTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (selectedOrderBook == null) {
            alert.setContentText("Tidak ada data yang dipilih. Pilih data untuk ditambahkan..");
            alert.show();
        } else if (Objects.equals(jml.getText(), "")) {
            alert.setContentText("Masukkan jumlah item.");
            alert.show();
        } else if (Objects.equals(transaksiId.getText(), "")) {
            alert.setContentText("ID Transaksi belum diisi.");
            alert.show();
        } else {

            tamp = Integer.parseInt(jml.getText());
            if(tamp > selectedOrderBook.getJumlah()){
                alert.setContentText("Jumlah melebihi stok. Silakan kurangi jumlah item untuk melanjutkan");
                alert.show();
            }else{
                Connection connection = Database.getConnection();
                total = selectedOrderBook.getHarga()*tamp;
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ordertable (id, judul, harga, jumlah, total) VALUES (?,?,?,?,?)");
                    preparedStatement.setString(1,transaksiId.getText());
                    preparedStatement.setString(2, selectedOrderBook.getJudul());
                    preparedStatement.setInt(3, selectedOrderBook.getHarga());
                    preparedStatement.setInt(4, tamp);
                    preparedStatement.setInt(5,total);
                    preparedStatement.executeUpdate();
                    loadDataOrder();

                    PreparedStatement psmt = connection.prepareStatement("INSERT INTO history (id, judul, harga, jumlah, total) VALUES (?,?,?,?,?)");
                    psmt.setString(1,transaksiId.getText());
                    psmt.setString(2, selectedOrderBook.getJudul());
                    psmt.setInt(3, selectedOrderBook.getHarga());
                    psmt.setInt(4, tamp);
                    psmt.setInt(5,total);
                    psmt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                sisa = selectedOrderBook.getJumlah() - tamp;
                totalByr += total;
                total = 0;
                totalBayar.setText(String.valueOf(totalByr));
                try {
                    PreparedStatement ps = connection.prepareStatement("update book set jumlah=? where id = ?");
                    ps.setInt(1,sisa);
                    ps.setInt(2,selectedOrderBook.getId());
                    ps.executeUpdate();
                    loadDataOrderBuku();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteAction() {
        selectedOrderBook = orderBookTable.getSelectionModel().getSelectedItem();
        if(selectedOrderBook == null){
            System.out.println("ggl pilih");
        }else{


        selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if(selectedOrder != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus Buku");
            alert.setHeaderText(null);
            alert.setContentText("Anda yakin ingin menghapus buku " + selectedOrder.getJudul() + "?");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                Connection connection = Database.getConnection();
                total = selectedOrder.getHarga()*tamp;
                totalByr -= selectedOrder.getTotal();
                totalBayar.setText(String.valueOf(totalByr));
                tampJudul = selectedOrder.getJudul();
                try {
                    PreparedStatement ps = connection.prepareStatement("update book set jumlah=? where judul = ?");
                    ps.setInt(1,selectedOrderBook.getJumlah()+selectedOrder.getJumlah());
                    ps.setString(2,selectedOrder.getJudul());
                    ps.executeUpdate();
                    loadDataOrderBuku();
                    System.out.println(selectedOrderBook.getJumlah() + "+" + selectedOrder.getJumlah() + " - " + selectedOrder.getId());

                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ordertable where id = ? and judul = ? and jumlah = ?");
                    preparedStatement.setString(1,transaksiId.getText());
                    preparedStatement.setString(2,selectedOrder.getJudul());
                    preparedStatement.setInt(3,selectedOrder.getJumlah());
                    preparedStatement.executeUpdate();
                    loadDataOrder();

                    PreparedStatement psmt = connection.prepareStatement("DELETE FROM history WHERE id = ? and judul = ? and jumlah = ?");
                    psmt.setString(1,transaksiId.getText());
                    psmt.setString(2,selectedOrder.getJudul());
                    psmt.setInt(3,selectedOrder.getJumlah());
                    psmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tidak ada data yang dipilih");
            alert.setHeaderText(null);
            alert.setContentText("Tidak ada data yang dipilih. Pilih data untuk diperbarui");
            alert.show();
        }}
    }

    public void saveAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Data transaksi berhasil disimpan.");
        alert.show();
        Connection connection = Database.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ordertable");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MainController.refresh();
    }
}