package indomarco.controllers.petugas.konten;

import indomarco.Main;
import indomarco.controllers.petugas.newstage.Create;
import indomarco.models.pasiens.PasienTerdaftar;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class Index {
    public TableView<PasienTerdaftar> antrianCekSuhu;
    public TableColumn<PasienTerdaftar, Integer> no;
    public TableColumn<PasienTerdaftar, String> namaPasien;
    public TableColumn<PasienTerdaftar, String> keterangan;

    public Label labelBelumCek;

    //static

    public static Label label;
    public static TableView<PasienTerdaftar> list;

    public void initialize(){
        label = labelBelumCek;
        list = antrianCekSuhu;
        reload();
    }

    public static void staticReload(){
        PasienTerdaftar pasien = new PasienTerdaftar();
        list.setItems(pasien.allNowPasienProses());
        label.setText("Jumlah pasien dalam antrian : " + pasien.pasienBelumCek() + " Orang");
    }

    public void reload(){
        staticReload();
        no.setCellValueFactory(new PropertyValueFactory<PasienTerdaftar, Integer>("no"));
        namaPasien.setCellValueFactory(new PropertyValueFactory<PasienTerdaftar, String>("namaPasien"));
        keterangan.setCellValueFactory(new PropertyValueFactory<PasienTerdaftar, String>("keterangan"));

        antrianCekSuhu.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2){
                PasienTerdaftar dipilih = antrianCekSuhu.getSelectionModel().getSelectedItem();
                clickItem(dipilih);
            }
        });
    }

    public void clickItem(PasienTerdaftar pasien){
        Stage createStage = new Stage();
        Create.create = createStage;
        createStage.initOwner(Main.window);
        createStage.initModality(Modality.WINDOW_MODAL);
        createStage.setResizable(false);
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("/views/petugas/newstage/create.fxml"));
        Create controller = new Create(pasien);
        controller.pasien = pasien;
        load.setController(controller);
        try{
            createStage.setScene(new Scene(load.load()));
            createStage.show();
        }catch (Exception e){
            System.out.println(e);
        }

    }

}
