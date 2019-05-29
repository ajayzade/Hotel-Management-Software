package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Stats implements Initializable {

    @FXML
    Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label.setText(
                "Taj Hotels is a chain of luxury hotels and a subsidiary of the Indian Hotels Company Limited\n" +
                " headquartered at Express Towers, Nariman Point in Mumbai.Incorporated by the founder of the Tata\n " +
                "Group, Jamsetji Tata, in 1903, the company is a part of the Tata Group, one of India's \n" +
                "largest business conglomerates. The company employed over 13,000 people in the year 2010.\n" +
                "As of 2018, the company operates a total of 100 hotels and hotel-resorts, with 84 across India \n" +
                "and 16 in other countries, including Bhutan, Malaysia, Maldives, Nepal, South Africa, Sri Lanka, \n" +
                "UAE, UK, USA and Zambia.\n"
        );
    }
}
