package BookOfCook;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BookOfCookController {
    private ArrayList<Recipe> recipes;

    @FXML
    private Label number;

    @FXML
    private GridPane recipeGrid;

    public void initialize(){
        initializeDummyRecipes();
        initializeRecipeGrid();
    }

    public void generateRandom(ActionEvent event) {
    //     Random rand = new Random();
    //     int myRandom = rand.nextInt(100) + 1;
    //     number.setText(Integer.toString(myRandom));

    //     //midlertidig add pane to grid
    //     //addRecipeToGrid();
    }

    private void initializeRecipeGrid() {
        for (Recipe recipe : recipes) {
            Recipe r = recipe; //copy recipe
            recipeGrid.add(createRecipeBody(r), recipes.indexOf(recipe) % 3,  recipes.indexOf(recipe) / 3);
        }
    }

    private void initializeDummyRecipes(){
        recipes = new ArrayList<Recipe>();
        recipes.addAll(List.of(
            new Recipe("Pasta", 4),
            new Recipe("Pizza", 2),
            new Recipe("Sushi", 1),
            new Recipe("Hamburger", 2),
            new Recipe("Lasagne", 3),
            new Recipe("Pancakes", 1),
            new Recipe("Spaghetti", 3),
            new Recipe("Taco", 1)
        ));
    }

    private Pane createRecipeBody(Recipe recipe){
        Pane body = new Pane();

        body.getChildren().add(createViewButton(recipe));
        body.getChildren().add(new Label(recipe.getName()));

        body.getStyleClass().clear();
        body.getStyleClass().add("recipe-body");
        body.setMaxWidth(Double.MAX_VALUE);
        body.setMaxHeight(Double.MAX_VALUE);

        return body;
    }

    private Button createViewButton(Recipe recipe){
        //creates a button with "View" text
        Button viewbtn = new Button("View");

        //sets style
        viewbtn.getStyleClass().clear();
        viewbtn.getStyleClass().add("standard-button");
        viewbtn.getStyleClass().add("view-button");

        //sets action
        viewbtn.setOnAction(e -> {
            System.out.println("View " + recipe.getName() + " recipe");
            //?viewRecipe(recipe);
        });

        return viewbtn;
    }
}