package org.neophyte.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.neophyte.Main;
import org.neophyte.model.User;
import org.neophyte.utils.DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class forgotController {
    public TextField userTextField;
    public TextField PasswordBaru;
    public TextField konfirmasiPasswordBaru;

    public Button nextBtn;
    public Button cancelBtn;

    User user;

    Connection conn = DataBase.getConnection();
    public void initialize(){
        userTextField.setText(MasukForgotController.tampUsername);
        nextBtn.setOnAction(event -> {
            PreparedStatement preparedStatement;
            if (Objects.equals(PasswordBaru.getText(),konfirmasiPasswordBaru.getText())){
                try {
                    preparedStatement = conn.prepareStatement("UPDATE USER SET password = ? WHERE USERNAME = ?");
                    preparedStatement.setString(2,userTextField.getText());
                    preparedStatement.setString(1,PasswordBaru.getText());
                    preparedStatement.executeUpdate();
                    close();
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Password Baru tidak sama");
                alert.show();
            }
        });
    }
    public void close() throws IOException {

        ((Stage) cancelBtn.getScene().getWindow()).close();
        Main.refresh();
    }
}
