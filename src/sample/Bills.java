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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.print.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class Bills implements Initializable {

    @FXML
    TextField search,name,rupees,item;

    @FXML
    TableView<Rate> table;

    @FXML
    TableColumn<Rate,String> menu,price;

    @FXML
    ComboBox quantity;

    @FXML
    TextArea area;

    @FXML
    CheckBox cash,online;

    ConnectorClass connectorClass = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ObservableList<Rate> list = FXCollections.observableArrayList();
    String food;
    int p,total = 0,count = 0;
    float gst = 0;

    ObservableList<Integer> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (int k = 0;k < 20;k++){
            observableList.add(k + 1);
        }

        quantity.setItems(observableList);
        quantity.setValue(observableList.get(0));

        try{
            connectorClass = new ConnectorClass();
            connection = connectorClass.getConnection();
            preparedStatement = connection.prepareStatement("select * from menu");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(new Rate(resultSet.getString("items"),resultSet.getInt("price")));
            }
            menu.setCellValueFactory(new PropertyValueFactory<>("menu"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            table.setItems(list);


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        ban();
    }

    ArrayList<String> arrayList1 = new ArrayList<>();
    ArrayList<Integer> arrayList2 = new ArrayList<>();
    ArrayList<Integer> arrayList3 = new ArrayList<>();

    public void onAdd(ActionEvent actionEvent) {
        if (food != "" && name.getText() != ""){
            count++;
            rupees.setText("Items : " + count);
            arrayList1.add(food);
            int x = Integer.parseInt(quantity.getValue().toString());
            arrayList3.add(x);
            try {
                preparedStatement = connection.prepareStatement("select * from menu where items=?");
                preparedStatement.setString(1,food);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    p = resultSet.getInt("price");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int np = x * p;
            arrayList2.add(np);
            total = total + np;
            gst = (float) (total * 0.18);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Your fields should not be empty");
            alert.show();
        }
    }

    public void onReset(ActionEvent actionEvent) {
        reset();
    }

    public void onMakeBill(ActionEvent actionEvent) {
        area.setText(area.getText() + "\n\t Name : " + name.getText().toUpperCase() + "\n");
        area.setText(area.getText() + "\n***************************************************\n");
        area.setText(area.getText() + "  Items\t\t\t" + "Price");
        area.setText(area.getText() + "\n***************************************************\n");
        for (int i = 0;i < arrayList1.size();i++) {
            area.setText(area.getText() + arrayList1.get(i) + " (" + arrayList3.get(i) +")" + " \t : \t " + arrayList2.get(i) + ".00" + "\n");
        }
        area.setText(area.getText() + "\n***************************************************\n");
        area.setText(area.getText() + "\tTotal : " + total + ".00" + "\n\n");
        area.setText(area.getText() + "\tGST : " + gst + "\n");
        if (cash.isSelected()){
            area.setText(area.getText() + "\tAmount to be Paid : " + (gst + total) + "\n\n");
        }else if (online.isSelected()){
            float discount = (float) ((gst + total) * 0.25);
            float pay = (gst + total) - discount;
            area.setText(area.getText() + "\tAmount to be Paid : " + pay + "\n\n");
        }
        area.setText(area.getText() + "\n***************************************************\n");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        area.setText(area.getText() + "\n" + dateFormat.format(date) + "\n");
        area.setText(area.getText() + "\n***************************************************\n");
        reset();
    }

    public Boolean onPrintBill(ActionEvent actionEvent) {
        PrinterJob job = PrinterJob.getPrinterJob();
        String b = area.getText();
        Printable printable = new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                Graphics2D graphics2D = (Graphics2D) graphics;
                graphics2D.translate(pageFormat.getImageableX(),pageFormat.getImageableY());
                graphics2D.setFont(new Font("Monospaced",Font.BOLD,10));
                String[] bill = b.split("\n");
                int y = 15;
                for (int i = 0;i < bill.length;i++){
                    graphics.drawString(bill[i],5,y);
                    y = y + 15;
                }
                if (pageIndex > 0){
                    return NO_SUCH_PAGE;
                }
                return PAGE_EXISTS;
            }
        };
        PageFormat pageFormat = new PageFormat();
        pageFormat.setOrientation(PageFormat.PORTRAIT);
        Paper paper = pageFormat.getPaper();
        paper.setImageableArea(0,0,paper.getWidth(),paper.getHeight() - 2);
        job.setPrintable(printable,pageFormat);
        try {
            job.print();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public void ban(){
        area.setText("\n***************************************************\n");
        area.setText(area.getText() + "\t\tSKY BLUE RESTAURENT\t");
        area.setText(area.getText() + "\n***************************************************\n");
    }

    public void reset(){
        name.setText("");
        item.setText("");
        p = 0;
        total = 0;
        count = 0;
        rupees.setText("");
        gst = 0;
        arrayList3.clear();
        arrayList2.clear();
        arrayList1.clear();
    }

    public void casi(ActionEvent actionEvent) {
        online.setSelected(false);
    }

    public void onli(ActionEvent actionEvent) {
        cash.setSelected(false);
    }

    public void clear(ActionEvent actionEvent) {
        area.setText("");
        ban();
        reset();
    }

    public void onSearchMenu(KeyEvent keyEvent) {
        FilteredList<Rate> filteredList = new FilteredList<>(list,p -> true);
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filteredList.setPredicate(s -> {
                    if (newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String filter = newValue.toLowerCase();
                    if (s.getMenu().toLowerCase().contains(filter)){
                        return true;
                    }
                    return false;
                });
            }
        });
        SortedList<Rate> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    public void onMousePressed(MouseEvent mouseEvent) {
        Rate r = table.getSelectionModel().getSelectedItem();
        item.setText(r.getMenu());
        food = r.getMenu();
    }
}