package BookOfCook;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


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
    
    private void initializeFridgeFood(){
        fridgeFood = new ArrayList<HashMap<String, Object>>();
        fridgeFood.addAll(fridge.getFoodInFridge());

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
        styleLabel(label, "recipe-header", 80.0, 10.0);
        return label;
    }


    //*DYNAMIC CREATION OF CATEGORY COMPONENTS
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


    //*DYNAMIC CREATION OF FRIDGE COMPONENTS
    private Pane createFoodComponent(HashMap<String, Object> food){
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
        styleLabel(label, "food-label", 80.0, 10.0);
        return label;
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
        initializeViewContent(recipe);
    }

    //fills recipeContent-grid in recipeView, with content from recipe
    public void initializeViewContent(Recipe recipe){
        addViewContent(recipe);
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
        recipeViewContent.add(closeButton, 0, 4);
    }

    //create title label with recipe name
    private void createRecipeViewTitle(Recipe recipe){
        Label label = new Label(recipe.getDisplayedName());
        styleLabel(label,"recipe-view-title", 80.0, 10.0);
        recipeViewBox1.add(label, 0, 0);
    }

    //create description label, steps ingredient, categories and serves label
    private void addViewContent(Recipe recipe){
        createRecipeViewTitle(recipe);
        createDescriptionLabel(recipe);
        createStepsLabel(recipe);
        createIngredientsLabel(recipe);
        createCategoriesLabel(recipe);
        createServesLabel(recipe);
        createPrepTimeLabel(recipe);
        createCaloriesLabel(recipe);
    }

    //create description label
    private void createDescriptionLabel(Recipe recipe){
        Label label = new Label(recipe.getDescription());
        styleLabel(label, "recipe-view-description", 80.0, 10.0);
        recipeViewBox1.add(label, 0, 2);
    }

    //create steps label
    private void createStepsLabel(Recipe recipe){
        Label label = new Label("Steps:");
        styleLabel(label, "recipe-view-steps", 80.0, 10.0);
        recipeViewContent.add(label, 1, 0);
    }

    //create ingredients label
    private void createIngredientsLabel(Recipe recipe){
        Label label = new Label("Ingredients:");
        styleLabel(label, "recipe-view-ingredients", 80.0, 10.0);
        recipeViewBox2.add(label, 0, 1);
    }

    //create categories label
    private void createCategoriesLabel(Recipe recipe){
        Label label = new Label("Categories:");
        styleLabel(label, "recipe-view-categories", 80.0, 10.0);
        recipeViewBox1.add(label, 0, 1);
    }

    //create serves label
    private void createServesLabel(Recipe recipe){
        Label label = new Label("Serves: " + recipe.getNumberOfServings());
        styleLabel(label, "recipe-view-serves", 80.0, 10.0);
        recipeViewBox2.add(label, 0, 0);
    }

    //create prep time label
    private void createPrepTimeLabel(Recipe recipe){
        Label label = new Label("Prep time: " + recipe.getPrepTime());
        styleLabel(label, "recipe-view-prep-time", 80.0, 10.0);
        recipeViewBox1.add(label, 0, 3);
    }

    //create calories label
    private void createCaloriesLabel(Recipe recipe){
        Label label = new Label("Calories: " + recipe.getCalories());
        styleLabel(label, "recipe-view-calories", 80.0, 10.0);
        recipeViewBox1.add(label, 0, 5);
    }

    //close recipeView
    public void closeRecipeView() {
        recipeView.setVisible(false);
        recipeViewContent.getChildren().clear();
        recipeGrid.setVisible(true);
    }


    //*STYLESETTERS
    public void styleLabel(Label label, String styleClass, Double x, Double y){
        label.getStyleClass().clear();
        label.getStyleClass().add(styleClass);
        label.setLayoutX(x);
        label.setLayoutY(y);
    }


    //*EVENT HANDLERS
    public void addRecipe() {
        System.out.println("Add button was clicked");
        cookbook.addRecipeToCookbook(new Recipe(recipeNameBar.getText(), Integer.parseInt(servesPeopleBar.getText())));
        updateRecipeGrid();
    }

    //view recipe
    public void viewRecipe() {
        System.out.println("View button was clicked");
    }

    //remove food from fridge
    public void removeFood() {
        System.out.println("Remove food button was clicked");
    }
} 