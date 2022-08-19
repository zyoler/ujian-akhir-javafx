package indomarco.controllers.layouts;

import javafx.scene.input.MouseEvent;

public class FooterController {
    public void klikFacebook(MouseEvent e){
        for (int i = 1; i < 20000; i *= 2){
            System.out.println("ID" + String.format("%1$5s", i).replace(' ', '0'));
        }

    }
}
