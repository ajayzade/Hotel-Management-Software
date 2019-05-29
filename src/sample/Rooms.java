package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.ResourceBundle;

public class Rooms implements Initializable {

    @FXML
    TextField room_name,room_email,room_mobile,days;

    @FXML
    ComboBox<String> room_type;

    @FXML
    DatePicker room_check_in,room_check_out;

    @FXML
    ComboBox<Integer> room_guest,rooms;

    @FXML
    RadioButton cash,online;

    ObservableList<String> list1 = FXCollections.observableArrayList("AC","Non AC");
    ObservableList<Integer> list2 = FXCollections.observableArrayList();

    ConnectorClass connectorClass = new ConnectorClass();
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    public void onSave(ActionEvent actionEvent) {
        float price = (float) 0.0,pay = (float) 0.0;
        try {
            String payment_mode = "";
            if (cash.isSelected()){
                payment_mode = "cash";
            }else if (online.isSelected()){
                payment_mode = "online";
            }
            connection = connectorClass.getConnection();
            preparedStatement = connection.prepareStatement("insert into room (name, email, mobile, type, guests, rooms, check_in, check_out, payment) values (?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,room_name.getText());
            preparedStatement.setString(2,room_email.getText());
            preparedStatement.setString(3,room_mobile.getText());
            preparedStatement.setString(4,room_type.getValue());
            preparedStatement.setInt(5,Integer.parseInt(String.valueOf(room_guest.getValue())));
            preparedStatement.setInt(6,Integer.parseInt(String.valueOf(rooms.getValue())));
            preparedStatement.setString(7,room_check_in.getValue().toString());
            preparedStatement.setString(8,room_check_out.getValue().toString());
            preparedStatement.setString(9,payment_mode);
            preparedStatement.execute();
            int d = Integer.parseInt(days.getText());
            if (room_type.getValue() == "AC"){
                price = d * 800;
            }else if (room_type.getValue() == "Non AC"){
                price = d * 650;
            }
            if (payment_mode == "cash"){
                pay = price;
            }else if (payment_mode == "online"){
                pay = price - ((20 * price) / 100);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Info");
            alert.setContentText("Data Saved Successfully with payment of " + pay);
            alert.show();
            clear();

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host","192.168.43.176");
            Session session = Session.getDefaultInstance(properties);
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("zadeajay2018@gmail.com"));
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(String.valueOf(room_email.getText())));
                message.setSubject("Sky Blue Hotel");
                message.setText(room_name.getText() + " your reservation for room has been confirmed with " +
                        room_guest.getValue() + " in " + rooms.getValue() + " from check in : " +
                        room_check_in.getValue().toString() + " to check out : " + room_check_out.getValue().toString() + ".");
                Transport.send(message);
                System.out.println("Email Send Successfully");
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    public void clear(){
        room_name.setText("");
        room_email.setText("");
        room_mobile.setText("");
        room_guest.setValue(1);
        rooms.setValue(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        room_type.setValue("Room Type");
        room_type.setItems(list1);

        for (int i = 1;i <= 20;i++){
            list2.add(i);
        }
        room_guest.setValue(1);
        rooms.setValue(1);
        room_guest.setItems(list2);
        rooms.setItems(list2);

        cash.setSelected(true);
    }

    public void onReset(ActionEvent actionEvent) {
        clear();
    }

    public void onCash(ActionEvent actionEvent) {
        online.setSelected(false);
    }

    public void onPayment(ActionEvent actionEvent) {
        cash.setSelected(false);
    }
}
