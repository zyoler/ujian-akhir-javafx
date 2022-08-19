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
import org.neophyte.model.Book;
import org.neophyte.util.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductController {

    public static TableView<Book> staticProductTable;
    public TableView<Book> productTable;

    Book selectedProduct;

    public Button addButton;
    public Button editButton;
    public Button deleteButton;

    public static void loadDataBuku() {
        staticProductTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book");
            while (resultSet.next()) {
                staticProductTable.getItems().add(new Book(resultSet.getInt("id"), resultSet.getString("judul"), resultSet.getString("pengarang"), resultSet.getString("penerbit"), resultSet.getInt("tahun"), resultSet.getString("deskripsi"), resultSet.getString("kategori"), resultSet.getInt("harga"), resultSet.getInt("jumlah")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        staticProductTable = productTable;
        loadDataBuku();

        if(!(MainController.log)){
            addButton.setDisable(true);
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }

    public void addButton() {
        try {
            VBox vBox = FXMLLoader.load(getClass().getResource("/create_book.fxml"));
            Scene addScene = new Scene(vBox);
            Stage addStage = new Stage();
            addStage.setScene(addScene);
            addStage.initOwner(productTable.getScene().getWindow());
            addStage.initModality(Modality.WINDOW_MODAL);
            addStage.setResizable(false);
            addStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editButton() {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct != null){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                VBox vBox = fxmlLoader.load(getClass().getResource("/create_book.fxml").openStream());
                Scene editScene = new Scene(vBox);
                Stage editStage = new Stage();
                editStage.setScene(editScene);
                editStage.initOwner(productTable.getScene().getWindow());
                editStage.initModality(Modality.WINDOW_MODAL);
                editStage.setResizable(false);
                CreateBookController createBookController = fxmlLoader.getController();
                createBookController.fillForm(selectedProduct);
                editStage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tidak ada data yang dipilih");
            alert.setHeaderText(null);
            alert.setContentText("Tidak ada data yang dipilih. Pilih data untuk diperbarui");
            alert.show();
        }
    }

    public void deleteButton() {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Hapus Buku");
            alert.setHeaderText(null);
            alert.setContentText("Anda yakin ingin menghapus buku " + selectedProduct.getJudul() + "?");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM book WHERE id = ?");
                    preparedStatement.setInt(1, selectedProduct.getId());
                    preparedStatement.executeUpdate();
                    loadDataBuku();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tidak ada data yang dipilih");
            alert.setHeaderText(null);
            alert.setContentText("Tidak ada data yang dipilih. Pilih data untuk dihapus.");
            alert.show();
        }
    }

    public void backButton() {
        MainController.refresh();
    }
}
