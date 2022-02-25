package BookOfCook;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BookOfCookController {

    @FXML
    private TextField firstNumber, secondNumber, operator;

    @FXML
    private Label result;

    private Cookbook cookbook;

    private void initCookbook(String operator) {
        cookbook = new Cookbook(operator);
    }

    @FXML
    private void handleButtonClick() {
        initCookbook(operator.getText());

    }
}
