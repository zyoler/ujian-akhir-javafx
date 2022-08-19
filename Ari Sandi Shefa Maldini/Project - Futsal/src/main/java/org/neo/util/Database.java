package org.neo.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private static Connection connection;

    public static Connection connect(){
        if(connection == null){
            try{
                Class.forName("org.h2.Driver");
                connection = (Connection) DriverManager.getConnection("jdbc:h2:./futsalDB");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void disconnect(){
        if(connection != null){
            try{
                connection.close();
                connection = null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
