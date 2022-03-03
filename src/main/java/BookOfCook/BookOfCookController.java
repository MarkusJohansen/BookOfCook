package BookOfCook;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookOfCookController {
    @FXML
    private Label number;

    public void generateRandom(ActionEvent event) {
        Random rand = new Random();
        int myRandom = rand.nextInt(100) + 1;
        number.setText(Integer.toString(myRandom));
    }

}