package BookOfCook;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BookOfCookController {

    //*FIELDS
    private Cookbook cookbook;
    private ArrayList<Recipe> recipes;

    //*FXML-noder
    @FXML
    private Label number;

    @FXML
    private GridPane recipeGrid;

    @FXML
    private TextField recipeNameBar, servesPeopleBar;

    //*INITIALIZATION
    public void initialize(){
        initializeCookBook();
        initializeRecipeGrid();
    }

    private void initializeRecipeGrid() {
        initializeRecipes();

        for (Recipe recipe : recipes) {
            Recipe r = recipe; //copy recipe
            recipeGrid.add(createRecipeBody(r), recipes.indexOf(recipe) % 3,  recipes.indexOf(recipe) / 3);
        }
        
        for (Recipe recipe : cookbook.getRecipes()) {
            Recipe r = recipe; //copy recipe
            recipeGrid.add(createRecipeBody(r), recipes.indexOf(recipe) % 3,  recipes.indexOf(recipe) / 3);
        }
    }

    private void initializeCookBook(){
        cookbook = new Cookbook("Dawg");
    }

    private void initializeRecipes(){
        recipes = new ArrayList<Recipe>();
        recipes.addAll(cookbook.getRecipes());
    }
    
    //*UPDATERS
    public void updateRecipeGrid(){
        recipeGrid.getChildren().clear();
        initializeRecipeGrid();
    }


    //*DYNAMIC CREATION OF RECIPE COMPONENTS IN GRID
    private Pane createRecipeBody(Recipe recipe){
        //creates pane for each recipe
        Pane body = new Pane();

        //adds children
        body.getChildren().add(createViewButton(recipe));
        body.getChildren().add(createRecipeHeader(recipe));

        //styling
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
        viewbtn.setLayoutX(80);
        viewbtn.setLayoutY(170);

        //sets action
        viewbtn.setOnAction(e -> {
            System.out.println("View " + recipe.getName() + " recipe");
            //?viewRecipe(recipe);
        });

        return viewbtn;
    }
    
    private Label createRecipeHeader(Recipe recipe){
        //creates a label with recipe name
        Label label = new Label(recipe.getName());

        //sets style
        label.getStyleClass().clear();
        label.getStyleClass().add("recipe-header");
        label.setLayoutX(80);
        label.setLayoutY(10);

        return label;
    }


    //*EVENT HANDLERS
    public void addRecipe(ActionEvent event) {
        System.out.println("Add button was clicked");
        cookbook.addRecipeToCookbook(new Recipe(recipeNameBar.getText(), Integer.parseInt(servesPeopleBar.getText())));
        updateRecipeGrid();
    }
}