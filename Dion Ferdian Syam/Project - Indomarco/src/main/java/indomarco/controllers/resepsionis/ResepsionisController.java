package indomarco.controllers.resepsionis;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ResepsionisController {
    public HBox mainResepsionis;
    public VBox sidebar;

    public static String aktifMenu = "pasien-hari-ini";
    public static HBox staticMainResepsionis;

    public void initialize(){
        staticMainResepsionis = mainResepsionis;
        setMainResepsionis();
    }
    public void hariIniAksi(){
        aktifMenu = "pasien-hari-ini";
        setMainResepsionis();
    }
    public void dataPasienAksi(){
        aktifMenu = "data_pasien";
        setMainResepsionis();
    }
    public static void setMainResepsionis() {
        FXMLLoader loader = new FXMLLoader();
        String location = "/views/resepsionis/" + aktifMenu + ".fxml";
        loader.setLocation(ResepsionisController.class.getResource(location));
        try {
            VBox load = loader.load();
            staticMainResepsionis.getChildren().setAll(load);
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void restart(String aktif){
        aktifMenu = aktif;
        setMainResepsionis();
    }
}
