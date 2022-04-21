package BookOfCook;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BookOfCookController {
    //-------------------
    //*FIELDS
    //-------------------
    private Cookbook book;
    private ArrayList<Recipe> recipes, searchedRecipes;
    private ArrayList<HashMap<String, String>> fridgeFood;
    private Fridge fridge;
    private int recipesShown;                                          //! hva skjer her       
    private ArrayList<Category> categoriesClicked = new ArrayList<Category>();
    private FileHandler fileHandler;

    //*FIELDS FOR RECIPE CREATOR (TEMPORARY ARRAYS)
    private ArrayList<String> stepsCreator;  
    private ArrayList<String> categoryCreator;
    private ArrayList<HashMap<String, String>> IngredCreator;

    //*FXML-noder
    @FXML
    private Label number, label, recipeAmount;  
    @FXML
    private GridPane recipeViewContent, recipeViewBox1, recipeViewBox2;
    @FXML
    private Pane recipeView, recipeStepView;
    @FXML
    private ListView fridgeList, categList, recipeList, categCreatorList, ingredCreatorList, stepCreatorList;
    @FXML
    private TextField recipeNameBar, servesPeopleBar, prepTimeBar, categoryBar, caloriesBar, ingredNameBar, ingredAmountBar, searchBar, fridgeNameInput, fridgeAmountInput, fridgeUnitInput;
    @FXML
    private TextArea descriptionArea, stepsArea;
    @FXML
    private ComboBox<String> unitComboBoxRecipe, unitComboBoxFridge, timeUnitComboBoxRecipe;
    @FXML
    private CheckBox fridgeCheckbox;

    //-------------------------------------
    //*INITIALIZATION
    //-------------------------------------
    //initializes the controller
    public void initialize(){
        initbook();
        initFridge();
        initDummy();
        initFridgeFood();
        initRecipeComponents();
        initRecipeAmount();
        initCategories();
        initUnitBoxes();
        initTempArrays();
        initFileHandler();
    }

    //inititializes the recipe components in the GUI, from the cookbook
    private void initRecipeComponents() {
        //initializes the recipe ArrayList
        recipes = new ArrayList<Recipe>();
        recipes.addAll(book.getRecipes());
        for (Recipe recipe : recipes/*book.filter(book.getRecipes(), fridge)*/) {
            recipeList.getItems().add(recipeComponent(recipe));
        }
    }

    private void initFileHandler() {
        fileHandler = new FileHandler();
    }

    public void initTempArrays() {
        //initializes the temporary arrays
        stepsCreator = new ArrayList<String>();
        categoryCreator = new ArrayList<String>();
        IngredCreator = new ArrayList<HashMap<String, String>>();
    }

    private void initRecipeAmount() {
        recipesShown = recipes.size();
        recipeAmount.setText(String.valueOf("Currently showing " + recipes.size() + "/" + book.getAmount() + " recipes.")); 
    }

    private void initbook(){
        book = new Cookbook();
    }

    private void initFridge(){
        fridge = new Fridge();
    }
    
    //initializes the category components in the GUI
    private void initCategories(){
        book.collectCategories();

        for(Category category : book.getCategories()){
            categList.getItems().add(categComponent(category));
        }
    }
    
    //initializes the food in fridge
    private void initFridgeFood(){
        fridgeFood = new ArrayList<HashMap<String, String>>();
        fridgeFood.addAll(fridge.getFood());
        for(HashMap<String, String> food : fridgeFood){
            fridgeList.getItems().add(foodComponent(food.get("name").toString(), food.get("amount").toString(), food.get("unit").toString()));
        }
    }
    
    public void initViewContent(Recipe recipe){
        //column 1
        viewLabel(recipe.getName(), recipeViewBox1, 0, 0);
        viewLabel(recipe.getDescription(), recipeViewBox1, 1, 0); 
        viewLabel("Serves: " + recipe.getServings(), recipeViewBox1, 2, 0); 
        viewLabel("Prep time: " + recipe.getPrepTime(), recipeViewBox1, 3, 0);
        viewLabel("Calories: " + recipe.getCalories(), recipeViewBox1, 4, 0);
        //column 2
        viewCateg(recipe);
        viewSteps(recipe);
        viewIngred(recipe);
        //btns
        closeBtn(recipe);
        removeBtn(recipe);
        //fikse steps, ingred og steps inn i list views fordi de er arrays
    }

    public void initUnitBoxes(){
        unitComboBoxRecipe.getItems().addAll("stk", "L", "g", "dL", "kg", "cl");
        unitComboBoxFridge.getItems().addAll("stk", "L", "g", "dL", "kg", "cl");
        timeUnitComboBoxRecipe.getItems().addAll("minutes", "hours", "days");
    }

    public void initDummy(){    //!Ikke en del av sluttproduktet
        HashMap<String, String> ost = new HashMap<String, String>() {{
            put("name", "ost");
            put("amount", 1.0);
            put("unit", "kg");
        }};
        HashMap<String, String> melk = new HashMap<String, String>() {{
            put("name", "melk");
            put("amount", 2.0);
            put("unit", "L");
        }};

        HashMap<String, String> tomat = new HashMap<String, String>() {{
            put("name", "tomat");
            put("amount", 5.0);
            put("unit", "stk");
        }};

        Category italiensk = new Category("italiensk");
        Category burger = new Category("burger");
        Category kjøtt = new Category("kjøtt");

        // DUMMYOPPSKRIFTER
        book.addRecipe(new Recipe("Pizza", 2, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), new ArrayList<String>(Arrays.asList("Tiss i en kopp", "Kok øving"))));
        book.addRecipe(new Recipe("Hamburger", 1, "Hambur er godt", "30 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk, tomat)), new ArrayList<Category>(Arrays.asList(kjøtt, burger)), new ArrayList<String>(Arrays.asList("Tiss i en kopp", "Kok øving", "blabla"))));
        
        // DUMMYFRIDGE
        fridge.addFood("tomater", 4, "stk");
        fridge.addFood("melk", 2, "L");
        fridge.addFood("ost", 400, "g");
        fridge.addFood("egg", 8, "stk");
        fridge.addFood("rømme", 3, "dL");
        fridge.addFood("ketchup", 5, "dL");
        fridge.addFood("pastaskruer", 1, "kg");
    }

    //-------------------------------------
    //*UPDATERS
    //-------------------------------------
    public void updateRecipeList(){
        recipeList.getItems().clear();
        updateCategList();
        initRecipeComponents();
    }

    private void updateCategList(){
        categList.getItems().clear();

        book.collectCategories();

        //System.out.println(book.getCategories());
        for(Category category : book.getCategories()){                                //!init lager, updater skal gjøre klar for ny init ved endring
            categList.getItems().add(categComponent(category));
        }

        System.out.println(book.getCategories());
    }

    public void updatefridge(){
        fridgeList.getItems().clear();
        initFridgeFood();
    }

    private void updateStepCreatorList() {
        stepsArea.clear();
        stepCreatorList.getItems().clear();
        for(String step : stepsCreator){  
            stepCreatorList.getItems().add(createRemovable(step, removeStepList(step)));
        }
    }

    private void updateIngredCreatorList() {
        ingredNameBar.clear();
        ingredAmountBar.clear();
        ingredCreatorList.getItems().clear();
        for(HashMap<String, String> ingredient : IngredCreator){ 
            ingredCreatorList.getItems().add(createRemovable(ingredient.get("name") + " " + ingredient.get("amount") + " " + ingredient.get("unit"), removeIngredientList(ingredient)));
        }
    }

    private void updateCategCreatorList() {
        categoryBar.clear();
        categCreatorList.getItems().clear();
        for(String category : categoryCreator){
            categCreatorList.getItems().add(createRemovable(category, removeCategoryList(category)));
        }
    }

    //-------------------------------------
    //*STYLING
    //-------------------------------------
    public void styleLabel(Label label, String styleClass, Double x, Double y){
        label.getStyleClass().clear();
        label.getStyleClass().add(styleClass);
        label.setLayoutX(x);
        label.setLayoutY(y);
    }

    
    //-------------------------------------
    //*REMOVE METHODS
    //-------------------------------------
    public void removeRecipe(Recipe recipe){
        System.out.println("Remove recipe button was clicked");
        book.removeRecipe(recipe);
        updateRecipeList();
        closeRecipeView();
    }

    public Button removeCategoryList(String target){
            Button btn = new Button("X");
    
            btn.getStyleClass().clear();
            btn.getStyleClass().add("standard-button");
            btn.setLayoutX(10);
            btn.setLayoutY(0);
    
            btn.setOnAction(e -> {
                System.out.println("Deleted " + target + " category from recipe creation");
                categoryCreator.remove(target);
                updateCategCreatorList();
            });
            return btn;
    }

    public Button removeStepList(String target){
            Button btn = new Button("X");
    
            btn.getStyleClass().clear();
            btn.getStyleClass().add("standard-button");
            btn.setLayoutX(10);
            btn.setLayoutY(0);
    
            btn.setOnAction(e -> {
                System.out.println("Deleted " + target + " step from recipe creation");
                stepsCreator.remove(target);
                updateStepCreatorList();
            });
            return btn;
    }

    public Button removeIngredientList(HashMap<String, String> target){
            Button btn = new Button("X");
    
            btn.getStyleClass().clear();
            btn.getStyleClass().add("standard-button");
            btn.setLayoutX(10);
            btn.setLayoutY(0);
    
            btn.setOnAction(e -> {
                System.out.println("Deleted " + target + " ingredient from recipe creation");
                IngredCreator.remove(target);
                updateIngredCreatorList();
            });   
            return btn;
    }


    //-------------------------------------
    //*BUTTONS
    //-------------------------------------
    //creates a close Btn for closing recipe view
    private void closeBtn(Recipe recipe){
        //creates button object
        Button btn = new Button("Close");

        //sets style
        btn.getStyleClass().clear();
        btn.getStyleClass().add("standard-button");
        btn.setLayoutX(80);
        btn.setLayoutY(170);

        //sets action
        btn.setOnAction(e -> {
            System.out.println("Close recipe view for: " + recipe.getName());
            closeRecipeView();
        });

        //adds to grid
        recipeViewContent.add(btn, 0, 4);
    }

    //creates a remove Btn in recipe view, for removing the recipe from the cookbook
    private void removeBtn(Recipe recipe){
        //creates button object
        Button removeButton = new Button("Remove");

        //sets style
        removeButton.getStyleClass().clear();
        removeButton.getStyleClass().add("standard-button");
        removeButton.setLayoutX(80);
        removeButton.setLayoutY(170);

        //sets action
        removeButton.setOnAction(e -> {
            removeRecipe(recipe);
        });

        //adds to grid
        recipeViewContent.add(removeButton, 1, 4);
    }

    //-------------------------------------
    //*ADD METHODS
    //-------------------------------------

    //!Funksjonen trenger total overhaling nå som vi har ny konstruktør for recipe objekter
    public void addRecipe() {
        // System.out.println("Add button was clicked");

        // //!create new recipe object
        // //!Recipe recipe = new Recipe(recipeNameBar.getText(), Integer.parseInt(servesPeopleBar.getText()));

        // recipe.setPrepTime(prepTimeBar.getText() + " " + timeUnitComboBoxRecipe.getValue());
        // recipe.setDescription(descriptionArea.getText());

        // //optional info
        // if(!caloriesBar.getText().equals("")){
        //     recipe.setCalories(Integer.parseInt(caloriesBar.getText()));
        // }

        // //add to the arrays using the buttons. and adding them to list
        // addStepsToRecipe(recipe);
        // addIngredientsToRecipe(recipe);
        // addCategoriesToRecipe(recipe);

        // //add to the cookbook
        // book.addRecipe(recipe);

        // //update the recipe list by adding new component
        // updateRecipeList();
        // updateCategList();
        // book.categCollect();       

        // System.out.println(book.getCategories());
    }

    public void addStepCreator(){
        //create step object with name from textfield, then add step to list in creator
        String stepContent = stepsArea.getText();
        stepsCreator.add(stepContent);
        updateStepCreatorList();
        //adding step to recipe, must happen through adding recipe function. cause that confirms that the steps in list is correct
    }

    //partial functions for adding steps, ingredients and categories to recipe. shall be run in addRecipe() 
    public void addStepsToRecipe(Recipe recipe){
        //loop through items in list
        for(String s : stepsCreator){
            //if the step is already in the list, do not add it again
            recipe.addStep(s);
        }
        stepsCreator.clear(); //clear the list for next use
        stepCreatorList.getItems().clear(); //clear the list for next use
    }

    public void addIngredientCreator(){
        HashMap<String, String> ingredient = new HashMap<String, String>();

        ingredient.put("name", ingredNameBar.getText());
        ingredient.put("amount", Integer.parseInt(ingredAmountBar.getText()));
        ingredient.put("unit", unitComboBoxRecipe.getValue());

        IngredCreator.add(ingredient);
        updateIngredCreatorList();
    }

    //partial functions for adding steps, ingredients and categories to recipe. shall be run in addRecipe()
    public void addIngredientsToRecipe(Recipe recipe){
        //loop through items in list
        for(HashMap<String, String> ingredient : IngredCreator){
            //if the step is already in the list, do not add it again
            recipe.addIngredient((String) ingredient.get("name"), (int) ingredient.get("amount"), (String) ingredient.get("unit")); //add ingredient to recipe
        }
        IngredCreator.clear(); //clear the list for next use
        ingredCreatorList.getItems().clear(); //clear the list for next use
    }

    public void addCategoryCreator(){
        //create category object with name from textfield, then add category to list in creator
        String categoryName = categoryBar.getText();
        categoryCreator.add(categoryName);
        updateCategCreatorList();
        //adding category to recipe, must happen through adding recipe function. cause that confirms that the categories in list is correct
    }

    //!HER KJØRER DU MYE BACKEND I CONTROLLEREN SOM ER FRONTEND
    //partial functions for adding steps, ingredients and categories to recipe. shall be run in addRecipe()
    public void addCategoriesToRecipe(Recipe recipe){
        //book.categCollect();
        //loop through items in list

        for(String category : categoryCreator){
            boolean categoryExists = false;                                     //!

            if(book.checkIfCategoryExist(category)){
                for(Category c : book.getCategories()){
                    if(category.equals(c.getName())){
                        recipe.addCategory(c);
                        System.out.println("adding category that alerady exists: " + c);
                    }
                }
            }else{
                recipe.addCategory(new Category(category));
                System.out.println("adding new category: " + category);
            }
            //if the step is already in the list, do not add it again
        }
        categoryCreator.clear(); //clear the list for next use
        categCreatorList.getItems().clear(); //clear the list for next use

        System.out.println("inneholder " + recipe.getCategories());
    }

    //-------------------------------------
    //*COMPONENTS
    //-------------------------------------
    private Pane categComponent(Category category){
        //creates pane for each category
        Pane body = new Pane(); 

        //adds children
        body.getChildren().add(categCheckBox(category));

        //styling
        body.getStyleClass().clear();
        body.getStyleClass().add("category-body");
        body.setMaxWidth(Double.MAX_VALUE);
        body.setMaxHeight(Double.MAX_VALUE);

        return body;
    }

    private Pane foodComponent(String food, String amount, String unit){
        Pane foodBody = new Pane();

        //adds children
        foodBody.getChildren().add(createFoodLabel(food, amount, unit));
        foodBody.getChildren().add(createDeleteX(food));

        return foodBody;
    }

    private Button recipeComponent(Recipe recipe){
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


    //-----------------------------------------------------------
    //*RECIPE VIEW
    //-----------------------------------------------------------
    //opens recipe view and builds the content
    public void viewRecipe(Recipe recipe){
        //hide grid and show recipeview
        recipeList.setVisible(false);
        recipeAmount.setVisible(false);
        recipeView.setVisible(true);
        
        //set recipe style
        recipeView.getStyleClass().clear();
        recipeView.getStyleClass().add("recipe-view");
        recipeView.setMaxWidth(Double.MAX_VALUE);
        recipeView.setMaxHeight(Double.MAX_VALUE);

        //add children
        initViewContent(recipe);
    }

    //close recipeView
    public void closeRecipeView() {
        recipeView.setVisible(false);
        recipeViewBox1.getChildren().clear();   //løser for  column 1 ved å tømme grid når den stenger og så må rekonstruere
        recipeViewBox2.getChildren().clear();   //løser for column 2 ved å tømme grid når den stenger og så må rekonstruere
        recipeList.setVisible(true);
        recipeAmount.setVisible(true);
    }
    
    //shorthand method for creating labels in recipe viewmode
    private void viewLabel(String content, Object parent, int row, int column){
        Label label = new Label(content);
        styleLabel(label, "recipe-view-text", 80.0, 10.0);
        ((GridPane)parent).add(label, column, row);
    }

    //shorthand method for creating list and fill them with categories in recipe viewmode
    private void viewCateg(Recipe recipe){
        ListView<String> listView = new ListView<String>();

        //add category label to grid
        viewLabel("Categories", recipeViewBox2, 0, 0);

        //add categories to listview
        for(Category category : recipe.getCategories()){
            listView.getItems().add(category.getName());
        }
        listView.getStyleClass().add("recipe-view-list");

        (recipeViewBox2).add(listView, 0, 1);
    }

    //shorthand method for creating list and fill them with ingredients in recipe viewmode
    private void viewIngred(Recipe recipe){
        ListView<String> listView = new ListView<String>();

        //add ingredients label to grid
        viewLabel("Ingredients", recipeViewBox2, 2, 0);

        //add ingredients to listview
        for(HashMap<String, String> ingredient : recipe.getIngredients()){
            listView.getItems().add(ingredient.get("name").toString() + " " + ingredient.get("amount").toString() + " " + ingredient.get("unit").toString());
        }

        listView.getStyleClass().add("recipe-view-list");
        (recipeViewBox2).add(listView, 0, 3);
    }

    //shorthand method for creating list and fill them with steps in recipe viewmode
    private void viewSteps(Recipe recipe){
        ListView<String> listView = new ListView<String>();

        //add steps label to grid
        viewLabel("Steps", recipeViewBox2, 4, 0);

        //add steps to listview
        for(String step : recipe.getSteps()){
            listView.getItems().add(step);
        }

        listView.getStyleClass().add("recipe-view-list");
        (recipeViewBox2).add(listView, 0, 5);
    }

    private CheckBox categCheckBox(Category category){
        //creates a checkbox with category name
        CheckBox checkbox = new CheckBox(category.getName());        

        //on checkbox click
        checkbox.setOnAction(e -> {
            System.out.println("Checkbox clicked");
            if(checkbox.isSelected()){
                categoriesClicked.add(category);
            }else{
                categoriesClicked.remove(category);
            }
            System.out.println("CategoriesClicked: " + categoriesClicked);

            filterRecipes(categoriesClicked);
        });
        return checkbox;
    }

    //---------------------------------------------------
    //*OTHER METHODS
    //---------------------------------------------------
    public Pane createRemovable(String content, Button btn){
        Pane pane = new Pane();
        Label label = new Label(content);

        styleLabel(label, "list-label", 80.0, 10.0); 
        pane.getChildren().add(label);
        pane.getChildren().add(btn);
        return pane;
    }

    private Button createDeleteX(String string){
        Button removeFoodBtn = new Button("X");

        removeFoodBtn.getStyleClass().clear();
        removeFoodBtn.getStyleClass().add("standard-button");
        removeFoodBtn.setLayoutX(10);
        removeFoodBtn.setLayoutY(0);

        removeFoodBtn.setOnAction(e -> {
            System.out.println("Delete food " + string);
            fridge.removeFood(string);
            updatefridge();
        });
        return removeFoodBtn;
    }

    private Label createFoodLabel(String foodname, String foodamount, String foodunit){
        Label label = new Label();
        label.setText(foodname + ": " + foodamount + " " + foodunit);
        styleLabel(label, "list-label", 80.0, 10.0);
        return label;
    }

    //! SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH 
    //search for food
    public void searchFood() {
        System.out.println("Search food bar was used was clicked");
        searchedRecipes = book.searchRecipes(searchBar.getText(), book.getRecipes());
        if(searchedRecipes.size() > 0){
            recipeList.getItems().clear();
            for(Recipe r : searchedRecipes){
                recipeList.getItems().add(recipeComponent(r));
            }
        }
        // updateAmountLabel(searchedRecipes);
    }

    //!MYE LOGIKK, filter recipes with categories
    public void filterRecipes(ArrayList<Category> categoriesClicked) {
        recipeList.getItems().clear();
        if(categoriesClicked.size() == 0){
            initRecipeComponents();
            return;
        }

        ArrayList<Recipe> sortedRecipes = book.filterByCategories(categoriesClicked);
        if(sortedRecipes.size() == 0){
        }

        for(Recipe r : sortedRecipes){
            recipeList.getItems().add(recipeComponent(r));
        }
        // updateAmountLabel(sortedRecipes);
    }

    //!MYE LOGIKK, bURDE GÅ I BACKEND
    public void fridgeAddFood() {
        System.out.println("add food btton");
        if(fridgeNameInput.getText().length() == 0 || fridgeAmountInput.getText().length() == 0){
            System.out.println("Not all textfields filled in");
            return;
        }
        // if(fridge.isFoodInFridge(fridgeNameInput.getText())){
        //     System.out.println("That type of food is already in your fridge");
        //     return;
        // }
        fridge.addFood(fridgeNameInput.getText(), Double.parseDouble(fridgeAmountInput.getText()), unitComboBoxFridge.getValue());
        updatefridge();
    }

    //*FILE WRITING
    public void load() {        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Cookbook");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage); 

        book = fileHandler.load(file);          //if the user chooses a file, initialize the cookbook
        initialize();
    }

    public void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Cookbook");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        fileHandler.save(file, book);
    }

    /*
    TODO: RØDT ER BUGS ELLER TING Å GJØRE, BLÅTT ER SPØRSMÅL, GRØNNT ER TING VI HAR GJORT ELLER GJØR
    ! om det er egg i kjøleskapet, hvorfor kan man ikke legge til flere egg med samme enhet?
    ! legge til ny oppskrift sørger for duplikate categories
    ! fix load book
    * fix save book
    ! skalering av oppskrifts, funksjon
    ! fridge filter av og på funksjon (i det hele tatt funksjon)

    !edit recipe funksjon (kan vurdere)

    ? hvordan skiller vi kategorier
    ? legge edit og lage recipe tool i et popupvindu?
    ? vise til studass en stund før fristen for å finne ut av potensielle endringer litt tidlig.
    */
} 