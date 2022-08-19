package org.neophyte.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StruckController {
    public Label tanggalLabel;
    public Label judulLabel;
    public Label jamLabel;
    public Label jmlLabel;
    public Label totalLabel;
    public Label kodeLabel;
    public Button btnNext;

    public void initialize(){
        tanggalLabel.setText(PemesananController.date.toString());
        judulLabel.setText(PemesananController.Judul);
        jamLabel.setText(PemesananController.time.toString());
        jmlLabel.setText(PemesananController.jumlah.toString());
        kodeLabel.setText(String.valueOf(Math.random()));

        btnNext.setOnAction(event -> {
            ((Stage) btnNext.getScene().getWindow()).close();
        });

    }
}
