package org.neophyte.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.model.JadwalTayang;
import org.neophyte.utils.DataBase;

import java.io.IOException;
import java.sql.*;

public class JadwalTayangController {
    public static TableView<JadwalTayang> staticDetailMovie;

    public TableView<JadwalTayang> detailMovie;

    public Button createMovieButton;
    public Button editMovieButton;
    public TextField showSearchjadwal;
    public Button deleteMovieButton;

    JadwalTayang selectJadwal;
    public static void loadDataJadwal(){
        staticDetailMovie.getItems().setAll();
        try{
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM JADWAL_TAYANG");
            while (resultSet.next()){
                staticDetailMovie.getItems().add(new JadwalTayang(resultSet.getString("kode_tayang"), resultSet.getString("tanggal"), resultSet.getString("jam_tayang"), resultSet.getString("tittle"), resultSet.getString("genre"), resultSet.getString("Poster")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void searchLoadDataJadwal(){
        staticDetailMovie.getItems().setAll();
        try{
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM JADWAL_TAYANG");
            while (resultSet.next()){
                if ((' ' + resultSet.getString("tanggal").toLowerCase()).contains(' ' + showSearchjadwal.getText().toLowerCase()) || (' ' + resultSet.getString("tittle").toLowerCase()).contains(' ' + showSearchjadwal.getText().toLowerCase()) || (' ' + resultSet.getString("jam_tayang").toLowerCase()).contains(' ' + showSearchjadwal.getText().toLowerCase()) || (' ' + resultSet.getString("genre").toLowerCase()).contains(' ' + showSearchjadwal.getText().toLowerCase())){
                    staticDetailMovie.getItems().add(new JadwalTayang(resultSet.getString("kode_tayang"), resultSet.getString("tanggal"), resultSet.getString("jam_tayang"), resultSet.getString("tittle"), resultSet.getString("genre"), resultSet.getString("Poster")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        staticDetailMovie = detailMovie;
        loadDataJadwal();

        createMovieButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/crud/crudJadwalTayang/crudJadwalTayang.fxml"));
                Scene scene = new Scene(root);
                Stage createUser = new Stage();
                createUser.setScene(scene);
                createUser.initOwner(detailMovie.getScene().getWindow());
                createUser.initModality(Modality.WINDOW_MODAL);
                createUser.setResizable(false);
                createUser.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        showSearchjadwal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchLoadDataJadwal();
            }
        });

        editMovieButton.setOnAction(event -> {
            selectJadwal = detailMovie.getSelectionModel().getSelectedItem();
            if (selectJadwal != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    VBox vBox = fxmlLoader.load(getClass().getResource("/crud/crudJadwalTayang/crudJadwalTayang.fxml").openStream());
                    Scene editScene = new Scene(vBox);
                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    editStage.initOwner(detailMovie.getScene().getWindow());
                    editStage.initModality(Modality.WINDOW_MODAL);
                    editStage.setResizable(false);

                    CrudJadwalController crudJadwalController = fxmlLoader.getController();
                    crudJadwalController.fillForm(selectJadwal);

                    editStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Klik Data");
                alert.setHeaderText(null);
                alert.setContentText("Pilih Satu Data Untuk Di Edit!!!");
                alert.show();

            }
        });

        deleteMovieButton.setOnAction(event -> {
            selectJadwal = detailMovie.getSelectionModel().getSelectedItem();

            if (selectJadwal != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectJadwal.getJudulMovie() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = DataBase.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM JADWAL_TAYANG WHERE KODE_TAYANG = ?");
                        preparedStatement.setInt(1, Integer.parseInt(selectJadwal.getKodeTayang()));
                        preparedStatement.executeUpdate();
                        loadDataJadwal();
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
