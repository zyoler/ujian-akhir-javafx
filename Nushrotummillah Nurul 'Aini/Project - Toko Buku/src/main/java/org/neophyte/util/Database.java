package org.neophyte.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private static Connection connection;

    public Database(){
        try{
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./default");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
