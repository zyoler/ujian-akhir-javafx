package org.neo.controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.util.Duration;
import org.neo.main.Main;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;

public class BerandaController {
    public Pagination page;
    public Label labelSelamat;
    public Label labelTgl;
    public Label labelJam;
    public ImageView img;
    private volatile boolean stop = false;
    Thread thread;

    public void initialize() {

        img.setImage(new Image("/image/home.png"));

        timeNow();
        LocalTime sekarang = LocalTime.now();
        LocalDate ayna = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM uuuu", Locale.ENGLISH);
        String waktu;
        if (sekarang.getHour() < 3)
            waktu = "malam";
        else if (sekarang.getHour() < 10)
            waktu = "pagi";
        else if (sekarang.getHour() < 15)
            waktu = "siang";
        else if (sekarang.getHour() < 19)
            waktu = "sore";
        else
            waktu = "malam";

        if (Main.admLogged == null)
            labelSelamat.setText("Selamat " + waktu + " pemain!");
        else
            labelSelamat.setText("Selamat " + waktu + ' ' + Main.admLogged.getFullName() + '!');

        labelTgl.setText(dtf.format(ayna));

        page.setPageCount(7);
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            int pos = (page.getCurrentPageIndex() + 1) % page.getPageCount();
            page.setCurrentPageIndex(pos);
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

        String[] photos = {"/image/1.jpg", "/image/2.jpg", "/image/3.jpg", "/image/rumput.jpg", "/image/semen.jpg", "/image/vinyl.jpg", "/image/lgo.png"};
        page.setPageFactory((Integer pageIndex) -> {
            ImageView n = new ImageView(Objects.requireNonNull(getClass().getResource(photos[pageIndex]))
                    .toExternalForm());
            n.setFitHeight(200);
            n.setFitWidth(250);
            return n;
        });
    }

    private void timeNow(){
        thread = new Thread(() -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            while(!stop){
                try{
                    Thread.sleep(1000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                final String timenow = LocalTime.now().format(dtf);
                Platform.runLater(() -> {
                    labelJam.setText(timenow + " WIB");
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
