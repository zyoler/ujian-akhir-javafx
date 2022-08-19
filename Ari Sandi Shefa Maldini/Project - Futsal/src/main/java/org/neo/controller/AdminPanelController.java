package org.neo.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neo.models.Admin;
import org.neo.models.Booking;
import org.neo.models.Jadwal;
import org.neo.models.Lapang;
import org.neo.util.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminPanelController {
    public static TableView<Admin> staticAdminTable;
    public static TableView<Jadwal> staticJadwalTable;
    public static TableView<Lapang> staticLapangTable;
    public static TableView<Booking> staticBookingTable;

    public TableView<Admin> adminTable;
    public TableView<Jadwal> jadwalTable;
    public TableView<Lapang> lapangTable;
    public TableView<Booking> bookingTable;
    public Button simpanButton;
    public Button editButton;
    public Button hapusButton;
    public Tab adminPage;
    public Tab jadwalPage;
    public Tab lapangPage;
    public Tab bookingPage;
    public ImageView img;

    Admin selectedAdmin;
    Jadwal selectedJadwal;
    Lapang selectedLapang;
    Booking selectedBooking;

    public void initialize(){

        img.setImage(new Image("/image/admin.png"));
        staticAdminTable = adminTable;
        staticJadwalTable = jadwalTable;
        staticLapangTable = lapangTable;
        staticBookingTable  = bookingTable;

        loadDataAdmin();
        loadDataJadwal();
        loadDataLapang();
        loadDataBooking();

        adminPage.setOnSelectionChanged(event -> {
            if(adminPage.isSelected()){
                simpanButton.setVisible(true);
            }else{
                simpanButton.setVisible(false);
            }
        });

        bookingPage.setOnSelectionChanged(event -> {
            if(bookingPage.isSelected()){
                simpanButton.setVisible(false);
                editButton.setVisible(false);
            }else{
                editButton.setVisible(true);
            }
        });

        lapangPage.setOnSelectionChanged(event -> {
            if(lapangPage.isSelected()){
                simpanButton.setVisible(false);
            }
        });

        jadwalPage.setOnSelectionChanged(event -> {
            if(jadwalPage.isSelected()){
                simpanButton.setVisible(false);
            }
        });

        hapusButton.setOnAction(event -> {
            selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
            selectedJadwal = jadwalTable.getSelectionModel().getSelectedItem();
            selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
            selectedLapang = lapangTable.getSelectionModel().getSelectedItem();

            if (selectedAdmin != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectedAdmin.getFullName() + '?');
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.connect();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ADMIN WHERE ID_ADMIN = ?");
                        preparedStatement.setInt(1, selectedAdmin.getId_admin());
                        preparedStatement.executeUpdate();
                        loadDataAdmin();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else if (selectedJadwal != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus jadwal" + selectedJadwal.getId_jadwal() + '?');
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.connect();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM JADWAL WHERE ID_JADWAL = ?");
                        preparedStatement.setInt(1, selectedJadwal.getId_jadwal());
                        preparedStatement.executeUpdate();
                        loadDataJadwal();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else if (selectedBooking != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus booking" + selectedBooking.getId_booking() + '?');
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.connect();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM BOOKING WHERE ID_BOOKING = ?");
                        preparedStatement.setInt(1, selectedBooking.getId_booking());
                        preparedStatement.executeUpdate();
                        loadDataBooking();
                        loadDataJadwal();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else if (selectedLapang != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectedLapang.getNamalpng()+ '?');
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.connect();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM LAPANG WHERE ID_LAPANG = ?");
                        preparedStatement.setInt(1, selectedLapang.getIdlpng());
                        preparedStatement.executeUpdate();
                        loadDataLapang();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        simpanButton.setOnAction(event -> {
                try {
                    VBox vBox = FXMLLoader.load(getClass().getResource("/crud/crudAdmin.fxml"));
                    Scene createScene = new Scene(vBox);
                    Stage createStage = new Stage();
                    createStage.setScene(createScene);
                    createStage.initOwner(adminTable.getScene().getWindow());
                    createStage.initModality(Modality.WINDOW_MODAL);
                    createStage.setResizable(false);
                    createStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });

        editButton.setOnAction(event -> {
            selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
            selectedJadwal = jadwalTable.getSelectionModel().getSelectedItem();
            selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
            selectedLapang = lapangTable.getSelectionModel().getSelectedItem();

            if (selectedAdmin != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    VBox vBox = fxmlLoader.load(getClass().getResource("/crud/crudAdmin.fxml").openStream());
                    Scene editScene = new Scene(vBox);
                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    editStage.initOwner(adminTable.getScene().getWindow());
                    editStage.initModality(Modality.WINDOW_MODAL);
                    editStage.setResizable(false);

                    CrudAdmin crudAdmin = fxmlLoader.getController();
                    crudAdmin.fillForm(selectedAdmin);

                    editStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (selectedJadwal != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    VBox vBox = fxmlLoader.load(getClass().getResource("/crud/crudJadwal.fxml").openStream());
                    Scene editScene = new Scene(vBox);
                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    editStage.initOwner(jadwalTable.getScene().getWindow());
                    editStage.initModality(Modality.WINDOW_MODAL);
                    editStage.setResizable(false);

                    CrudJadwal crudJadwal = fxmlLoader.getController();
                    crudJadwal.fillForm(selectedJadwal);

                    editStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (selectedLapang != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    VBox vBox = fxmlLoader.load(getClass().getResource("/crud/crudLapang.fxml").openStream());
                    Scene editScene = new Scene(vBox);
                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    editStage.initOwner(lapangTable.getScene().getWindow());
                    editStage.initModality(Modality.WINDOW_MODAL);
                    editStage.setResizable(false);

                    CrudLapang crudLapang = fxmlLoader.getController();
                    crudLapang.fillForm(selectedLapang);

                    editStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void loadDataLapang() {
        staticLapangTable.getItems().setAll();
        try {
            Connection connection = Database.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM LAPANG");
            while (resultSet.next()) {
                staticLapangTable.getItems().add(new Lapang(resultSet.getInt("id_lapang"),resultSet.getString("namalpng"),resultSet.getString("jenislpng"),resultSet.getInt("harga"),resultSet.getString("keterangan"),resultSet.getString("gambar")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadDataJadwal() {
        staticJadwalTable.getItems().setAll();
        try {
            Connection connection = Database.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT j.ID_JADWAL,b.NAMA_PEMESAN,b.NO_HP,b.TGL_MAIN,b.PUKUL,l.NAMALPNG,b.ID_BOOKING FROM JADWAL j, BOOKING b, LAPANG l where j.ID_BOOKING=b.ID_BOOKING and b.ID_LAPANG=l.ID_LAPANG and b.STATUS IS NOT NULL");
            while (resultSet.next()) {
                staticJadwalTable.getItems().add(new Jadwal(resultSet.getInt("id_jadwal"),resultSet.getString("nama_pemesan"),resultSet.getString("no_hp"),resultSet.getString("tgl_main"),resultSet.getString("pukul"),resultSet.getString("namalpng"),resultSet.getInt("id_booking")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadDataAdmin() {
        staticAdminTable.getItems().setAll();
        try {
            Connection connection = Database.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ADMIN");
            while (resultSet.next()) {
                staticAdminTable.getItems().add(new Admin(resultSet.getInt("id_admin"),resultSet.getString("fullname"),resultSet.getString("nohp"),resultSet.getString("username"), resultSet.getString("password")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadDataBooking() {
        staticBookingTable.getItems().setAll();
        try {
            Connection connection = Database.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM BOOKING");
            while (resultSet.next()) {
                staticBookingTable.getItems().add(new Booking(resultSet.getInt("id_booking"),resultSet.getString("nama_pemesan"),resultSet.getString("no_hp"),resultSet.getString("tgl_pesan"),resultSet.getString("tgl_main"),resultSet.getInt("lama"),resultSet.getString("pukul"),resultSet.getInt("total"),resultSet.getInt("id_lapang"),resultSet.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
