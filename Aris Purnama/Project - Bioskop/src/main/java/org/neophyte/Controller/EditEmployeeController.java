package org.neophyte.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.model.Employee;
import org.neophyte.utils.DataBase;
import org.neophyte.validasi.ValidasiUmum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class EditEmployeeController {
    public TextField textfieldName;
    public TextArea textareaAlamat;
    public TextField textfieldNokontak;
    public TextField textfieldJabatan;
    public Button saveBtn;
    public Button cancelBtn;

    Employee employee;

    public void save(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        if (Objects.equals(textfieldName.getText(), "")) {
            alert.setContentText("Nama pengguna belum diisi!!!");
            alert.show();
        } else if (textfieldName.getText().matches(".*\\d.*")) {
            alert.setContentText("Nama Tidak Boleh Berisi Angka!!!");
            alert.show();
        } else if (ValidasiUmum.nama2(textfieldName.getText()) == false) {
            alert.setContentText("Nama Hanya Berisi Huruf dan Karakter Tentu.");
            alert.show();
        } else if (Objects.equals(textareaAlamat.getText(), "")) {
            alert.setContentText("alamat belum diisi.");
            alert.show();
        } else if (ValidasiUmum.alamat(textareaAlamat.getText()) == false) {
            alert.setContentText("Alamat Berisi Angka dan Huruf.");
            alert.show();
        } else if (Objects.equals(textfieldNokontak.getText(), "")) {
            alert.setContentText("Kontak Belum Di isi.");
            alert.show();
        } else if (ValidasiUmum.number(textfieldNokontak.getText()) == false) {
            alert.setContentText("Number HAnya Berisi Angka dan 12 Karakter.");
            alert.show();
        }else if(textfieldNokontak.getText().charAt(0) != '0' || textfieldNokontak.getText().charAt(1) != '8'){
            alert.setContentText("Number di Awali 08....");
            alert.show();
        } else if (Objects.equals(textfieldJabatan.getText(), "")) {
            alert.setContentText("Jabatan tidak boleh Koosng!!!! belum diisi.");
            alert.show();
        } else if (ValidasiUmum.nama2(textfieldJabatan.getText()) == false) {
            alert.setContentText("Jabatan Hanya Berisi Huruf.");
            alert.show();
        } else {
            try {
                Connection connection = DataBase.getConnection();
                PreparedStatement preparedStatement;
                preparedStatement = connection.prepareStatement("UPDATE EMPLOYEE SET NAMA_EMPLOYE = ?, ALAMAT_EMPLOYE = ?, NO_KONTAK = ?, JABATAN = ? WHERE ID_EMPLOYE = ?");
                preparedStatement.setInt(5, employee.getIdEmploye());
                preparedStatement.setString(1, textfieldName.getText());
                preparedStatement.setString(2, textareaAlamat.getText());
                preparedStatement.setString(3, textfieldNokontak.getText());
                preparedStatement.setString(4, textfieldJabatan.getText());
                preparedStatement.executeUpdate();
                KelolaStudioController.getAllDataEmployee();
                close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void close() {
        ((Stage) cancelBtn.getScene().getWindow()).close();
    }

    public void fillForm(Employee employee) {
        this.employee = employee;
        textfieldName.setText(employee.getNamaEmploye());
        textareaAlamat.setText(employee.getAlamatEmploye());
        textfieldNokontak.setText(employee.getNoKontak());
        textfieldJabatan.setText(employee.getJabatan());
    }
}
