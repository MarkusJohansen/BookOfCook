package BookOfCook;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class BookOfCookController {
    private Cookbook cookbook;
    private ArrayList<Recipe> recipes;
    private ArrayList<String> ingredients; //!food in fridge was hashmap?


    //*FXML-noder
    @FXML
    private Label number;  

    @FXML
    private GridPane recipeGrid;

    @FXML
    private Line fridgeList; //!List? Listpane?

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
            recipeGrid.add(createRecipeComponent(r), recipes.indexOf(recipe) % 3,  recipes.indexOf(recipe) / 3);
        }
        
        for (Recipe recipe : cookbook.getRecipes()) {
            Recipe r = recipe; //copy recipe
            recipeGrid.add(createRecipeComponent(r), recipes.indexOf(recipe) % 3,  recipes.indexOf(recipe) / 3);
        }
    }

    private void initializeCookBook(){
        cookbook = new Cookbook("Dawg");
    }

    private void initializeRecipes(){
        recipes = new ArrayList<Recipe>();
        recipes.addAll(cookbook.getRecipes());
    }
    
    //!not done
    private void initializeCategories(){
        //TODO
    }
    
    //!not done
    private void initializeFridge(){
        //TODO
    }
    
    //!not done
    private void initializeFridgeFood(){
        for(String food : ingredients){
            createFoodComponent(food);         //! Må ta inn food på et vis, må sørge for at den får data relatert til en food 
        }
    }


    //*UPDATERS
    public void updateRecipeGrid(){
        recipeGrid.getChildren().clear();
        updateCategories();
        initializeRecipeGrid();
    }

    //! not done
    private void updateCategories(){
        fridgeList.getChildren().clear();
        initializeCategories();
    }

    //!not done
    public void updatefridge(){
        initializeFridge();
    }


    //*DYNAMIC CREATION OF RECIPE COMPONENTS IN GRID
    private Pane createRecipeComponent(Recipe recipe){
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

    //!not done
    //*DYNAMIC CREATION OF CATEGORY COMPONENTS IN LIST
    private Pane createCategoryComponent(){
        //TODO
    }

    private CheckBox createCategoryCheckbox(){
        //TODO
    }

    private Label createCategoryLabel(){
        //TODO
    }

    //!not done
    //*DYNAMIC CREATION OF FRIDGE COMPONENTS IN LIST
    private Pane createFoodComponent(String food){//!FATAL ERROR: "Cannot find symbol"
        Pane foodBody = new Pane();

        //adds children
        foodBody.getChildren().add(createFoodLabel("!foodname", "!amount", "!unit")); //!TO
        foodBody.getChildren().add(createDeleteX("!foodname"));

        return foodBody;
    }

    private Button createDeleteX(String foodname){
        Button removeFoodBtn = new Button("X");

        removeFoodBtn.getStyleClass().clear();
        removeFoodBtn.getStyleClass().add("standard-button");
        removeFoodBtn.setLayoutX(80);
        removeFoodBtn.setLayoutY(170);

        removeFoodBtn.setOnAction(e -> {
            System.out.println("Delete food " + foodname);
            //?deleteFood();
        });

        return removeFoodBtn;
    }

    private Label createFoodLabel(String foodname, String foodamount, String foodunit){
        Label label = new Label();
        label.setText(foodname + ": " + foodamount + " " + foodunit);

        label.getStyleClass().clear();
        label.getStyleClass().add("food-label");
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

    //!not done
    public void addFoodToFridge(ActionEvent event) {
        System.out.println("Add food to fridge");
    }
} 