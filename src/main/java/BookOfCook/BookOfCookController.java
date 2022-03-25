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

public class BookOfCookController {
    private Cookbook book;
    private ArrayList<Recipe> recipes, searchedRecipes;
    private ArrayList<HashMap<String, Object>> fridgeFood;
    private ArrayList<Category> categories;
    private Fridge fridge;
    private int numbersOfRecipesShown;

    //*FXML-noder
    @FXML
    private Label number, label, recipeAmount;  

    @FXML
    private GridPane recipeViewContent, recipeViewBox1, recipeViewBox2;

    @FXML
    private Pane recipeView, recipeStepView;

    @FXML
    private ListView fridgeList, categList, recipeList;

    @FXML
    private TextField recipeNameBar, servesPeopleBar, prepTimeBar, categoryBar, caloriesBar, ingredientNameBar, amountBar, searchBar, fridgeNameInput, fridgeAmountInput, fridgeUnitInput;

    @FXML
    private TextArea descriptionArea, stepsArea;


    //*INITIALIZATION
    public void initialize(){
        //! fungerer ikke searchBar.requestFocus();
        initbook();
        initRecipeList();
        initFridge();
        initCateg();
        initFridgeFood();
    }

    private void initRecipeList() {
        initRecipes();
        for (Recipe recipe : recipes) {
            Recipe r = recipe; //copy recipe
            recipeList.getItems().add(recipeComponent(r));
        }
        updateAmount();
    }

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
        // book.addRecipe(new Recipe("Pinnekjøtt", 1)); //!Ikke lov med æøå
        book.addRecipe(new Recipe("lasagne", 1));
        book.addRecipe(new Recipe("croissant", 1));
        book.addRecipe(new Recipe("french toast", 1));
        book.addRecipe(new Recipe("bacon and eggs", 1));
        book.addRecipe(new Recipe("Spam", 1));
        book.addRecipe(new Recipe("tomatsuppe", 1));
        book.addRecipe(new Recipe("pulled Pork", 1));
        book.addRecipe(new Recipe("Tacos", 1));
        book.addRecipe(new Recipe("shepeards pie", 1));
        book.addRecipe(new Recipe("kylling taco", 1));
        book.addRecipe(new Recipe("quesedillas", 1));
        book.addRecipe(new Recipe("fajitas", 1));
        book.addRecipe(new Recipe("vegetarburger", 1));
        book.addRecipe(new Recipe("omelett", 1));
    }

    private void initRecipes(){
        recipes = new ArrayList<Recipe>();
        recipes.addAll(book.getRecipes());
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

        book.categCollect();

        //recipes.addAll(book.getRecipes());
    }
    
    private void initCateg(){

        categories = new ArrayList<Category>();
        book.categCollect();

        categories.addAll(book.getCategories());

        //System.out.println(book.getCategories());
        for(Category category : categories){
            categList.getItems().add(categComponent(category));
        }
    }
    
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
    
    private void initFridgeFood(){
        fridgeFood = new ArrayList<HashMap<String, Object>>();
        fridgeFood.addAll(fridge.getFood());

        for(HashMap<String, Object> food : fridgeFood){
            fridgeList.getItems().add(foodComponent(food.get("name").toString(), food.get("amount").toString(), food.get("unit").toString()));
        }
    }
    

    //*UPDATERS
    public void updateRecipeList(){
        recipeList.getItems().clear();
        updateCategList();
        initRecipeList();
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


    //*RECIPE LIST
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

    //*CATEGORY COMPONENTS
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
            //?start filter();
        });

        return checkbox;
    }

    //*DYNAMIC CREATION OF FRIDGE COMPONENTS
    private Pane foodComponent(String food, String amount, String unit){
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

    public void initViewContent(Recipe recipe){
        // createStepsLabel(recipe);       //! fungerer ikke optimalt

        //column 1
        viewLabel(recipe.getDisplayedName(), recipeViewBox1, 0, 0);
        viewLabel("Categories:" + recipe.getCategories(), recipeViewBox1, 0, 1);
        viewLabel(recipe.getDescription(), recipeViewBox1, 0, 2);       
        viewLabel("Prep time: " + recipe.getPrepTime(), recipeViewBox1, 0, 3);
        viewLabel("Calories: " + recipe.getCalories(), recipeViewBox1, 0, 5);

        //column 2
        viewLabel("Serves: " + recipe.getNumberOfServings(), recipeViewBox2, 0, 0); 
        createIngredientsLabel(recipe); //! fungerer ikke

        //btns
        addCloseButton(recipe);
        addRemoveBtn(recipe);
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
            System.out.println("Close recipe view for: " + recipe.getName());
            closeRecipeView();
        });

        //adds to grid
        recipeViewContent.add(closeButton, 0, 4);
    }

    private void addRemoveBtn(Recipe recipe){
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
        recipeViewContent.add(removeButton, 1, 3);
    }


    //second column in recipeview
    private void stepsLabel(Recipe recipe){
        Label label = new Label("Steps:" + recipe.getSteps());
        styleLabel(label, "recipe-view-text", 80.0, 10.0);
        // recipeStepView.getChildren().clear();
        recipeStepView.getChildren().add(label);
    }

    private void createIngredientsLabel(Recipe recipe){
        Label label = new Label("Ingredients:");
        styleLabel(label, "recipe-view-text", 80.0, 10.0);
        recipeViewBox2.add(label, 0, 1);
    }

    private void viewLabel(String content, Object parent, int row, int column){
        Label label = new Label(content);
        styleLabel(label, "recipe-view-text", 80.0, 10.0);
        ((GridPane)parent).add(label, column, row);
    }

    //close recipeView
    public void closeRecipeView() {
        recipeView.setVisible(false);
        recipeViewBox1.getChildren().clear();   //løser for  column 1 ved å tømme grid når den stenger og så må rekonstruere
        recipeViewBox2.getChildren().clear();   //løser for column 2 ved å tømme grid når den stenger og så må rekonstruere
        recipeList.setVisible(true);
        recipeAmount.setVisible(true);
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
        book.addRecipe(recipe);
        updateRecipeList();
    }

    //view recipe
    public void viewRecipe() {
        System.out.println("View button was clicked");
    }

    //remove food from fridge
    public void removeFood() {
        System.out.println("Remove food button was clicked");
    }

    //remove recipe
    public void removeRecipe(Recipe recipe){
        System.out.println("Remove recipe button was clicked");
        book.removeRecipe(recipe);
        updateRecipeList();
        closeRecipeView();
    }

    //search for food
    public void searchFood() {
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
    ? bruke predicates for å filtrere etter ingredienser i fridge tool. feks et predicate som sier at oppskriften kan lages av maten i fridge

    category
    ! add category components with filters, use checkbox because that are standard
    ! legge til ny oppskrift sørger for duplikate categories
    ? hvordan skiller vi kategorier
    ? bruke predicates for å filtrere etter kategorier

    skriving til fil
    ! fix load book function
    ! fix save book 
    
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
    ? knapp for å fjerne all text fra search field?


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


    JULIAN HAR GJORT: 
    * add fridge 
    * fix remove food function
    * sandra
    * mora si
    * donald trump
    * billy
    * faren til billy
    * munken
    
    */
} 