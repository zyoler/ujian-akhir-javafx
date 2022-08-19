package org.neophyte.controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.neophyte.Main;

import java.io.IOException;

public class HomeController {
    public Button logoutButton;
    public Button penjualanButton;
    public Button pembelianButton;
    public Button riwayatButton;
    public Button tambahButton;
    public Button tentangButton;
    public Button dataButton;


    public void initialize(){

        penjualanButton.setOnAction(event ->{
            pilihan(1);
        });
        pembelianButton.setOnAction(event ->{
            pilihan(2);
        });
        riwayatButton.setOnAction(event ->{
            pilihan(3);
        });
        dataButton.setOnAction(event ->{
            pilihan(4);
        });
        tambahButton.setOnAction(event ->{
            pilihan(5);
        });
        tentangButton.setOnAction(event ->{
            pilihan(6);
        });
        logoutButton.setOnAction(event ->{
            pilihan(7);
        });
    }
    public void pilihan(int a)  {
        ((Stage) penjualanButton.getScene().getWindow()).close();
        if(a==1){
            System.out.println("Tombol 1 berhasil:v");
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/pages/penjualan.fxml"));
                Scene tambahScene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(tambahScene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(a==2){
            System.out.println("Tombol 2 berhasil:v");
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/pages/pembelian.fxml"));
                Scene tambahScene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(tambahScene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(a==3){
            System.out.println("Tombol 3 berhasil:v");
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/pages/riwayat.fxml"));
                Scene tambahScene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(tambahScene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(a==4){
            System.out.println("Tombol  4 berhasil:v");
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/pages/data.fxml"));
                Scene tambahScene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(tambahScene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(a==5){
            System.out.println("Tombol 5 berhasil:v");
            if (Main.loggedInUser.isAdmin()) {
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("/pages/admin.fxml"));
                    Scene tambahScene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(tambahScene);
                    stage.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Hanya admin yang dapat mengakses menu ini");
                alert.show();
            }
        }else if(a==6){
            System.out.println("Tombol 6 berhasil:v");
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/pages/tentang.fxml"));
                Scene tambahScene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(tambahScene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(a==7){
            System.out.println("Tombol 7 berhasil:v");
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
                Scene tambahScene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(tambahScene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
