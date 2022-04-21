package BookOfCook;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BookOfCookController {
    private Cookbook book;
    private ArrayList<Recipe> searchedRecipes;
    private ArrayList<HashMap<String, String>> fridgeFood;
    private Fridge fridge;
    private ArrayList<Category> categoriesClicked = new ArrayList<Category>();
    private FileHandler fileHandler;
    //*FIELDS FOR RECIPE CREATOR (TEMPORARY ARRAYS)
    private ArrayList<String> stepsCreator;  
    private ArrayList<String> categoryCreator;
    private ArrayList<HashMap<String, String>> IngredCreator;

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

    //*INITIALIZATION
    public void initialize(){//initializes the controller
        initBookFridgeDummys();
        initFridgeFood();
        initRecipeComponents();
    }

    //! kan flyttes over til backend i cookbook
    private void initBookFridgeDummys(){//!muligens legge i egen fil
        book = new Cookbook();
        fridge = new Fridge();

        HashMap<String, String> ost = new HashMap<String, String>() {{//! IKKE EN DEL AV SLUTTPRODUKTET START START START START START 
            put("name", "ost");
            put("amount", "1.0");
            put("unit", "kg");
        }};
        HashMap<String, String> melk = new HashMap<String, String>() {{
            put("name", "melk");
            put("amount", "2.0");
            put("unit", "L");
        }};

        HashMap<String, String> tomat = new HashMap<String, String>() {{
            put("name", "tomat");
            put("amount", "5.0");
            put("unit", "stk");
        }};

        Category italiensk = new Category("italiensk");
        Category burger = new Category("burger");
        Category kjøtt = new Category("kjøtt");

        // DUMMYOPPSKRIFTER
        book.addRecipe(new Recipe("Pizza", 2, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), new ArrayList<String>(Arrays.asList("Tiss i en kopp", "Kok øving"))));
        book.addRecipe(new Recipe("Hamburger", 1, "Hambur er godt", "30 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk, tomat)), new ArrayList<Category>(Arrays.asList(kjøtt, burger)), new ArrayList<String>(Arrays.asList("Tiss i en kopp", "Kok øving", "blabla"))));

        fridge.addFood("tomater", "4", "stk");
        fridge.addFood("melk", "2", "L");
        fridge.addFood("ost", "400", "g");
        fridge.addFood("egg", "8", "stk");

        book.collectCategories();

        for(Category category : book.getCategories()){          // fyller inn kategorilista
            categList.getItems().add(categComponent(category));
        }                                                       //! IKKE EN DEL AV SLUTTPRODUKTET SLUTT SLUTT SLUTT SLUTT SLUTT SLUTT SLUTT 

        recipeAmount.setText(String.valueOf("Currently showing " + book.getRecipes().size() + "/" + book.getAmount() + " recipes.")); 

        unitComboBoxRecipe.getItems().addAll("stk", "L", "g", "dL", "kg", "cl");
        unitComboBoxFridge.getItems().addAll("stk", "L", "g", "dL", "kg", "cl");
        timeUnitComboBoxRecipe.getItems().addAll("minutes", "hours", "days");

        stepsCreator = new ArrayList<String>();// create creator arrays
        categoryCreator = new ArrayList<String>();
        IngredCreator = new ArrayList<HashMap<String, String>>();

        fileHandler = new FileHandler(); // initialize filehandler
    }

    private void initRecipeComponents() {//inititializes the recipe components in the GUI, from the cookbook
        for (Recipe recipe : book.getRecipes()/*book.filter(book.getRecipes(), fridge)*/) {
            recipeList.getItems().add(recipeComponent(recipe));
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

    //*UPDATERS
    public void updateRecipeList(){
        recipeList.getItems().clear();
        updateCategList();
        initRecipeComponents();
    }

    private void updateCategList(){
        categList.getItems().clear();

        book.collectCategories();

        for(Category category : book.getCategories()){                                //!init lager, updater skal gjøre klar for ny init ved endring
            categList.getItems().add(categComponent(category));
        }
    }

    public void updatefridge(){
        fridgeList.getItems().clear();
        initFridgeFood();
    }

    private void updateStepCreatorList() {
        stepsArea.clear();
        stepCreatorList.getItems().clear();
        for(String step : stepsCreator){  
            stepCreatorList.getItems().add(createRemovable(step, removeList(step, stepsCreator)));
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
            categCreatorList.getItems().add(createRemovable(category, removeList(category, categoryCreator)));
        }
    }


    //*STYLING
    public void styleNode(Node node, String styleClass, Double x, Double y){
        node.getStyleClass().clear();
        node.getStyleClass().add(styleClass);
        node.setLayoutX(x);
        node.setLayoutY(y);
    }

    public void styleRegion(Region region, String styleClass, Double width, Double height){
        region.getStyleClass().clear();
        region.getStyleClass().add(styleClass);
        region.setMaxWidth(width);
        region.setMaxWidth(height);
    }
    
    //!REMOVE METHODS trenger generell metode, for å  kutte linjer
    public void removeRecipe(Recipe recipe){
        book.removeRecipe(recipe);
        updateRecipeList();
        closeRecipeView();
    }

    public Button removeCategoryList(String target){
        Button btn = new Button("-");
        styleNode(btn, "standard-button", 10.0, 0.0);
        btn.setOnAction(e -> {
            categoryCreator.remove(target);
            updateCategCreatorList();
        });
        return btn;
    }

    public Button removeIngredientList(HashMap<String, String> target){
            Button btn = new Button("-");
            styleNode(btn, "standard-button", 10.0, 0.0);
            btn.setOnAction(e -> {
                IngredCreator.remove(target);
                updateIngredCreatorList();
            });   
            return btn;
    }

    //*!BUTTONS metodene ligner svært muye på hverandre. lage en generell funksjon?
    private void closeBtn(Recipe recipe){    //creates a close Btn for closing recipe view
        Button btn = new Button("Close");           //creates button object
        styleNode(btn, "standard-button", 80.0, 170.0);
        btn.setOnAction(e -> {
            closeRecipeView();
        });
        recipeViewContent.add(btn, 0, 4);        //adds to grid

    }

    private void removeBtn(Recipe recipe){                  //creates a remove Btn in recipe view, for removing the recipe from the cookbook
        Button btn = new Button("Remove");
        styleNode(btn, "standard-button", 80.0, 170.0);
        btn.setOnAction(e -> {
            removeRecipe(recipe);
        });
        recipeViewContent.add(btn, 1, 4);
    }

    //*ADD METHODS
    public void addRecipe() {
        Recipe recipe = new Recipe(recipeNameBar.getText(), Integer.parseInt(servesPeopleBar.getText()),  descriptionArea.getText(), prepTimeBar.getText() + " " + timeUnitComboBoxRecipe.getValue(), getIngredientsFromCreator(), getGategoriesFromCreator(), getStepsFromCreator());
        book.addRecipe(recipe);
        updateRecipeList();
        updateCategList();
        book.collectCategories();
    }

    public void addStepCreator(){                   //create step object with name from textfield, then add step to list in creator
        String stepContent = stepsArea.getText();
        stepsCreator.add(stepContent);
        updateStepCreatorList();                    //adding step to recipe, must happen through adding recipe function. cause that confirms that the steps in list is correct
    }

    //! MYE LOGIKK, FLYTTE TIL BACKEND
    public ArrayList<String> getStepsFromCreator(){
        ArrayList<String> outputArray = new ArrayList<String>(stepsCreator);
        stepsCreator.clear();                                               //clear the list for next use
        stepCreatorList.getItems().clear();                                 //clear the list for next use
        return outputArray;
    }

    public void addIngredientCreator(){
        HashMap<String, String> ingredient = new HashMap<String, String>();
        ingredient.put("name", ingredNameBar.getText());
        ingredient.put("amount", ingredAmountBar.getText());
        ingredient.put("unit", unitComboBoxRecipe.getValue());
        IngredCreator.add(ingredient);
        updateIngredCreatorList();
    }

    //! MYE LOGIKK, FLYTTE TIL BACKEND
    public ArrayList<HashMap<String, String>> getIngredientsFromCreator(){
        ArrayList<HashMap<String, String>> outputArray = new ArrayList<HashMap<String, String>>(); //!refresh
        for(HashMap<String, String> ingredient : IngredCreator){
            outputArray.add(ingredient);
        }
        IngredCreator.clear(); //clear the list for next use
        ingredCreatorList.getItems().clear(); //clear the list for next use
        return outputArray;
    }

    public void addCategoryCreator(){//create category object with name from textfield, then add category to list in creator
        String categoryName = categoryBar.getText();
        categoryCreator.add(categoryName);
        updateCategCreatorList();//adding category to recipe, must happen through adding recipe function. cause that confirms that the categories in list is correct
    }

    //! MYE LOGIKK, FLYTTE TIL BACKEND
    public ArrayList<Category> getGategoriesFromCreator(){
        book.collectCategories();// ta ut alle categories fra creator, sjekkehver enkelt om de finnes fra før av, hvis de finnes legg de til i output, hvis ikke lag en ny category og legg den til i output
        ArrayList<Category> outputArray = new ArrayList<Category>();//!refresh funksjon
        for(String category : categoryCreator){
            if(book.checkIfCategoryExist(category, book)){
                for(Category c : book.getCategories()){
                    if(category.equals(c.getName())){
                        outputArray.add(c);
                    }
                }
            }else{
                outputArray.add(new Category(category));
            }
        }
        categoryCreator.clear(); //clear the list for next use
        categCreatorList.getItems().clear(); //clear the list for next use

        return outputArray;
    }

    //*COMPONENTS
    //! DE TRE METODENE UNDER ER GANSKE LIKE
    private Pane categComponent(Category category){
        Pane body = new Pane(); //creates pane for each category
        body.getChildren().add(categCheckBox(category));//adds children
        styleRegion(body, "category-body", Double.MAX_VALUE, Double.MAX_VALUE);
        return body;
    }

    private Pane foodComponent(String food, String amount, String unit){
        Pane foodBody = new Pane();
        foodBody.getChildren().add(createFoodLabel(food, amount, unit));//adds children
        foodBody.getChildren().add(createDeleteX(food));
        return foodBody;
    }

    private Button recipeComponent(Recipe recipe){
        Button recipeBtn = new Button(recipe.getName());//create button object
        styleRegion(recipeBtn, "recipeBtn", Double.MAX_VALUE, Double.MAX_VALUE);
        recipeBtn.setOnAction(e -> {//event
            viewRecipe(recipe);
        });
        return recipeBtn;
    }

    //*RECIPE VIEW
    public void viewRecipe(Recipe recipe){//opens recipe view and builds the content
        recipeList.setVisible(false);//hide grid and show recipeview
        recipeAmount.setVisible(false);
        recipeView.setVisible(true);
        styleRegion(recipeView, "recipe-view", Double.MAX_VALUE, Double.MAX_VALUE);
        initViewContent(recipe);//add children
    }

    public void closeRecipeView() {//close recipeView
        recipeView.setVisible(false);
        recipeViewBox1.getChildren().clear();   //løser for  column 1 ved å tømme grid når den stenger og så må rekonstruere
        recipeViewBox2.getChildren().clear();   //løser for column 2 ved å tømme grid når den stenger og så må rekonstruere
        recipeList.setVisible(true);
        recipeAmount.setVisible(true);
    }
    
    private void viewLabel(String content, Object parent, int row, int column){//shorthand method for creating labels in recipe viewmode
        Label label = new Label(content);
        styleNode(label, "recipe-view-text", 80.0, 10.0);
        ((GridPane)parent).add(label, column, row);
    }

    //! DISSE TRE METODENE UNDER ER GANSKE LIKE
    private void viewCateg(Recipe recipe){//shorthand method for creating list and fill them with categories in recipe viewmode
        ListView<String> listView = new ListView<String>();
        viewLabel("Categories", recipeViewBox2, 0, 0);//add category label to grid
        
        for(Category category : recipe.getCategories()){//add categories to listview
            listView.getItems().add(category.getName());
        }
        listView.getStyleClass().add("recipe-view-list");
        recipeViewBox2.add(listView, 0, 1);
    }

    private void viewIngred(Recipe recipe){//shorthand method for creating list and fill them with ingredients in recipe viewmode
        ListView<String> listView = new ListView<String>();
        viewLabel("Ingredients", recipeViewBox2, 2, 0);//add ingredients label to grid

        for(HashMap<String, String> ingredient : recipe.getIngredients()){//add ingredients to listview
            listView.getItems().add(ingredient.get("name").toString() + " " + ingredient.get("amount").toString() + " " + ingredient.get("unit").toString());
        }

        listView.getStyleClass().add("recipe-view-list");
        recipeViewBox2.add(listView, 0, 3);
    }

    private void viewSteps(Recipe recipe){//shorthand method for creating list and fill them with steps in recipe viewmode
        ListView<String> listView = new ListView<String>();
        viewLabel("Steps", recipeViewBox2, 4, 0);//add steps label to grid

        for(String step : recipe.getSteps()){//add steps to listview
            listView.getItems().add(step);
        }

        listView.getStyleClass().add("recipe-view-list");
        recipeViewBox2.add(listView, 0, 5);
    }

    private CheckBox categCheckBox(Category category){
        CheckBox checkbox = new CheckBox(category.getName()); //creates a checkbox with category name       

        checkbox.setOnAction(e -> {//on checkbox click
            if(checkbox.isSelected()){
                categoriesClicked.add(category);
            }else{
                categoriesClicked.remove(category);
            }
            filterRecipes(categoriesClicked);
        });
        return checkbox;
    }

    //*OTHER METHODS
    public Pane createRemovable(String content, Button btn){
        Pane pane = new Pane();
        Label label = new Label(content);

        styleNode(label, "list-label", 80.0, 10.0); 
        pane.getChildren().add(label);
        pane.getChildren().add(btn);
        return pane;
    }

    private Button createDeleteX(String string){
        Button btn = new Button("X");
        styleNode(btn, "standard-button", 10.0, 0.0);
        btn.setOnAction(e -> {
            fridge.removeFood(string);
            updatefridge();
        });
        return btn;
    }

    private Label createFoodLabel(String foodname, String foodamount, String foodunit){
        Label label = new Label();
        label.setText(foodname + ": " + foodamount + " " + foodunit);
        styleNode(label, "list-label", 80.0, 10.0);
        return label;
    }

    //! SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH  SEARCH 
    //! SAKL VEKK
    public void searchFood() {
        searchedRecipes = book.searchRecipes(searchBar.getText(), book.getRecipes());
        if(searchedRecipes.size() > 0){
            recipeList.getItems().clear();      //!refresh funksjon
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
        if(fridgeNameInput.getText().length() == 0 || fridgeAmountInput.getText().length() == 0){
            System.out.println("Not all textfields filled in");
            return;
        }
        fridge.addFood(fridgeNameInput.getText(), fridgeAmountInput.getText(), unitComboBoxFridge.getValue());
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
        
        updateRecipeList();
        updateCategList();
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

    public Button removeList(String target, ArrayList<String> listView){
        Button btn = new Button("X");
        btn.getStyleClass().clear();
        styleNode(btn, "standard-button", 10.0, 0.0);


        btn.setOnAction(e -> {
            listView.remove(target);
            updateCategCreatorList();
            updateStepCreatorList();
        });
        return btn;
    }

    public void update(){
        
    };
}