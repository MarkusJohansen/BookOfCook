package BookOfCook;

import java.util.ArrayList;
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

public class BookOfCookController {
    //-------------------
    //*FIELDS
    //-------------------

    private Cookbook book;
    private ArrayList<Recipe> recipes, searchedRecipes;
    private ArrayList<HashMap<String, Object>> fridgeFood;
    private ArrayList<Category> categories;
    private Fridge fridge;
    private int numbersOfRecipesShown;
    private ArrayList<Category> categoriesClicked = new ArrayList<Category>();

    //*FIELDS FOR RECIPE CREATOR (TEMPORARY ARRAYS)
    private ArrayList<String> stepsCreator = new ArrayList<String>();  
    private ArrayList<String> categoryCreator = new ArrayList<String>();
    private ArrayList<HashMap<String, Object>> IngredCreator = new ArrayList<HashMap<String, Object>>();
    

    //-------------------------------------
    //*FXML-noder
    //-------------------------------------
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


    //-------------------------------------
    //*INITIALIZATION
    //-------------------------------------

    //initializes the controller
    public void initialize(){
        //! fungerer ikke searchBar.requestFocus();
        initbook();
        initRecipeComponents();
        initFridge();
        initCateg();
        initFridgeFood();
        initUnitBoxes();
    }

    //inititializes the recipe components in the GUI
    private void initRecipeComponents() {
        initRecipes();
        for (Recipe recipe : recipes) {
            Recipe r = recipe; //copy recipe
            recipeList.getItems().add(recipeComponent(r));
        }
        updateAmount();
    }

    //initializes the book components in the GUI
    private void initbook(){
        book = new Cookbook("foo");
        book.addRecipe(new Recipe("Pizza", 2));
        book.addRecipe(new Recipe("Hamburger", 1));
        book.addRecipe(new Recipe("Spaghetti", 2));
        book.addRecipe(new Recipe("Enchiladas", 1));
        book.addRecipe(new Recipe("Entrecote", 1));
        book.addRecipe(new Recipe("Mousaka", 1));
        book.addRecipe(new Recipe("Tika masala", 1));
        book.addRecipe(new Recipe("garam masala", 1));
        book.addRecipe(new Recipe("smalahove", 1));
        book.addRecipe(new Recipe("ribbe", 1));
    }

    //initializes the recipe ArrayList
    private void initRecipes(){
        recipes = new ArrayList<Recipe>();
        recipes.addAll(book.getRecipes());
    }
    
    //initializes the category components in the GUI
    private void initCateg(){
        categories = new ArrayList<Category>();
        
        Category italiensk = new Category("italiensk");
        Category burger = new Category("burger");
        Category kjøtt = new Category("kjøtt");

        recipes.get(0).addCategory(italiensk);
        recipes.get(1).addCategory(burger);
        recipes.get(2).addCategory(italiensk);
        recipes.get(3).addCategory(kjøtt);

        book.categCollect();

        categories.addAll(book.getCategories());

        //System.out.println(book.getCategories());
        for(Category category : categories){
            categList.getItems().add(categComponent(category));
        }
    }
    
    //initializes the frige object and the fridge food components in the GUI
    private void initFridge(){
        fridge = new Fridge();
        fridge.addFood("tomater", 4, "stk");
        fridge.addFood("melk", 2, "L");
        fridge.addFood("ost", 400, "g");
        fridge.addFood("egg", 8, "stk");
        fridge.addFood("rømme", 3, "dL");
        fridge.addFood("ketchup", 5, "dL");
        fridge.addFood("pastaskruer", 1, "kg");
    }
    
    //initializes the food in fridge
    private void initFridgeFood(){
        fridgeFood = new ArrayList<HashMap<String, Object>>();
        fridgeFood.addAll(fridge.getFood());
        for(HashMap<String, Object> food : fridgeFood){
            fridgeList.getItems().add(foodComponent(food.get("name").toString(), food.get("amount").toString(), food.get("unit").toString()));
        }
    }
    
    public void initViewContent(Recipe recipe){
        //column 1
        viewLabel(recipe.getDisplayedName(), recipeViewBox1, 0, 0);
        viewLabel(recipe.getDescription(), recipeViewBox1, 1, 0); 
        viewLabel("Serves: " + recipe.getNumberOfServings(), recipeViewBox1, 2, 0); 
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
        initCateg();
    }

    public void updatefridge(){
        fridgeList.getItems().clear();
        initFridgeFood();
    }

    //! maybe only one of these
    public void updateAmount(){
        numbersOfRecipesShown = recipes.size();
        recipeAmount.setText(String.valueOf("Currently showing " + recipes.size() + "/" + book.getAmount() + " recipes."));
    }

    public void updateAmountLabel(ArrayList<Recipe> recipeArray){
        numbersOfRecipesShown = recipes.size();
        recipeAmount.setText(String.valueOf("Currently showing " + recipeArray.size() + "/" + book.getAmount() + " recipes."));
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
    //*RECIPE VIEW FUNCTIONALITY
    //-------------------------------------

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
        for(HashMap<String, Object> ingredient : recipe.getIngredients()){
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


    //-----------------------------------------------------------
    //*RECIPE CREATION FUNCTIONALITY
    //-----------------------------------------------------------
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

    public void addRecipe() {
        System.out.println("Add button was clicked");

        //create new recipe object 
        Recipe recipe = new Recipe(recipeNameBar.getText(), Integer.parseInt(servesPeopleBar.getText()));

        //
        recipe.setPrepTime(prepTimeBar.getText() + " " + timeUnitComboBoxRecipe.getValue());
        recipe.setDescription(descriptionArea.getText());

        //optional info
        if(!caloriesBar.getText().equals("")){
            recipe.setCalories(Integer.parseInt(caloriesBar.getText()));
        }

        //add to the arrays using the buttons. and adding them to list
        addStepsToRecipe(recipe);
        addIngredientsToRecipe(recipe);
        addCategoriesToRecipe(recipe);

        //add to the cookbook
        book.addRecipe(recipe);

        //update the recipe list by adding new component
        updateRecipeList();
    }

    //remove recipe
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

    public Button removeIngredientList(HashMap<String, Object> target){
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

    //*adding recipe steps */
    public Pane createRemovable(String content, Button btn){
        Pane pane = new Pane();
        Label label = new Label(content);

        styleLabel(label, "list-label", 80.0, 10.0); 

        pane.getChildren().add(label);
        pane.getChildren().add(btn);

        return pane;
    }
    
    public void addStepCreator(){
        //create step object with name from textfield, then add step to list in creator
        stepCreatorList.getItems().add(stepsArea.getText());
        updateStepCreatorList();
        //adding step to recipe, must happen through adding recipe function. cause that confirms that the steps in list is correct
    }

    private void updateStepCreatorList() {
        stepsArea.clear();
        stepCreatorList.getItems().clear();
        for(String step : stepsCreator){
            stepCreatorList.getItems().add(createRemovable(step, removeStepList(step)));
        }
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

    //*adding recipe ingredients */
    public void addIngredientCreator(){
        HashMap<String, Object> ingredient = new HashMap<String, Object>();

        ingredient.put("name", ingredNameBar.getText());
        ingredient.put("amount", Integer.parseInt(ingredAmountBar.getText()));
        ingredient.put("unit", unitComboBoxRecipe.getValue());

        IngredCreator.add(ingredient);
        updateIngredCreatorList();
    }

    private void updateIngredCreatorList() {
        ingredNameBar.clear();
        ingredAmountBar.clear();
        ingredCreatorList.getItems().clear();
        for(HashMap<String, Object> ingredient : IngredCreator){
            ingredCreatorList.getItems().add(createRemovable(ingredient.get("name") + " " + ingredient.get("amount") + " " + ingredient.get("unit"), removeIngredientList(ingredient)));
        }
    }

    //partial functions for adding steps, ingredients and categories to recipe. shall be run in addRecipe()
    public void addIngredientsToRecipe(Recipe recipe){
        //loop through items in list
        for(HashMap<String, Object> ingredient : IngredCreator){
            //if the step is already in the list, do not add it again
            recipe.addIngredient((String) ingredient.get("name"), (int) ingredient.get("amount"), (String) ingredient.get("unit")); //add ingredient to recipe
        }
        IngredCreator.clear(); //clear the list for next use
        ingredCreatorList.getItems().clear(); //clear the list for next use
    }

    //*adding recipe categories */
    public void addCategoryCreator(){
        //create category object with name from textfield, then add category to list in creator
        String categoryName = categoryBar.getText();
        categoryCreator.add(categoryName);

        categoryBar.clear();
        categCreatorList.getItems().clear();
        for(String category : categoryCreator){
            categCreatorList.getItems().add(category);
        }

        //updateCategCreatorList();
        //adding category to recipe, must happen through adding recipe function. cause that confirms that the categories in list is correct
    }

    /*private void updateCategCreatorList() {       KOMMENTERT UT METODE
        categoryBar.clear();
        categCreatorList.getItems().clear();
        for(String category : categoryCreator){
            categCreatorList.getItems().add(createRemovable(category, removeCategoryList(category)));
        }
    }*/

    //partial functions for adding steps, ingredients and categories to recipe. shall be run in addRecipe()
    public void addCategoriesToRecipe(Recipe recipe){
        //book.categCollect();
        //loop through items in list

        for(String category : categoryCreator){
            boolean categoryExists = false;
            
            for(Category c : book.getCategories()){

                if(c.getName().equals(category)){
                    categoryExists = true;
                    recipe.addCategory(c);
                }
            }

            if(!categoryExists){
                recipe.addCategory(new Category(category));
            }
            //if the step is already in the list, do not add it again
        }
        categoryCreator.clear(); //clear the list for next use
        categCreatorList.getItems().clear(); //clear the list for next use

        System.out.println(book.getCategories());
    }

    //-----------------------------------------------------------
    //*CATEGORY FUNCTIONALITY
    //-----------------------------------------------------------
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
    //*FRIDGE FUNCTIONALITY
    //---------------------------------------------------
    private Pane foodComponent(String food, String amount, String unit){
        Pane foodBody = new Pane();

        //adds children
        foodBody.getChildren().add(createFoodLabel(food, amount, unit));
        foodBody.getChildren().add(createDeleteX(food));

        return foodBody;
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

    //remove food from fridge
    public void removeFood() {
        System.out.println("Remove food button was clicked");
    }

    //---------------------------------------------------
    //*FILE WRITING FUNCTIONALITY
    //---------------------------------------------------

    //------------------------------------------------
    //*RECIPE SEARCH FUNCTIONALITY
    //------------------------------------------------

    //search for food
    public void searchFood() { // !rart navn på metode darkus??
        System.out.println("Search food bar was used was clicked");
        searchedRecipes = book.searchRecipes(searchBar.getText());
        if(searchedRecipes.size() > 0){
            recipeList.getItems().clear();
            for(Recipe r : searchedRecipes){
                recipeList.getItems().add(recipeComponent(r));
            }
        }
        updateAmountLabel(searchedRecipes);
    }

    //filter recipes with categories
    public void filterRecipes(ArrayList<Category> categoriesClicked) {
        recipeList.getItems().clear();
        if(categoriesClicked.size() == 0){
            initRecipeComponents();
            return;
        }

        ArrayList<Recipe> sortedRecipes = book.getSortedRecipesAllCategories(categoriesClicked);
        if(sortedRecipes.size() == 0){
        }

        for(Recipe r : sortedRecipes){
            recipeList.getItems().add(recipeComponent(r));
        }
        updateAmountLabel(sortedRecipes);
    }

    public void fridgeAddFood() {
        System.out.println("add food button");

        if(fridgeNameInput.getText().length() == 0 || fridgeAmountInput.getText().length() == 0){

            System.out.println("Not all textfields filled in");
            return;
        }

        if(fridge.isFoodInFridge(fridgeNameInput.getText())){
            System.out.println("That type of food is already in your fridge");
            return;
        }

        fridge.addFood(fridgeNameInput.getText(), Double.parseDouble(fridgeAmountInput.getText()), unitComboBoxFridge.getValue());
        
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


    ! om det er egg i kjøleskapet, hvorfor kan man ikke legge til flere egg med samme enhet?
    ! legge til ny oppskrift sørger for duplikate categories
    ! fix load book
    ! fix save book
    ! skalering av oppskrifts, funksjon
    ! fridge filter av og på funksjon (i det hele tatt funksjon)

    !fil skriving og lesing
    !få fridge fungere
    !edit recipe funksjon (kan vurdere)

    ? bruke predicates for å filtrere etter ingredienser i fridge tool. feks et predicate som sier at oppskriften kan lages av maten i fridge
    ? hvordan skiller vi kategorier
    ? bruke predicates for å filtrere etter kategorier
    ? legge edit og lage recipe tool i et popupvindu?
    ? picture support
    ? hvem skriver dokumentasjonen
    ? vise til studass en stund før fristen for å finne ut av potensielle endringer litt tidlig.


    MARKUS HAR GJORT:
    * gått over privacy declarators i Recipe klassen
    * gått over privacy declarators i book klassen
    * gått over privacy declarators i Category klassen
    * gått over privacy declarators i Fridge klassen
    * fikset oppdatering av antall recipes label
    * fikset convertering fra grid til list for recipes
    * fikset skjuling av nummereringslabel i recipe list
    * fikk kvittet meg med tretti linjer kode. lagde viewlabel component
    * fikset litt styling 60/30/10 regel og darkmode
    * fjernet nivå 2 panes
    * la recipe ta inn 'æøå som symboler i navns
    * fix remove recipe function
    * sette opp dropdown menyer for enheter i Fridge og Recipe
    * fikk dropdown menyer for enheter i fridge til å fungere
    * fikset listviews i recipe view mode
    * satte opp listviews i recipe creator
    * koblet opp steps med recipe i creator
    * koblet opp ingredients med recipe i creator
    * koblet opp categories med recipe i creator


    JULIAN HAR GJORT: 
    * add fridge 
    * fix remove food function
    * add category components with filters, use checkbox because that are standard

    * sandra
    * mora si
    * donald trump
    * billy
    * faren til billy
    * munken 
    
    */
} 