package org.neo.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TentangController {
    public ImageView img;

    public void initialize(){
        img.setImage(new Image("/image/tentang.png"));
    }
}
