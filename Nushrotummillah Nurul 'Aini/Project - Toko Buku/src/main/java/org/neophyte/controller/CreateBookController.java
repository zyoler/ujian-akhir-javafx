package org.neophyte.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.model.Book;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class CreateBookController {

    public TextField tittleField;
    public TextField authorField;
    public TextField publisherField;
    public TextField yearField;
    public TextField descField;
    public TextField categoryField;
    public TextField priceField;
    public TextField itemField;
    public Button cancel;
    public Button saveButton;

    Book buku;


    public void saveBookAction() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (Objects.equals(tittleField.getText(), "")) {
            alert.setContentText("Judul belum diisi.");
            alert.show();
        } else if (Objects.equals(authorField.getText(), "")) {
            alert.setContentText("Penulis belum diisi.");
            alert.show();
        } else if (Objects.equals(publisherField.getText(), "")) {
            alert.setContentText("Penerbit belum diisi.");
            alert.show();
        } else if (Objects.equals(yearField.getText(), "")) {
            alert.setContentText("Tahun belum diisi.");
            alert.show();
        } else if (Objects.equals(descField.getText(), "")) {
            alert.setContentText("Deskripsi belum diisi.");
            alert.show();
        } else if (Objects.equals(categoryField.getText(), "")) {
            alert.setContentText("Kategori belum diisi.");
            alert.show();
        } else if (Objects.equals(priceField.getText(), "")) {
            alert.setContentText("Harga belum diisi.");
            alert.show();
        } else if (Objects.equals(itemField.getText(), "")) {
            alert.setContentText("Jumlah belum diisi.");
            alert.show();
        }else {
            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement;
                if (buku == null)
                    preparedStatement = connection.prepareStatement("INSERT INTO book (judul, pengarang, penerbit, tahun, deskripsi, kategori, harga, jumlah) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                else {
                    preparedStatement = connection.prepareStatement("UPDATE book SET judul=?, pengarang=?, penerbit=?, tahun=?, deskripsi=?, kategori=?, harga=?, jumlah=? WHERE id = ?");
                    preparedStatement.setInt(9, buku.getId());
                }
                preparedStatement.setString(1, tittleField.getText());
                preparedStatement.setString(2, authorField.getText());
                preparedStatement.setString(3, publisherField.getText());
                preparedStatement.setString(4, yearField.getText());
                preparedStatement.setString(5, descField.getText());
                preparedStatement.setString(6, categoryField.getText());
                preparedStatement.setString(7, priceField.getText());
                preparedStatement.setString(8, itemField.getText());
                preparedStatement.executeUpdate();
                ProductController.loadDataBuku();
                cancelButton();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelButton() {
        ((Stage) cancel.getScene().getWindow()).close();
    }

    public void fillForm(Book buku){
        this.buku = buku;
        tittleField.setText(buku.getJudul());
        authorField.setText(buku.getPengarang());
        publisherField.setText(buku.getPenerbit());
        yearField.setText(String.valueOf(buku.getTahun()));
        descField.setText(buku.getDeskripsi());
        categoryField.setText(buku.getKategori());
        priceField.setText(String.valueOf(buku.getHarga()));
        itemField.setText(String.valueOf(buku.getJumlah()));
    }
}
