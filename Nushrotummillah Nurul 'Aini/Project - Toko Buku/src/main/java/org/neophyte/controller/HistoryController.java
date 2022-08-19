package org.neophyte.controller;

import javafx.scene.control.TableView;
import org.neophyte.model.History;
import org.neophyte.util.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HistoryController {
    public TableView<History> historyTable;
    public static TableView<History> staticHistoryTable;

    public void loadDataHistory(){
        staticHistoryTable.getItems().setAll();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM history");
            while (resultSet.next()) {
                staticHistoryTable.getItems().add(new History(resultSet.getString("id"), resultSet.getString("judul"), resultSet.getInt("harga"), resultSet.getInt("jumlah"), resultSet.getInt("total")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        staticHistoryTable = historyTable;
        loadDataHistory();
    }

    public void backAction() {
        MainController.refresh();
    }
}
