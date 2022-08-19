package org.neophyte.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.neophyte.utils.DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BerandaController {
    public static FlowPane staticMovie;

    public FlowPane movie;
    public TextField textFieldSearch;

    public void movie() {
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM JADWAL_TAYANG");
            while (resultSet.next()) {
                VBox movies = new VBox();
                movies.getStyleClass().add("movies");

                movies.setSpacing(4);
                movies.setPadding(new Insets(12));
                movies.setAlignment(Pos.BASELINE_CENTER);
                ImageView post = new ImageView(new Image(resultSet.getString("poster")));
                post.setFitWidth(128);
                post.setFitHeight(196);
                String title = resultSet.getString("tittle");
                Label lab = new Label(title);
                String genre = resultSet.getString("genre");
                Label gen = new Label(genre);

                movies.getChildren().setAll(post, lab, gen);

                movie.getChildren().add(movies);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchMovie() {
        movie.getChildren().setAll();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM JADWAL_TAYANG");
            while (resultSet.next()) {
                if (resultSet.getString("tittle").toLowerCase().startsWith(textFieldSearch.getText().toLowerCase()) || (' ' + resultSet.getString("genre").toLowerCase()).contains(' ' + textFieldSearch.getText().toLowerCase())) {
                    VBox movies = new VBox();
                    movies.getStyleClass().add("movies");
                    movies.setSpacing(12);
                    movies.setPadding(new Insets(12));
                    movies.setAlignment(Pos.BASELINE_CENTER);
                    ImageView post = new ImageView(new Image(resultSet.getString("poster")));
                    post.setFitWidth(128);
                    post.setFitHeight(196);
                    String title = resultSet.getString("tittle");
                    Label lab = new Label(title);
                    String genre = resultSet.getString("genre");
                    Label gen = new Label(genre);

                    movies.getChildren().setAll(post, lab, gen);

                    movie.getChildren().add(movies);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        movie();
        textFieldSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchMovie();
            }
        });
    }
}
