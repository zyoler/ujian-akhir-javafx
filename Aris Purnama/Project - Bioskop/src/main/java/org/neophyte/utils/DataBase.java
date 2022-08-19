package org.neophyte.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {
    private static Connection connection;

    public DataBase() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./DataBioskop");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
