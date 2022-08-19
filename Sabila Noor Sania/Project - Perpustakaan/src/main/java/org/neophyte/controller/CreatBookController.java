package org.neophyte.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.model.Buku;
import org.neophyte.model.User;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreatBookController {
    public Button saveButton;
    public Button cancleButton;

    public TextField judulField;
    public TextField hargaField;
    public TextField kategoriField;
    public TextField tahunTerbitField;
    public TextField penerbitField;
    public TextField penulisField;

    Buku buku;

    public void close() {
        ((Stage) cancleButton.getScene().getWindow()).close();
    }

    public void save() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);


        if(penulisField.getText().matches(".*\\d.*")){
            alert.setContentText("Penulis Tidak Boleh Berisi Angka");
            alert.show();
        }else if (penerbitField.getText().matches(".*\\d.*")){
            alert.setContentText("Penerbit Tidak Boleh Berisi Angka");
            alert.show();
        }else if (kategoriField.getText().matches(".*\\d.*")){
            alert.setContentText("Kategori Tidak Boleh Berisi Angka");
            alert.show();
        } else {
            try {
                Connection connection = Database.getConnection();
                PreparedStatement preparedStatement;
                if (buku == null) 
                    preparedStatement = connection.prepareStatement("INSERT INTO BOOK (judulbuku, penulis, penerbit, tahunterbit, kategori, harga) VALUES (?, ?, ?, ?, ?, ?)");
                else { 
                    preparedStatement = connection.prepareStatement("UPDATE BOOK SET JUDULBUKU = ?, PENULIS= ?, PENERBIT = ?, TAHUNTERBIT = ?, KATEGORI=?, HARGA =? WHERE id = ?");
                    preparedStatement.setInt(7, buku.getId());
                }
                preparedStatement.setString(1, judulField.getText());
                preparedStatement.setString(2, penulisField.getText());
                preparedStatement.setString(3, penerbitField.getText());
                preparedStatement.setString(4, tahunTerbitField.getText());
                preparedStatement.setString(5, kategoriField.getText());
                preparedStatement.setInt(6, Integer.parseInt(hargaField.getText()));
                preparedStatement.executeUpdate();
                CrudBukuController.loadData();
                close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
   }
    public void fillForm(Buku buku) {
        this.buku = buku;
        judulField.setText(buku.getJudulbuku());
        penulisField.setText(buku.getPenulis());
        penerbitField.setText(buku.getPenerbit());
        tahunTerbitField.setText(buku.getTahunterbit());
        kategoriField.setText(buku.getKategori());
        hargaField.setText(Integer.toString(buku.getHarga()));
    }
}
