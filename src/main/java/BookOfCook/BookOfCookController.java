package BookOfCook;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class BookOfCookController {

    @FXML
    private TextField firstNumber, secondNumber, operator;

    @FXML
    private Label result;

    private Cookbook cookbook;

    public void initialize() {
        cookbook = new Cookbook("MyCookbook");
    }

    private Button CreateDeleteButton(){
        Button button = new Button("Delete");
        button.setOnAction((event) -> {
            //cookbook.deleteRecipe();
            result.setText("Recipe deleted");
        });
        return button;
    }

    private Button CreateEditButton(){
        Button button = new Button("Edit");
        button.setOnAction((event) -> {
            //cookbook.editRecipe();
            result.setText("Recipe edited");
        });
        return button;
    }

    private Button CreateViewButton(){
        Button button = new Button("View");
        button.setOnAction((event) -> {
            //cookbook.viewRecipe();
            result.setText("Recipe viewed");
        });
        return button;
    }

    public Pane addRecipeToGUI (Recipe recipe) {
        Pane pane = new Pane();
        pane.getChildren().add(new Label(recipe.getName()));
        pane.getChildren().add(CreateDeleteButton());
        pane.getChildren().add(CreateEditButton());
        pane.getChildren().add(CreateViewButton());
        return pane;
    }
}
