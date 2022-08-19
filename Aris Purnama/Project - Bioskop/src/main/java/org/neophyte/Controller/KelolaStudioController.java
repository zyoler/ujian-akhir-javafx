package org.neophyte.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.neophyte.model.Employee;
import org.neophyte.model.JadwalTayang;
import org.neophyte.model.Tiket;
import org.neophyte.model.User;
import org.neophyte.utils.DataBase;

import java.io.IOException;
import java.sql.*;

public class KelolaStudioController {
    public static TableView<User> staticUserTable;
    public static TableView<Employee> staticEmployeTable;
    public static TableView<JadwalTayang> staticMovieTable;
    public static TableView<Tiket> statikTiketTable;

    public TableView<User> userTable;
    public TableView<Employee> employeeTable;
    public TableView<JadwalTayang> movieTable;
    public TableView<Tiket> tiketTable;

    public Button createEmployeButton;
    public Button editEmployeButton;
    public Button deleteEmployeButton;
    public Tab tombolEmploye;

    public Button createUserButton;
    public Button editUserButton;
    public Button deleteUserButton;

    public Button createMovieButton;
    public Button editMovieButton;
    public Button deleteMovieButton;

    public Tab tombolTiket;
    public Button createTiketButton;
    public Button editTiketButton;
    public Button deleteTiketButton;


    public TextField showSearchUserTextfield;
    public TextField showSearchEmployeeTextfield;
    public TableColumn idUser;
    public TextField showSearchTiketTextfield;

    User selectedUser;
    Employee selctedEmploye;
    Tiket selectedTiket;

    ///////////////////////////////////////////////////////// LOAD DATA ////////////////////////////////////////////////////
    public static void loadData() {
        staticUserTable.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USER");
            while (resultSet.next()) {
                staticUserTable.getItems().add(new User(resultSet.getInt("ID"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("nama_user"), resultSet.getBoolean("admin")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllDataEmployee() {
        staticEmployeTable.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
            while (resultSet.next()) {
                staticEmployeTable.getItems().add(new Employee(resultSet.getInt("id_employe"), resultSet.getString("nama_employe"), resultSet.getString("alamat_employe"), resultSet.getString("no_kontak"), resultSet.getString("jabatan")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void getAllDatatiket() {
        statikTiketTable.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TIKET");
            while (resultSet.next()) {
                statikTiketTable.getItems().add(new Tiket(resultSet.getString("kode_tiket"), resultSet.getString("jenis_tiket"), resultSet.getInt("harga")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //////////////////////////////////////////// MENAMPILKAN DATA DI COMBO BOX//////////////////////////////////////////////
    public void searchDataUser() {
        staticUserTable.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                if (resultSet.getString("username").startsWith(showSearchUserTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("nama_user").toLowerCase()).contains(' ' + showSearchUserTextfield.getText().toLowerCase()))
                    staticUserTable.getItems().add(new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("nama_user"), resultSet.getBoolean("admin")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void searchDataTiket() {
        statikTiketTable.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM TIKET");
            while (resultSet.next()) {
                if ((resultSet.getString("kode_tiket").toLowerCase()).startsWith(showSearchTiketTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("jenis_tiket").toLowerCase()).contains(' ' + showSearchTiketTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("harga").toLowerCase()).contains(' ' + showSearchTiketTextfield.getText().toLowerCase()))
                statikTiketTable.getItems().add(new Tiket(resultSet.getString("kode_tiket"), resultSet.getString("jenis_tiket"), resultSet.getInt("harga")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void searchDataEmployee() {
        staticEmployeTable.getItems().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
            while (resultSet.next()) {
                if (resultSet.getString("alamat_employe" + "").startsWith(showSearchEmployeeTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("nama_employe").toLowerCase()).contains(' ' + showSearchEmployeeTextfield.getText().toLowerCase()) || (' ' + resultSet.getString("jabatan").toLowerCase()).contains(' ' + showSearchEmployeeTextfield.getText().toLowerCase()))
                    staticEmployeTable.getItems().add(new Employee(resultSet.getInt("id_employe"), resultSet.getString("nama_employe"), resultSet.getString("alamat_employe"), resultSet.getString("no_kontak"), resultSet.getString("jabatan")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    ////////////////////////////////////////// INITIALIZE //////////////////////////////////////////////////////////////////
    public void initialize() {
        staticUserTable = userTable;
        staticEmployeTable = employeeTable;
        staticMovieTable = movieTable;
        statikTiketTable = tiketTable;
        loadData();
        getAllDataEmployee();
        getAllDatatiket();

        showSearchUserTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                searchDataUser();
            }
        });
        showSearchEmployeeTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchDataEmployee();
            }
        });
        showSearchTiketTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchDataTiket();
            }
        });
//////////////////////////////////////////////////////CRUD USER/////////////////////////////////////////////////////////
        createUserButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/crud/crudUser/create_user.fxml"));
                Scene scene = new Scene(root);
                Stage createUser = new Stage();
                createUser.setScene(scene);
                createUser.initOwner(userTable.getScene().getWindow());
                createUser.initModality(Modality.WINDOW_MODAL);
                createUser.setResizable(false);
                createUser.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        editUserButton.setOnAction(event -> {
            selectedUser = userTable.getSelectionModel().getSelectedItem();

            if (selectedUser != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    VBox vBox = fxmlLoader.load(getClass().getResource("/crud/crudUser/create_user.fxml").openStream());
                    Scene editScene = new Scene(vBox);
                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    editStage.initOwner(userTable.getScene().getWindow());
                    editStage.initModality(Modality.WINDOW_MODAL);
                    editStage.setResizable(false);

                    CrudUserController crudUserController = fxmlLoader.getController();
                    crudUserController.fillForm(selectedUser);

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
        deleteUserButton.setOnAction(event -> {
            selectedUser = userTable.getSelectionModel().getSelectedItem();

            if (selectedUser != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selectedUser.getNamaUser() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = DataBase.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
                        preparedStatement.setInt(1, selectedUser.getId());
                        preparedStatement.executeUpdate();
                        loadData();
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
//////////////////////////////////////////CRUD EMPLOYEE/////////////////////////////////////////////////////////////////
        createEmployeButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/crud/crudEmployee/create_employee.fxml"));
                Scene scene = new Scene(root);
                Stage createEmploye = new Stage();
                createEmploye.setScene(scene);
                createEmploye.initOwner(employeeTable.getScene().getWindow());
                createEmploye.initModality(Modality.WINDOW_MODAL);
                createEmploye.setResizable(false);
                createEmploye.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        editEmployeButton.setOnAction(event -> {
            selctedEmploye = employeeTable.getSelectionModel().getSelectedItem();

            if (selctedEmploye != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    VBox vBox = fxmlLoader.load(getClass().getResource("/crud/crudEmployee/edit_employee.fxml").openStream());
                    Scene editScene = new Scene(vBox);
                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    editStage.initOwner(userTable.getScene().getWindow());
                    editStage.initModality(Modality.WINDOW_MODAL);
                    editStage.setResizable(false);

                    EditEmployeeController editEmployeeController = fxmlLoader.getController();
                    editEmployeeController.fillForm(selctedEmploye);

                    editStage.show();
                } catch (IOException e) {
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
        deleteEmployeButton.setOnAction(event -> {
            selctedEmploye = employeeTable.getSelectionModel().getSelectedItem();

            if (selctedEmploye != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hapus pengguna");
                alert.setHeaderText(null);
                alert.setContentText("Apakah Anda ingin menghapus " + selctedEmploye.getNamaEmploye() + '?');
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    try {
                        Connection connection = DataBase.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM EMPLOYEE WHERE ID_EMPLOYE = ?");
                        preparedStatement.setInt(1, selctedEmploye.getIdEmploye());
                        preparedStatement.executeUpdate();
                        getAllDataEmployee();
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
//////////////////////////////////////////CRUD TIKET/////////////////////////////////////////////////////////////////
        createTiketButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/crud/crudTiket/createTiket.fxml"));
                Scene scene = new Scene(root);
                Stage createTiket = new Stage();
                createTiket.setScene(scene);
                createTiket.initOwner(userTable.getScene().getWindow());
                createTiket.initModality(Modality.WINDOW_MODAL);
                createTiket.setResizable(false);
                createTiket.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        editTiketButton.setOnAction(event -> {
            selectedTiket = tiketTable.getSelectionModel().getSelectedItem();

            if (selectedTiket != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    VBox vBox = fxmlLoader.load(getClass().getResource("/crud/crudTiket/createTiket.fxml").openStream());
                    Scene editScene = new Scene(vBox);
                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    editStage.initOwner(tiketTable.getScene().getWindow());
                    editStage.initModality(Modality.WINDOW_MODAL);
                    editStage.setResizable(false);

                    CreateTiketController createTiketController = fxmlLoader.getController();
                    createTiketController.fillForm(selectedTiket);

                    editStage.show();
                } catch (IOException e) {
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
    }
}
