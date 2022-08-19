package indomarco.util;

import java.sql.*;

public class Database {
    public Connection connection;

    public Database() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./indomarco");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
