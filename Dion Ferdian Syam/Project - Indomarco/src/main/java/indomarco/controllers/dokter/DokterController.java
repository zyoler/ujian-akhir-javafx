package indomarco.controllers.dokter;

import indomarco.models.pasiens.Pasien;
import indomarco.models.pasiens.PasienTerdaftar;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DokterController {
    public TableView<PasienTerdaftar> dataPanggil;
    public TableColumn<PasienTerdaftar, String> no;
    public TableColumn<PasienTerdaftar, String> nama;

    public HBox main;

    public void initialize(){
        dataPanggil.setItems(new Pasien().dataTablePasienTungguPanggilanDokter());
        no.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNo())));
        nama.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNamaPendek()));
        dataPanggil.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2){
                PasienTerdaftar dipilih = dataPanggil.getSelectionModel().getSelectedItem();
                clickItem(dipilih);
            }
        });
    }
    public void clickItem(PasienTerdaftar pasien){
        try {
            FXMLLoader loader = new FXMLLoader();
            Index pasienIndex = new Index(pasien);
            loader.setController(pasienIndex);
            loader.setLocation(getClass().getResource("/views/dokter/index.fxml"));
            VBox hBox = loader.load();
            main.getChildren().setAll(hBox);
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}
