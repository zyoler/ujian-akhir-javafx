package indomarco.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Pemberitahuan {

    public static void berhasilDaftar(){
        String judul = "Akun berhasil terdaftar";
        String pesan = "Terimakasih, akun anda berhasil dibuat. Silahkan masuk:)";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(judul);
        alert.setHeaderText(pesan);
        alert.show();
    }
    public static void berhasilUpdate(){
        String judul = "Informasi pembaharuan";
        String pesan = "Data berhasil diperbaharui!";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(judul);
        alert.setHeaderText(pesan);
        alert.show();
    }
    public static void berhasilDestroy(){
        String judul = "Informasi penghapusan";
        String pesan = "Data berhasil dihapuskan!";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(judul);
        alert.setHeaderText(pesan);
        alert.show();
    }
    public static void gagalLogin(String pesan){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Gagal masuk");
        alert.setHeaderText(pesan);
        alert.show();
    }
    public static void berhasilLogin(String namaLengkap){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Berhasil masuk");
        alert.setHeaderText(null);
        alert.setContentText("Hi!, anda login sebagai " + namaLengkap);
        alert.show();
    }
    public static void gagalDaftar(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Gagal daftar");
        alert.setHeaderText("Inputan anda salah atau bernilai kosong dan mungkin nama pengguna telah terdaftar!");
        alert.show();
    }
    public static void berhasilDaftar(String namaLengkap){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Berhasil masuk");
        alert.setHeaderText(null);
        alert.setContentText("Hi!, anda login sebagai " + namaLengkap);
        alert.show();
    }
    public static boolean keluar(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Permintaan keluar");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin ingin keluar?");
        ButtonType confirm = alert.showAndWait().get();
        return confirm.getText().equals("OK");
    }
    public static boolean hapusData(String nama){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hapus data");
        alert.setHeaderText(null);
        alert.setContentText("Apakah anda yakin ingin menghapus " + nama + " ?");
        ButtonType confirm = alert.showAndWait().get();
        return confirm.getText().equals("OK");
    }

}
