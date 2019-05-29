package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Gallery implements Initializable {

    @FXML
    ImageView image;

    @FXML
    Button rightButton,leftButton;

    String[] paths = new String[] {
            "C://Users/Lenovo/IntelliJIDEAProjects/Hotel Software/src/images/one.jpg",
            "C://Users/Lenovo/IntelliJIDEAProjects/Hotel Software/src/images/two.jpg",
            "C://Users/Lenovo/IntelliJIDEAProjects/Hotel Software/src/images/three.jpg",
            "C://Users/Lenovo/IntelliJIDEAProjects/Hotel Software/src/images/four.jpg",
            "C://Users/Lenovo/IntelliJIDEAProjects/Hotel Software/src/images/five.jpg"
    };

    int x = 0;

    @FXML
    Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Image i = new Image(new FileInputStream(new File(paths[0])));
            image.setImage(i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onRightButton(ActionEvent actionEvent) throws FileNotFoundException {
        x++;
        setImage(x);
    }

    private void setImage(int x) throws FileNotFoundException {
        if (x < 0){
            x = paths.length;
        }
        if (x > paths.length - 1){
            x = 0;
        }
        label.setText((x + 1) + "/5");
        Image i = new Image(new FileInputStream(new File(paths[x])));
        image.setImage(i);
    }

    public void onLeftButton(ActionEvent actionEvent) throws FileNotFoundException {
        x--;
        setImage(x);
    }
}
