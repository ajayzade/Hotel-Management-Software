package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Adminpanel implements Initializable {

    @FXML
    TextField name,contact;

    @FXML
    DatePicker dob,jod;

    @FXML
    ComboBox<String> job;

    @FXML
    TextArea address;

    @FXML
    ImageView image;

    @FXML
    RadioButton radioMale,radioFemale;

    @FXML
    TextField searchName,searchContact;

    @FXML
    ImageView searchImage;

    @FXML
    TableView<Model> searchTable;

    @FXML
    TableColumn<Model,String> col11;

    @FXML
    TableColumn<Model,String> col12;

    @FXML
    TableColumn<Model,String> col13;

    @FXML
    TableColumn<Model,String> col14;

    @FXML
    TableColumn<Model,String> col15;

    @FXML
    TableColumn<Model,String> col16;

    @FXML
    Button deleteButton;

    @FXML
    TextField getName,update_name,update_contact,update_gender;

    @FXML
    DatePicker update_dob,update_jod;

    @FXML
    ComboBox<String> update_job;

    @FXML
    TextArea update_address;

    @FXML
    Button save;

    @FXML
    TableView<Model2> room_table;

    @FXML
    TableColumn<Model2,String> col21,col22,col23,col24,col25;

    private Connection connection = null;
    private ConnectorClass connectorClass = new ConnectorClass();
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    private File file = null;
    private String path = "C://Users/Lenovo/IntelliJIDEAProjects/Hotel Software/src/images/user.png";

    private ObservableList<String> list = FXCollections.observableArrayList();
    private ObservableList<Model> list1 = FXCollections.observableArrayList();
    private ObservableList<Model2> list2 = FXCollections.observableArrayList();

    private String s1,s2,s3,s4,s5,s6,s7 = "MALE";

    public void onReset(ActionEvent actionEvent) throws FileNotFoundException {
        clear();
    }

    public void onSave(ActionEvent actionEvent) {
        int c = 0;
        try {
            s1 = name.getText();
            s3 = contact.getText();
            s4 = dob.getValue().toString();
            s5 = jod.getValue().toString();
            s6 = job.getValue();
            s2 = address.getText();
            if (radioMale.isSelected()) {
                s7 = "Male";
            } else if (radioFemale.isSelected()) {
                s7 = "Female";
            }

            if (s1.isEmpty() || s2.isEmpty() || s6.isEmpty()){
                c = 1;
            }

            if (c == 0) {
                String query = "insert into staff (name, address, contact, dob, jod, job, gender, photo) values (?,?,?,?,?,?,?,?)";
                connection = connectorClass.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, s1);
                preparedStatement.setString(2, s2);
                preparedStatement.setString(3, s3);
                preparedStatement.setString(4, s4);
                preparedStatement.setString(5, s5);
                preparedStatement.setString(6, s6);
                preparedStatement.setString(7, s7);
                InputStream fin = new FileInputStream(file);
                preparedStatement.setBinaryStream(8, fin, file.length());
                preparedStatement.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informtion");
                alert.setContentText("Data has been saved successfully");
                alert.show();
                clear();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void onBrowse(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("image","*.jpg","*.png"));
            Stage stage = new Stage();
            file = fileChooser.showOpenDialog(stage);
            FileInputStream fileInputStream = new FileInputStream(file);
            Image img = new Image(fileInputStream);
            image.setImage(img);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.add("Waiter");
        list.add("Room Maintainer");
        list.add("Senior Chef");
        list.add("Cook");
        list.add("Sweeper");
        list.add("Gardener");
        list.add("Plumber");
        list.add("Electrician");
        list.add("Receptionist");
        list.add("CareTaker");

        job.setValue("Select Job");
        job.setItems(list);

        update_job.setValue("Select Job");
        update_job.setItems(list);

        fillTable();

        try {
            connection = connectorClass.getConnection();
            preparedStatement = connection.prepareStatement("select * from room");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list2.add(new Model2(resultSet.getString("name"),resultSet.getString("email"),resultSet.getString("mobile")
                ,resultSet.getString("check_in"),resultSet.getString("check_out")));
            }
            col21.setCellValueFactory(new PropertyValueFactory<>("name"));
            col22.setCellValueFactory(new PropertyValueFactory<>("email"));
            col23.setCellValueFactory(new PropertyValueFactory<>("mobile"));
            col24.setCellValueFactory(new PropertyValueFactory<>("check_in"));
            col25.setCellValueFactory(new PropertyValueFactory<>("check_out"));
            room_table.setItems(list2);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    public void fillTable(){
        try {
            connection = connectorClass.getConnection();
            preparedStatement = connection.prepareStatement("select * from staff");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list1.add(new Model(resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("contact"),
                        resultSet.getString("dob"), resultSet.getString("jod"), resultSet.getString("job")));
            }
            col11.setCellValueFactory(new PropertyValueFactory<>("name"));
            col12.setCellValueFactory(new PropertyValueFactory<>("address"));
            col13.setCellValueFactory(new PropertyValueFactory<>("contact"));
            col14.setCellValueFactory(new PropertyValueFactory<>("dob"));
            col15.setCellValueFactory(new PropertyValueFactory<>("jod"));
            col16.setCellValueFactory(new PropertyValueFactory<>("profession"));
            searchTable.setItems(list1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clear() throws FileNotFoundException {
        name.setText("");
        contact.setText("");
        address.setText("");
        name.setText("");
        File f = new File(path);
        FileInputStream stream = new FileInputStream(f);
        Image i = new Image(stream);
        image.setImage(i);
    }

    public void onMale(ActionEvent actionEvent) {
        radioFemale.setSelected(false);
    }

    public void onFemale(ActionEvent actionEvent) {
        radioMale.setSelected(false);
    }

    public void onSearchName(KeyEvent keyEvent) {
        FilteredList<Model> filteredList = new FilteredList<Model>(list1,p -> true);
        searchName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filteredList.setPredicate(model -> {
                    if (newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String filter = newValue.toLowerCase();
                    if (model.getName().toLowerCase().contains(filter)){
                        return true;
                    }
                    return false;
                });
            }
        });
        SortedList<Model> sortedList = new SortedList<Model>(filteredList);
        sortedList.comparatorProperty().bind(searchTable.comparatorProperty());
        searchTable.setItems(sortedList);
    }

    public void onSearchContact(KeyEvent keyEvent) {
        FilteredList<Model> filteredList = new FilteredList<Model>(list1,p -> true);
        searchContact.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filteredList.setPredicate(model -> {
                    if (newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String filter = newValue.toLowerCase();
                    if (model.getName().toLowerCase().contains(filter)){
                        return true;
                    }
                    return false;
                });
            }
        });
        SortedList<Model> sortedList = new SortedList<Model>(filteredList);
        sortedList.comparatorProperty().bind(searchTable.comparatorProperty());
        searchTable.setItems(sortedList);
    }

    public void onRowSelected(MouseEvent mouseEvent) throws SQLException {
        Model model = searchTable.getSelectionModel().getSelectedItem();
        String name = model.getName();
        preparedStatement = connection.prepareStatement("select * from staff where name=?");
        preparedStatement.setString(1,name);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            byte[] bytes = resultSet.getBytes("photo");
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            Image i = new Image(bis);
            searchImage.setImage(i);
        }
        deleteButton.setDisable(false);
    }

    public void onDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you confirm want to delete staff data ?");
        Optional<ButtonType> r = alert.showAndWait();
        if (r.get() == ButtonType.OK){
            Model model = searchTable.getSelectionModel().getSelectedItem();
            String name = model.getName();
            try {
                preparedStatement = connection.prepareStatement("delete from staff where name=?");
                preparedStatement.setString(1,name);
                preparedStatement.execute();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Information");
                alert1.setContentText("Data Deleted Successfully");
                alert1.show();
                searchTable.setItems(null);
                fillTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            alert.close();
        }
    }

    public void onSearchStaff(ActionEvent actionEvent) {
        try {
            String n = getName.getText();
            update_name.setEditable(true);
            update_contact.setEditable(true);
            update_dob.setEditable(true);
            update_jod.setEditable(true);
            update_job.setEditable(true);
            update_gender.setEditable(true);
            update_address.setEditable(true);
            save.setDisable(false);
            preparedStatement = connection.prepareStatement("select * from staff where name=?");
            preparedStatement.setString(1,n);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                update_name.setText(resultSet.getString("name"));
                update_contact.setText(resultSet.getString("contact"));
                update_dob.setValue(LocalDate.parse(resultSet.getString("dob")));
                update_jod.setValue(LocalDate.parse(resultSet.getString("jod")));
                update_job.setValue(resultSet.getString("job"));
                update_gender.setText(resultSet.getString("gender"));
                update_address.setText(resultSet.getString("address"));
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    public void onSaveUpdate(ActionEvent actionEvent) {
        try {
            String n = getName.getText();
            preparedStatement = connection.prepareStatement("update staff set name=?,contact=?,dob=?,jod=?,job=?,gender=?,address=? where name=?");
            preparedStatement.setString(1,update_name.getText());
            preparedStatement.setString(2,update_contact.getText());
            preparedStatement.setString(3,update_dob.getValue().toString());
            preparedStatement.setString(4,update_jod.getValue().toString());
            preparedStatement.setString(5, String.valueOf(update_job.getValue()));
            preparedStatement.setString(6,update_gender.getText());
            preparedStatement.setString(7,update_address.getText());
            preparedStatement.setString(8,n);
            preparedStatement.execute();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Information");
            alert1.setContentText("Staff's Data Updated Successfully");
            alert1.show();
            update_name.setEditable(false);
            update_contact.setEditable(false);
            update_dob.setEditable(false);
            update_jod.setEditable(false);
            update_job.setEditable(false);
            update_gender.setEditable(false);
            update_address.setEditable(false);
            update_name.setText("");
            update_contact.setText("");
            update_gender.setText("");
            update_address.setText("");
            update_job.setValue("Select Job");
            update_dob.setValue(null);
            update_jod.setValue(null);
            save.setDisable(true);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
