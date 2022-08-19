package org.neophyte.validasi;

public class ValidasiUmum {

    public boolean ceklength(String no) {
        if (no.length() <= 12) {
            if (no.length() >= 12) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    public static boolean nama(String name) {

        if (name.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
            return true;
        }
        return false;
    }
    public static boolean nama2(String name) {

        if (name.matches("[a-zA-z]+([ .'-][a-zA-Z]+)*")) {
            return true;
        }
        return false;
    }
    public static boolean alamat(String alamat){
        if(alamat.matches("[a-zA-z0-9]+([ /.'-][a-zA-Z0-9]+)*")){
            return true;
        }
        return false;
    }
    public static boolean jenisKelamin(String jk){
        if (jk.matches("[a-zA-z]+([ -][a-zA-Z]+)*")){
            return true;
        }
        return false;
    }

    public static boolean number(String num){
        if (num.matches(".*\\d.*")){
            if (num.length()>=0){
                if (num.length()>=12){
                    return true ;
                }
            }
        }
        return false;
    }

    public static boolean cekharga(String harga){
        if (harga.matches(".*\\d.*")){
            if (Integer.parseInt(harga)>=15000){
                return true;
            }
        }
        return false;
    }

    public static boolean jumlahtiket(String tk){
        if (tk.matches(".*\\d.*")){
            if (!((Integer.parseInt(tk)) == 0)){
                return true;
            }
        }
        return false;
    }

    public static boolean username(String user) {
        if (user.matches("^[a-zA-Z0-9]\\w{5,29}$")){
            return true;
        }
        return false;
    }
    public static boolean password(String pass){
        if (pass.matches("^[A-Za-z]\\w{5,29}$")){
            if (pass.length()>6 && pass.length()==8){
                return true;
            }
        }
        return false;
    }
    public static boolean cekKode(String cek){
        if (cek.matches("[A-Z0-9]+([A-Z0-9]+)*")){
            return true;
        }
        return false;
    }
    public static boolean tittle(String tittle){
        if (tittle.matches("[a-zA-Z0-9]+([,.':][a-zA-Z0-9]+)*")){
            return true;
        }
        return false;
    }
    public static boolean genre(String cek){
        if (cek.matches("[a-zA-Z]+([a-zA-Z]+)*")){
            return true;
        }
        return false;
    }
}