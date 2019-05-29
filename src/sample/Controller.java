package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Button gallery,founders,location,hotelmap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onGallery(ActionEvent actionEvent) throws IOException {
        open("gallery.fxml","Gallery");
    }

    public void onFounders(ActionEvent actionEvent) {
        box();
    }

    public void onLocation(ActionEvent actionEvent) {
        box();
    }

    public void onHotelMap(ActionEvent actionEvent) {
        box();
    }

    public void adminLogin(ActionEvent actionEvent) throws IOException {
        open("login.fxml","Admin Login");
    }

    public void manageBill(ActionEvent actionEvent) throws IOException {
        open("bills.fxml","Manage Bills");
    }

    public void manageRooms(ActionEvent actionEvent) throws IOException {
        open("rooms.fxml","Manage Rooms");
    }

    public void onlineOrders(ActionEvent actionEvent) throws IOException {
        open("stats.fxml","");
    }

    private void box(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("This Window is not available");
        alert.show();
    }

    public void open(String s,String str) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(s));
        stage.setTitle(str);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
