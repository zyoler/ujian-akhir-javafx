package org.neophyte.controller.adminpages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.controller.Validation;
import org.neophyte.model.Pegawai;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PegawaiController {

    public static TableView<Pegawai> staticUserTable;
    public static int nampungId;

    public TextField idField;
    public TextField nameField;
    public TextField alamatField;
    public TextField usiaField;
    public TextField telpField;
    public RadioButton ikhwanButton;
    public RadioButton akhwatButton;
    public Button simpanButton;
    public Button cancelButton;
    public Button deleteButton;
    public Button editButton;
    public TextField searchField;
    public TableView<Pegawai> pegawaiTable;

    Pegawai selectedPegawai;

    public static void loadData() {
        staticUserTable.getItems().setAll();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PEGAWAI");
            nampungId = 1;
            while (resultSet.next()){
                staticUserTable.getItems().add(new Pegawai(resultSet.getString("id"),resultSet.getString("nama"),resultSet.getString("alamat"),resultSet.getString("usia"),resultSet.getString("jk"),resultSet.getString("telp")));
                nampungId = Integer.parseInt(resultSet.getString("id").substring(3)) + 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialize() {
        validation();
        staticUserTable = pegawaiTable;
        loadData();cekID();
        idField.setDisable(true);

        final ToggleGroup group = new ToggleGroup();
        ikhwanButton.setToggleGroup(group);
        akhwatButton.setToggleGroup(group);

        simpanButton.setOnAction(event -> {
            ArrayList<Object> cek = new ArrayList<>();
            cek.add(nameField.getText());cek.add(alamatField.getText());cek.add(usiaField.getText());cek.add(telpField.getText());
            if(cek.contains("")){
                alert();
            }else if(!(ikhwanButton.isSelected() || akhwatButton.isSelected())){
                alert();
            }else{
                try {
                    Connection connection = Database.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO pegawai VALUES (?,?,?,?,?,?)");
                    preparedStatement.setString(1, idField.getText());
                    preparedStatement.setString(2,nameField.getText());
                    preparedStatement.setString(3,alamatField.getText());
                    preparedStatement.setString(4,usiaField.getText());
                    if(ikhwanButton.isSelected()){
                        preparedStatement.setString(5,"Laki-laki");
                    }else{
                        preparedStatement.setString(5,"Perempuan");
                    }
                    preparedStatement.setString(6,telpField.getText());
                    preparedStatement.executeUpdate();
                    loadData();cekID();hapusField();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        editButton.setOnAction(event -> {
            selectedPegawai = pegawaiTable.getSelectionModel().getSelectedItem();
            if(selectedPegawai != null){
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Parent root = fxmlLoader.load(getClass().getResource("/pages/mainpages/edit_data_pegawai.fxml").openStream());
                    Scene editScene = new Scene(root);
                    Stage editStage = new Stage();
                    editScene.getStylesheets().add("styles/style.css");
                    editStage.setScene(editScene);
                    editStage.initOwner(pegawaiTable.getScene().getWindow());
                    editStage.initModality(Modality.WINDOW_MODAL);
                    editStage.setResizable(false);
                    EditPegawaiController editPegawaiController = fxmlLoader.getController();
                    editPegawaiController.fillForm(selectedPegawai);
                    editStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        deleteButton.setOnAction(event -> {
            selectedPegawai = pegawaiTable.getSelectionModel().getSelectedItem();
            if(selectedPegawai != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectedPegawai.getNama() + '?');
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = Database.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pegawai WHERE id = ?");
                        preparedStatement.setString(1,selectedPegawai.getId());
                        preparedStatement.executeUpdate();
                        loadData();cekID();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        cancelButton.setOnAction(event -> {
            hapusField();
        });
    }

    public void validation(){
        Validation.textOnly(nameField);
        Validation.numericOnly(telpField);Validation.addTextLimiter(telpField,13);
        Validation.numericOnly(usiaField);Validation.addTextLimiter(usiaField,2);
    }

    public void alert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Pastikan data terisi semua");
        alert.show();
    }

    public void cekID(){
        if(nampungId < 10)
            idField.setText("PGW00" + nampungId);
        else if(nampungId < 100)
            idField.setText("PGW0" + nampungId);
        else
            idField.setText("PGW" + nampungId);
    }

    public void hapusField(){
        nameField.setText("");alamatField.setText("");usiaField.setText("");telpField.setText("");ikhwanButton.setSelected(false);akhwatButton.setSelected(false);
    }
}
