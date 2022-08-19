package indomarco.controllers.petugas.konten;

import indomarco.models.pasiens.Pasien;
import indomarco.models.pasiens.PasienTerdaftar;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Antrian {

    @FXML
    private Label pasienTerdaftar;

    @FXML
    private TableView<PasienTerdaftar> pasienTableView;

    @FXML
    private TableColumn<PasienTerdaftar, String> no;

    @FXML
    private TableColumn<PasienTerdaftar, String> namaPasien;

    @FXML
    private TableColumn<PasienTerdaftar, String> suhu;

    @FXML
    private TableColumn<PasienTerdaftar, String > tensi;

    @FXML
    private TableColumn<PasienTerdaftar, String> layanan;

    @FXML
    private TableColumn<PasienTerdaftar, String> keterangan;

    public void initialize(){
        pasienTableView.setItems(new Pasien().dataTablePasienTungguPanggilanDokter());
        no.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNo())));
        namaPasien.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaPasien()));
        suhu.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getKesehatan().getSuhu())));
        tensi.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKesehatan().getTensi()));
        layanan.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLayanan().getNamaLayanan()));
        keterangan.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKesehatan().getKeterangan()));
    }

}
