package org.neophyte.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.Obat;
import org.neophyte.model.Supplier;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataController {
    public static TableView<Obat> staticObatTable;
    public TableView<Obat> obatTable;
    public Button createObatButton;
    public Button editObatButton;
    public Button deleteObatButton;
    public Button kembaliObatButton;

    Obat selectedObat;

    public static TableView<Supplier> staticSuppTable;
    public static int nampungId;
    public static int nampungId2;
    public TableView<Supplier> supplierTable;
    public Button createSuppButton;
    public Button editSuppButton;
    public Button deleteSuppButton;
    public Button kembaliSuppButton;

    Supplier selectedSupp;


    public static void loadData() {
        staticObatTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM obat");
            while (resultSet.next()) {
                staticObatTable.getItems().add(new Obat(resultSet.getString("idObat"), resultSet.getString("namaObat"), resultSet.getInt("hargaBeli"), resultSet.getInt("hargaJual"), resultSet.getInt("stok")));
                nampungId = Integer.parseInt(resultSet.getString("idObat").substring(2)) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loadDataSupp(){
        staticSuppTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM supplier");
            while (resultSet.next()) {
                staticSuppTable.getItems().add(new Supplier(resultSet.getString("idSupplier"), resultSet.getString("namaSupplier"), resultSet.getString("noTelepon"), resultSet.getString("alamat")));
                nampungId2 = Integer.parseInt(resultSet.getString("idSupplier").substring(2)) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void forSupp(){
        staticSuppTable = supplierTable;
        loadDataSupp();

        createSuppButton.setOnAction(event -> {
            try {
                VBox vBox = FXMLLoader.load(getClass().getResource("/crud/create_supplier.fxml"));
                Scene createScene = new Scene(vBox);
                Stage createStage = new Stage();
                createStage.setScene(createScene);
                createStage.initOwner(supplierTable.getScene().getWindow());
                createStage.initModality(Modality.WINDOW_MODAL);
                createStage.setResizable(false);
                createStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        deleteSuppButton.setOnAction(event -> {
            selectedSupp = supplierTable.getSelectionModel().getSelectedItem();

            if (selectedSupp != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus data Supplier");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectedSupp.getNamaSupplier() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM supplier WHERE idSupplier = ?");
                        preparedStatement.setString(1, selectedSupp.getIdSupplier());
                        preparedStatement.executeUpdate();
                        loadDataSupp();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Pilih yang ingin di hapus");
                alert.show();
            }
        });
        kembaliSuppButton.setOnAction(event -> {
            ((Stage) kembaliSuppButton.getScene().getWindow()).close();
            Main.refresh();
        });
    }
    public void forObat(){
        staticObatTable = obatTable;
        loadData();

        createObatButton.setOnAction(event -> {
            try {
                VBox vBox = FXMLLoader.load(getClass().getResource("/crud/create_obat.fxml"));
                Scene createScene = new Scene(vBox);
                Stage createStage = new Stage();
                createStage.setScene(createScene);
                createStage.initOwner(obatTable.getScene().getWindow());
                createStage.initModality(Modality.WINDOW_MODAL);
                createStage.setResizable(false);
                createStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        deleteObatButton.setOnAction(event -> {
            selectedObat = obatTable.getSelectionModel().getSelectedItem();

            if (selectedObat != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus data Obat");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectedObat.getNamaObat() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM obat WHERE idObat = ?");
                        preparedStatement.setString(1, selectedObat.getIdObat());
                        preparedStatement.executeUpdate();
                        loadData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        kembaliObatButton.setOnAction(event -> {
            ((Stage) kembaliObatButton.getScene().getWindow()).close();
            Main.refresh();
        });
    }
    public void editObat() {
        selectedObat = obatTable.getSelectionModel().getSelectedItem();

        if (selectedObat != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                VBox vBox = fxmlLoader.load(getClass().getResource("/crud/create_obat.fxml").openStream());
                Scene editScene = new Scene(vBox);
                Stage editStage = new Stage();
                editStage.setScene(editScene);
                editStage.initOwner(obatTable.getScene().getWindow());
                editStage.initModality(Modality.WINDOW_MODAL);
                editStage.setResizable(false);

                CreateObatController createObatController = fxmlLoader.getController();
                createObatController.fillForm(selectedObat);

                editStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Pilih yang ingin di edit");
            alert.show();
        }
    }
    public void editSupp() {
        selectedSupp = supplierTable.getSelectionModel().getSelectedItem();

        if (selectedSupp != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                VBox vBox = fxmlLoader.load(getClass().getResource("/crud/create_supplier.fxml").openStream());
                Scene editScene = new Scene(vBox);
                Stage editStage = new Stage();
                editStage.setScene(editScene);
                editStage.initOwner(obatTable.getScene().getWindow());
                editStage.initModality(Modality.WINDOW_MODAL);
                editStage.setResizable(false);

                CreateSupplierController createSupplierController = fxmlLoader.getController();
                createSupplierController.fillForm(selectedSupp);

                editStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Pilih yang ingin di edit");
            alert.show();
        }
    }
}
