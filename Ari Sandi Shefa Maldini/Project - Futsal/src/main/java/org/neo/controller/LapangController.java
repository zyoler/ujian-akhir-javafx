package org.neo.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.neo.util.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LapangController {

    public VBox lp;
    public Button detail;
    public int x=1;
    public ImageView img;

    public void initialize(){
    img.setImage(new Image("/image/lapang.png"));
        try {
            Connection connection = Database.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM LAPANG");
            while (resultSet.next()) {
                HBox lpa = new HBox();
                HBox lpa2 = new HBox();
                VBox lpa3 = new VBox();
                detail = new Button("Detail"+x);
                lpa3.setSpacing(5);
                lpa.getStyleClass().add("lapang");
                detail.getStyleClass().add("buttonDetail");
                ImageView poster = new ImageView(new Image("/image/"+resultSet.getString("GAMBAR")));
                poster.setFitWidth(70);
                poster.setFitHeight(70);
                String namalpng = resultSet.getString("namalpng");
                String jenislpng = resultSet.getString("jenislpng");
                String ket = resultSet.getString("keterangan");
                Label namalp = new Label(namalpng);
                Label jenislp = new Label(jenislpng);
                Label keterangan = new Label(ket);

                lpa.getChildren().setAll(lpa2,lpa3);
                lpa2.getChildren().add(poster);
                lpa3.getChildren().setAll(namalp,jenislp,keterangan);

                lp.getChildren().add(lpa);
                x++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        detail.setOnAction(event -> {
            System.out.println("Cek");
        });
    }
}
