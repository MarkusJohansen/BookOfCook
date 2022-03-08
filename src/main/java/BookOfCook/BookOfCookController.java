package BookOfCook;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookOfCookController {
    private Cookbook cookbook;
    private ArrayList<Recipe> recipes;
    private ArrayList<HashMap<String, Object>> fridgeFood;
    private ArrayList<Category> categories;
    private Fridge fridge;


    //*FXML-noder
    @FXML
    private Label number;  

    @FXML
    private GridPane recipeGrid, recipeViewContent, recipeViewBox1, recipeViewBox2;

    @FXML
    private Pane recipeView;

    @FXML
    private VBox fridgeList, categoryList; //!List? Listpane?

    @FXML
    private TextField recipeNameBar, servesPeopleBar, prepTimeBar, categoryBar, ingredientBar;

    @FXML
    private TextArea descriptionBar;


    //*INITIALIZATION
    public void initialize(){
        initializeCookBook();
        initializeRecipeGrid();
        initializeFridge();
        initializeCategories();
        initializeFridgeFood();
    }

    private void initializeRecipeGrid() {
        initializeRecipes();
        for (Recipe recipe : recipes) {
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
    
    private void initializeCategories(){
        categories = new ArrayList<Category>();
        categories.addAll(cookbook.getCategories());

        for(Category category : categories){
            categoryList.getChildren().add(createCategoryComponent(category));
        }
    }
    
    private void initializeFridge(){
        fridge = new Fridge();
    }
    
    //!not done
    private void initializeFridgeFood(){
        fridgeFood = new ArrayList<HashMap<String, Object>>();
        fridgeFood.addAll(fridge.getFoodInFridge()); //!hvordan får jeg ut food fra fridge?

        for(HashMap<String, Object> food : fridgeFood){
            fridgeList.getChildren().add(createFoodComponent(food));
        }
    }


    //*UPDATERS
    public void updateRecipeGrid(){
        recipeGrid.getChildren().clear();
        updateCategories();
        initializeRecipeGrid();
    }

    private void updateCategories(){
        categoryList.getChildren().clear();
        initializeCategories();
    }

    public void updatefridge(){
        fridgeList.getChildren().clear();
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
            viewRecipe(recipe);
        });

        return viewbtn;
    }
    
    private Label createRecipeHeader(Recipe recipe){
        //creates a label with recipe name
        Label label = new Label(recipe.getDisplayedName());

        //sets style
        label.getStyleClass().clear();
        label.getStyleClass().add("recipe-header");
        label.setLayoutX(80);
        label.setLayoutY(10);

        return label;
    }

    //!not done
    //*DYNAMIC CREATION OF CATEGORY COMPONENTS IN LIST
    private Pane createCategoryComponent(Category category){
        //creates pane for each category
        Pane body = new Pane();

        //adds children
        body.getChildren().add(createCategoryLabel(category));
        body.getChildren().add(createCategoryCheckbox(category));

        //styling
        body.getStyleClass().clear();
        body.getStyleClass().add("category-body");
        body.setMaxWidth(Double.MAX_VALUE);
        body.setMaxHeight(Double.MAX_VALUE);

        return body;
    }

    private CheckBox createCategoryCheckbox(Category category){
        //creates a checkbox with category name
        CheckBox checkbox = new CheckBox(category.getName());

        //sets style
        checkbox.getStyleClass().clear();
        checkbox.getStyleClass().add("category-checkbox");
        checkbox.setLayoutX(10);
        checkbox.setLayoutY(10);

        //on checkbox click
        checkbox.setOnAction(e -> {
            System.out.println("Checkbox clicked");
            //?start filter();
        });

        return checkbox;
    }

    private Label createCategoryLabel(Category category){
        //creates a label with category name
        Label label = new Label(category.getName());

        //sets style
        label.getStyleClass().clear();
        label.getStyleClass().add("category-header");
        label.setLayoutX(10);
        label.setLayoutY(10);

        return label;
    }

    //!not done
    //*DYNAMIC CREATION OF FRIDGE COMPONENTS IN LIST
    private Pane createFoodComponent(HashMap<String, Object> food){//!FATAL ERROR: "Cannot find symbol"
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

    //view recipe
    public void viewRecipe(ActionEvent event) {
        System.out.println("View button was clicked");
    }

    //remove food from fridge
    public void removeFood(ActionEvent event) {
        System.out.println("Remove food button was clicked");
    }

    // *RECIPEVIEW
    public void viewRecipe(Recipe recipe){
        //hide grid and show recipeview
        recipeGrid.setVisible(false);
        recipeView.setVisible(true);

        //set recipe style
        recipeView.getStyleClass().clear();
        recipeView.getStyleClass().add("recipe-view");
        recipeView.setMaxWidth(Double.MAX_VALUE);
        recipeView.setMaxHeight(Double.MAX_VALUE);

        //add children
        createRecipeViewContent(recipe);
    }

    //fills recipeContent-grid in recipeView, with content from recipe
    public void createRecipeViewContent(Recipe recipe){
        createRecipeViewTitle(recipe);
        fillWithDataContent(recipe);
        addCloseButton(recipe);
    }

    //creates a button with "Close" text that rund closeRecipeView() when clicked
    private void addCloseButton(Recipe recipe){
        //creates button object
        Button closeButton = new Button("Close");

        //sets style
        closeButton.getStyleClass().clear();
        closeButton.getStyleClass().add("standard-button");
        closeButton.setLayoutX(80);
        closeButton.setLayoutY(170);

        //sets action
        closeButton.setOnAction(e -> {
            closeRecipeView();
        });

        //adds to grid
        recipeViewContent.add(closeButton, 1, 4);
    }

    //create title label with recipe name
    private void createRecipeViewTitle(Recipe recipe){
        Label label = new Label(recipe.getName());
        label.getStyleClass().clear();
        label.getStyleClass().add("recipe-view-title");
        label.setLayoutX(80);
        label.setLayoutY(10);

        recipeViewBox1.add(label, 0, 0);
    }

    //create description label, steps ingredient, categories and serves label
    private void fillWithDataContent(Recipe recipe){
        //create description content
        Label description = new Label(recipe.getDescription());

        description.getStyleClass().clear();
        description.getStyleClass().add("recipe-view-description");
        description.setLayoutX(80);
        description.setLayoutY(10);

        recipeViewBox1.add(description, 0, 3);

        //create steps content
        Label steps = new Label(recipe.getSteps().toString());
        
        steps.getStyleClass().clear();
        steps.getStyleClass().add("recipe-view-description");
        steps.setLayoutX(80);
        steps.setLayoutY(10);

        recipeViewContent.add(steps, 1, 0);

        //create categories content
        Label categories = new Label(recipe.getCategories().toString());

        categories.getStyleClass().clear();
        categories.getStyleClass().add("recipe-view-description");
        categories.setLayoutX(80);
        categories.setLayoutY(10);

        recipeViewBox1.add(categories, 0, 1);
        
        //create serves content
        Label serves = new Label("Serves " + recipe.getNumberOfServings());
        
        serves.getStyleClass().clear();
        serves.getStyleClass().add("recipe-view-description");
        serves.setLayoutX(80);
        serves.setLayoutY(10);

        recipeViewContent.add(serves, 2, 1);

        //creates ingredients content
        Label ingredients = new Label(recipe.getIngredients().toString());

        ingredients.getStyleClass().clear();
        ingredients.getStyleClass().add("recipe-view-description");
        ingredients.setLayoutX(80);
        ingredients.setLayoutY(10);

        recipeViewContent.add(ingredients, 2, 2);

        Label prepTime = new Label("Prep time: " + recipe.getPrepTime());

        prepTime.getStyleClass().clear();
        prepTime.getStyleClass().add("recipe-view-description");
        prepTime.setLayoutX(80);
        prepTime.setLayoutY(10);

        recipeViewBox1.add(prepTime, 0, 2);

        Label calories = new Label("Calories: " + recipe.getCalories());

        calories.getStyleClass().clear();
        calories.getStyleClass().add("recipe-view-description");
        calories.setLayoutX(80);
        calories.setLayoutY(10);

        recipeViewBox1.add(calories, 0, 4);
    }

    //close recipeView
    public void closeRecipeView() {
        recipeView.setVisible(false);
        recipeViewContent.getChildren().clear();
        recipeGrid.setVisible(true);
    }
} 