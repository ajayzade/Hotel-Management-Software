package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    TextField user;

    @FXML
    PasswordField pass;

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) pass.getScene().getWindow();
        stage.close();
    }

    public void onLogin(ActionEvent actionEvent) {
        String s1 = user.getText();
        String s2 = pass.getText();
        int c = 0;
        if (s1.isEmpty() || s2.isEmpty()){
            c = 1;
        }
        if (c == 1){
            callWarningAlert();
        }else {
            String str1 = null,str2 = null;
            try {
                ConnectorClass connectorClass = new ConnectorClass();
                Connection connection = connectorClass.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from admin");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    str1 = resultSet.getString("username");
                    str2 = resultSet.getString("password");
                }
                if (str1.equals(s1) && str2.equals(s2)){
                    Stage stage = (Stage) pass.getScene().getWindow();
                    stage.close();
                    open("adminpanel.fxml","Admin Panel");
                }
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.show();
                e.printStackTrace();
            }
        }
    }

    public void open(String s,String str) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(s));
        stage.setTitle(str);
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void callWarningAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Fields should not be empty");
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
