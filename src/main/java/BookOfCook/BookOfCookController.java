package BookOfCook;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BookOfCookController {
    private Cookbook cookbook;
    private ArrayList<Recipe> recipes, searchedRecipes;
    private ArrayList<HashMap<String, Object>> fridgeFood;
    private ArrayList<Category> categories;
    private Fridge fridge;


    //*FXML-noder
    @FXML
    private Label number, label, recipeAmount;  

    @FXML
    private GridPane recipeGrid, recipeViewContent, recipeViewBox1, recipeViewBox2;

    @FXML
    private Pane recipeView, recipeStepView;

    @FXML
    private ListView fridgeList, categoryList;

    @FXML
    private TextField recipeNameBar, servesPeopleBar, prepTimeBar, categoryBar, caloriesBar, ingredientNameBar, amountBar, searchBar, fridgeNameInput, fridgeAmountInput, fridgeUnitInput;

    @FXML
    private TextArea descriptionArea, stepsArea;


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
        updateNumberOfRecipes();
    }

    private void initializeCookBook(){
        cookbook = new Cookbook("Dawg");
        cookbook.addRecipeToCookbook(new Recipe("Pizza", 2));
        cookbook.addRecipeToCookbook(new Recipe("Hamburger", 1));
        cookbook.addRecipeToCookbook(new Recipe("Spaghetti", 2));
        cookbook.addRecipeToCookbook(new Recipe("Enchiladas", 1));
        cookbook.addRecipeToCookbook(new Recipe("Entrecote", 1));
        cookbook.addRecipeToCookbook(new Recipe("Mousaka", 1));
        cookbook.addRecipeToCookbook(new Recipe("Tika masala", 1));
        cookbook.addRecipeToCookbook(new Recipe("garam masala", 1));
        cookbook.addRecipeToCookbook(new Recipe("smalahove", 1));
        cookbook.addRecipeToCookbook(new Recipe("ribbe", 1));
        // cookbook.addRecipeToCookbook(new Recipe("Pinnekjøtt", 1)); //!Ikke lov med æøå
        // cookbook.addRecipeToCookbook(new Recipe("kjøttkaker", 1));
        cookbook.addRecipeToCookbook(new Recipe("lasagne", 1));
        cookbook.addRecipeToCookbook(new Recipe("croissant", 1));
        cookbook.addRecipeToCookbook(new Recipe("french toast", 1));
        cookbook.addRecipeToCookbook(new Recipe("bacon and eggs", 1));
        cookbook.addRecipeToCookbook(new Recipe("Spam", 1));
        cookbook.addRecipeToCookbook(new Recipe("tomatsuppe", 1));
        cookbook.addRecipeToCookbook(new Recipe("pulled Pork", 1));
        cookbook.addRecipeToCookbook(new Recipe("Tacos", 1));
    }

    private void initializeRecipes(){
        recipes = new ArrayList<Recipe>();
        recipes.addAll(cookbook.getRecipes());
        /*recipes.addAll(List.of(
            new Recipe("Pizza", 2),
            new Recipe("Hamburger", 1),
            new Recipe("Spaghetti", 2),
            new Recipe("Biff", 1)
        ));*/

        Category italiensk = new Category("italiensk");
        Category burger = new Category("burger");
        Category kjøtt = new Category("kjøtt");

        recipes.get(0).addCategory(italiensk);
        recipes.get(1).addCategory(burger);
        recipes.get(2).addCategory(italiensk);
        recipes.get(3).addCategory(kjøtt);

        cookbook.collectCategories();

        //recipes.addAll(cookbook.getRecipes());
    }
    
    private void initializeCategories(){

        categories = new ArrayList<Category>();
        cookbook.collectCategories();

        categories.addAll(cookbook.getCategories());

        //System.out.println(cookbook.getCategories());

        for(Category category : categories){
            categoryList.getItems().add(createCategoryComponent(category));
        }
    }
    
    private void initializeFridge(){
        fridge = new Fridge();
        fridge.addFood("tomater", 4, "stk");
        fridge.addFood("melk", 2, "L");
        fridge.addFood("ost", 400, "g");
        fridge.addFood("egg", 8, "stk");
        fridge.addFood("rømme", 3, "dL");
        fridge.addFood("ketchup", 5, "dL");
        fridge.addFood("pastaskruer", 1, "kg");
    }
    
    private void initializeFridgeFood(){
        fridgeFood = new ArrayList<HashMap<String, Object>>();
        fridgeFood.addAll(fridge.getFoodInFridge());

        for(HashMap<String, Object> food : fridgeFood){
            fridgeList.getItems().add(createFoodComponent(food.get("name").toString(), food.get("amount").toString(), food.get("unit").toString()));
        }
    }


    //*UPDATERS
    public void updateRecipeGrid(){
        recipeGrid.getChildren().clear();
        updateCategories();
        initializeRecipeGrid();
    }

    private void updateCategories(){
        categoryList.getItems().clear();
        initializeCategories();
    }

    public void updatefridge(){
        fridgeList.getItems().clear();

        initializeFridgeFood();
    }

    public void updateNumberOfRecipes(){
        recipeAmount.setText(String.valueOf("Currently showing " + cookbook.getRecipeAmount() + "/" + cookbook.getRecipeAmount() + " recipes."));
    }
    


    //*DYNAMIC CREATION OF RECIPE COMPONENTS IN GRID
    private Button createRecipeComponent(Recipe recipe){
        //create button object
        Button recipeBtn = new Button(recipe.getName());

        //style
        recipeBtn.getStyleClass().clear();
        recipeBtn.getStyleClass().add("recipeBtn");
        recipeBtn.setMaxWidth(Double.MAX_VALUE);
        recipeBtn.setMaxHeight(Double.MAX_VALUE);

        //event
        recipeBtn.setOnAction(e -> {
            System.out.println("View " + recipe.getName() + " recipe");
            viewRecipe(recipe);
        });

        //return
        return recipeBtn;
    }

    //*DYNAMIC CREATION OF CATEGORY COMPONENTS
    private Pane createCategoryComponent(Category category){
        //creates pane for each category
        Pane body = new Pane(); 

        //adds children
        //body.getChildren().add(createCategoryLabel(category));
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
        /* checkbox.getStyleClass().clear();
        checkbox.getStyleClass().add("category-checkbox");
        checkbox.setLayoutX(0);
        checkbox.setLayoutY(0); */

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
    private Pane createFoodComponent(String food, String amount, String unit){
        Pane foodBody = new Pane();

        //adds children
        foodBody.getChildren().add(createFoodLabel(food, amount, unit)); //!TO
        foodBody.getChildren().add(createDeleteX(food));

        return foodBody;
    }

    private Button createDeleteX(String foodname){
        Button removeFoodBtn = new Button("X");

        removeFoodBtn.getStyleClass().clear();
        removeFoodBtn.getStyleClass().add("standard-button");
        removeFoodBtn.setLayoutX(10);
        removeFoodBtn.setLayoutY(0);

        removeFoodBtn.setOnAction(e -> {
            System.out.println("Delete food " + foodname);

            fridge.removeFood(foodname);

            updatefridge();
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

    public void initializeViewContent(Recipe recipe){
        createRecipeViewTitle(recipe);
        createDescriptionLabel(recipe); //! fungerer ikke optimalt
        createStepsLabel(recipe);       //! fungerer ikke optimalt
        createIngredientsLabel(recipe); //! fungerer ikke
        createCategoriesLabel(recipe);  //! fungerer ikke optimalt
        createServesLabel(recipe);
        createPrepTimeLabel(recipe);
        createCaloriesLabel(recipe);
        addCloseButton(recipe);
    }

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

    private void createRecipeViewTitle(Recipe recipe){
        Label label = new Label(recipe.getDisplayedName());
        styleLabel(label,"recipe-header", 80.0, 10.0);
        recipeViewBox1.add(label, 0, 0);
    }

    //first column in recipeview
    private void createDescriptionLabel(Recipe recipe){
        Label label = new Label(recipe.getDescription());
        styleLabel(label, "recipe-view-text", 80.0, 10.0);
        recipeViewBox1.add(label, 0, 2);
    }

    private void createCategoriesLabel(Recipe recipe){
        Label label = new Label("Categories:" + recipe.getCategories());
        styleLabel(label, "recipe-view-text", 80.0, 10.0);
        recipeViewBox1.add(label, 0, 1);
    }

    private void createPrepTimeLabel(Recipe recipe){
        Label label = new Label("Prep time: " + recipe.getPrepTime());
        styleLabel(label, "recipe-view-text", 80.0, 10.0);
        recipeViewBox1.add(label, 0, 3);
    }

    private void createCaloriesLabel(Recipe recipe){
        Label label = new Label("Calories: " + recipe.getCalories());
        styleLabel(label, "recipe-view-text", 80.0, 10.0);
        recipeViewBox1.add(label, 0, 5);
    }

    //second column in recipeview
    private void createStepsLabel(Recipe recipe){
        Label label = new Label("Steps:" + recipe.getSteps());
        styleLabel(label, "recipe-view-text", 80.0, 10.0);
        recipeStepView.getChildren().clear();
        recipeStepView.getChildren().add(label);
    }

    //third column in recipeview
    private void createServesLabel(Recipe recipe){
        Label label = new Label("Serves: " + recipe.getNumberOfServings());
        styleLabel(label, "recipe-view-text", 80.0, 10.0);
        recipeViewBox2.add(label, 0, 0);
    }

    private void createIngredientsLabel(Recipe recipe){
        Label label = new Label("Ingredients:");
        styleLabel(label, "recipe-view-text", 80.0, 10.0);
        recipeViewBox2.add(label, 0, 1);
    }

    //close recipeView
    public void closeRecipeView() {
        recipeView.setVisible(false);
        recipeViewBox1.getChildren().clear();   //løser for  column 1 ved å tømme grid når den stenger og så må rekonstruere
        recipeViewBox2.getChildren().clear();   //løser for column 2 ved å tømme grid når den stenger og så må rekonstruere
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
        Recipe recipe = new Recipe(recipeNameBar.getText(), Integer.parseInt(servesPeopleBar.getText()));
        Category category = new Category(categoryBar.getText());

        recipe.setDescription(descriptionArea.getText());

        if(!caloriesBar.getText().equals("")){
            recipe.setCalories(Integer.parseInt(caloriesBar.getText()));
        }

        recipe.setPrepTime(Integer.parseInt(prepTimeBar.getText()));
        
        recipe.addCategory(category);

        // recipe.setIngredients(ingredientBar.getText());

        recipe.addStep(stepsArea.getText());

        cookbook.addRecipeToCookbook(recipe);
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

    //search for food
    public void searchFood() {
        System.out.println("Search food bar was used was clicked");
        searchedRecipes = cookbook.searchRecipes(searchBar.getText());
        if(searchedRecipes.size() > 0){
            recipeGrid.getChildren().clear();
            for(Recipe r : searchedRecipes){
                recipeGrid.add(createRecipeComponent(r), searchedRecipes.indexOf(r) % 3,  searchedRecipes.indexOf(r) / 3);
            }
        }
    }

    public void fridgeAddFood() {
        System.out.println("add food button");

         if(fridgeNameInput.getText().length() == 0 || fridgeAmountInput.getText().length() == 0 || fridgeUnitInput.getText().length() == 0){

            System.out.println("Not all textfields filled in");
            return;
        }

        if(fridge.isFoodInFridge(fridgeNameInput.getText())){
            System.out.println("That type of food is already in your fridge");
            return;
        }

        fridge.addFood(fridgeNameInput.getText(), Double.parseDouble(fridgeAmountInput.getText()), fridgeUnitInput.getText());
        
        updatefridge();
    }

    //Files
    public void load() {
        System.out.println("Load button was clicked");
    }

    public void save() {
        System.out.println("Save button was clicked");
    }

    /*
    TODO: RØDT ER BUGS ELLER TING Å GJØRE, BLÅTT ER SPØRSMÅL, GRØNNT ER TING VI HAR GJORT ELLER GJØR
    Ny oppdagede bugs:


    fridge
    ! add fridge 
    ! fix remove food function
    ? bruke predicates for å filtrere etter ingredienser i fridge tool. feks et predicate som sier at oppskriften kan lages av maten i fridge

    category
    ! add category components with filters, use checkbox because that are standard
    ? hvordan skiller vi kategorier
    ? bruke predicates for å filtrere etter kategorier

    skriving til fil
    ! fix load cookbook function
    ! fix save cookbook 

    recipe grid
    ! finne en metode for å sørge for at oppskrifter ikke stables i grid, men at vi heller kan scrolle
    ! finne en metdoe for å gjøre fonten mindre og få teksten til å bryte linjer dersom den er for lang til å vises
    
    recipe creator og editor
    ! la recipe ta inn 'æøå som symboler i navns
    ! steps ikke linket med view
    ! koble opp legge til ingredienser
    ! setup connection with textfields, so that when you click on a recipe, the textfields are filled with the recipe info for editing
    ! fix remove recipe function
    ! sette opp dropdown menyer for enheter
    ? legge edit og lage recipe tool i et popupvindu?
    ? picture support

    i klassene
    ! gjøre om name til recipes om til lowercase
    !? do we have full on validation?

    logistikk
    ? hvem skriver dokumentasjonen
    ? vise til studass en stund før fristen for å finne ut av potensielle endringer litt tidlig.

    søkefelt
    ! må oppdatere hvor mange av totalt oppskrifter den viser
    ? knapp for å fjerne all text fra search field?


    MARKUS HAR GJORT: ikke en dritt
    * gått over privacy declarators i Recipe klassen
    * gått over privacy declarators i Cookbook klassen
    * gått over privacy declarators i Category klassen
    * gått over privacy declarators i Fridge klassen

    JULIAN HAR GJORT: masse
    

    */
} 