package BookOfCook;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle.Control;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
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
    private TextField recipeNameBar, stepsField, servesPeopleBar, prepTimeBar, categoryBar, caloriesBar, ingredNameBar, ingredAmountBar, searchBar, fridgeNameInput, fridgeAmountInput, fridgeUnitInput;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ComboBox<String> unitComboBoxRecipe, unitComboBoxFridge, timeUnitComboBoxRecipe;
    @FXML
    private CheckBox fridgeCheckbox;

    public void initialize(){//initializes the controller
        book = new Cookbook();
        fridge = new Fridge();        
        fileHandler = new FileHandler(); // initialize filehandler
        book.addDummy();

        unitComboBoxRecipe.getItems().addAll("stk", "L", "g", "dL", "kg", "cl");
        unitComboBoxFridge.getItems().addAll("stk", "L", "g", "dL", "kg", "cl");
        timeUnitComboBoxRecipe.getItems().addAll("minutes", "hours", "days");
        
        for(Category category : book.getCategories()){          // fyller inn kategorilista
            categList.getItems().add(categComponent(category));
        }  

        stepsCreator = new ArrayList<String>();
        categoryCreator = new ArrayList<String>();
        IngredCreator = new ArrayList<HashMap<String, String>>();

        initFridgeFood();
        initRecipeComponents();

        recipeAmount.setText(String.valueOf("Currently showing " + book.getRecipes().size() + "/" + book.getAmount() + " recipes.")); 
    }

    //?usikker
    private void initRecipeComponents() {                                                   //inititializes the recipe components in the GUI, from the cookbook
        for (Recipe recipe : book.getRecipes()/*book.filter(book.getRecipes(), fridge)*/) {
            recipeList.getItems().add(recipeComponent(recipe));
        }
    }
    
    //?usikker
    private void initFridgeFood(){                              //initializes the food in fridge
        fridgeFood = new ArrayList<HashMap<String, String>>();
        fridgeFood.addAll(fridge.getFood());
        for(HashMap<String, String> food : fridgeFood){
            fridgeList.getItems().add(foodComponent(food.get("name").toString(), food.get("amount").toString(), food.get("unit").toString()));
        }
    }
    
    //*godkjent
    public void initViewContent(Recipe recipe){
        viewLabel(recipe.getName(), recipeViewBox1, 0, 0);
        viewLabel(recipe.getDescription(), recipeViewBox1, 1, 0); 
        viewLabel("Serves: " + recipe.getServings(), recipeViewBox1, 2, 0); 
        viewLabel("Prep time: " + recipe.getPrepTime(), recipeViewBox1, 3, 0);
        viewLabel("Calories: " + recipe.getCalories(), recipeViewBox1, 4, 0);

        List<String> categories = recipe.getCategories().stream().map(object -> Objects.toString(object, null)).collect(Collectors.toList());
        List<String> steps = recipe.getSteps();
        List<String> Ingredients = recipe.getIngredients().stream().map(object -> Objects.toString(object, null)).collect(Collectors.toList());
        viewList(0, 0, 1, 0, "Categories", categories);
        viewList(4, 0, 5, 0, "steps", steps);
        viewList(2, 0, 3, 0, "Ingredients", Ingredients);

        //btns
        closeBtn();
        removeBtn(recipe);
    }

    //*godkjent
    public void updateRecipeList(){
        recipeList.getItems().clear();
        updateCategList();
        initRecipeComponents();
    }

    //!nesten helt lik updateCategCreatorList
    private void updateCategList(){
        categList.getItems().clear();
        book.collectCategories();
        for(Category category : book.getCategories()){                                //!init lager, updater skal gjøre klar for ny init ved endring
            categList.getItems().add(categComponent(category));
        }
    }

    //?usikker kan listupdater kjøres her
    public void updatefridge(){
        fridgeList.getItems().clear();
        initFridgeFood();
    }

    //*godkjent
    private void updateIngredCreatorList() {
        List<String> ingredients = IngredCreator.stream().map(object -> Objects.toString(object, null)).collect(Collectors.toList());
        listUpdater(ingredients, ingredCreatorList, ingredNameBar, ingredAmountBar);
    }

    //*godkjent
    private void listUpdater(List<String> array, ListView list, TextField...textControl){                   //bruker varargs for å kunne ta inn flere textfields
        for(TextField text : textControl){                                                                       //for every textcontrol object passed in method
            ((TextInputControl) text).clear();                                                                   //clear the textcontrol
        }
        list.getItems().clear();                                                                                 //clear listview
        for(Object element : array){                                                                             //for every element in array                                   
            list.getItems().add(createRemovable(element.toString(), removeList(element.toString(), array)));     //add element to listview
        }
    }


    //*godkjent
    public void styleNode(Node node, String styleClass, Double x, Double y){
        node.getStyleClass().clear();
        node.getStyleClass().add(styleClass);
        node.setLayoutX(x);
        node.setLayoutY(y);
    }

    //*godkjent
    public void styleRegion(Region region, String styleClass, Double width, Double height){
        region.getStyleClass().clear();
        region.getStyleClass().add(styleClass);
        region.setMaxWidth(width);
        region.setMaxWidth(height);
    }
    
    //*godkjent
    public void removeRecipe(Recipe recipe){
        book.removeRecipe(recipe);
        updateRecipeList();
        closeRecipeView();
    }

    //!REMOVE METHODS trenger generell metode, for å  kutte linjer
    public Button removeCategoryList(String target){
        Button btn = new Button("-");
        styleNode(btn, "standard-button", 10.0, 0.0);
        btn.setOnAction(e -> {
            categoryCreator.remove(target);
            listUpdater(categoryCreator, categCreatorList, categoryBar);;
        });
        return btn;
    }

    //!REMOVE METHODS trenger generell metode, for å  kutte linjer
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
    private void closeBtn(){    //creates a close Btn for closing recipe view
        Button btn = new Button("Close");           //creates button object
        styleNode(btn, "standard-button", 80.0, 170.0);
        btn.setOnAction(e -> {
            closeRecipeView();
        });
        recipeViewContent.add(btn, 0, 4);        //adds to grid

        // viewBtn("Close", () -> closeRecipeView(), 0, 4);
    }

    //*!BUTTONS metodene ligner svært muye på hverandre. lage en generell funksjon?
    private void removeBtn(Recipe recipe){                  //creates a remove Btn in recipe view, for removing the recipe from the cookbook
        Button btn = new Button("Remove");
        styleNode(btn, "standard-button", 80.0, 170.0);
        btn.setOnAction(e -> {
            removeRecipe(recipe);
        });
        recipeViewContent.add(btn, 1, 4);
    }

    //?prøver å lage shorthands for buttons, men sliter med å passe void arguments som funksjoner
    private void viewBtn(String btnLabel, Runnable function, int btnX, int btnY){ 
        Button btn = new Button(btnLabel);
        styleNode(btn, "standard-button", 80.0, 170.0);
        btn.setOnAction(e -> {
            function.run();
        });
        recipeViewContent.add(btn, btnX, btnY);
    }

    //?usikke, må ha mer updaters?
    public void addRecipe() {
        Recipe recipe = new Recipe(recipeNameBar.getText(), Integer.parseInt(servesPeopleBar.getText()),  descriptionArea.getText(), prepTimeBar.getText() + " " + timeUnitComboBoxRecipe.getValue(), IngredCreator, getGategoriesFromCreator(), stepsCreator);
        book.addRecipe(recipe);
        updateRecipeList();
        updateCategList();
        book.collectCategories();
    }

    //!må ha validering her for at disse skal erstatte get
    public void addStepCreator(){                   //create step object with name from textfield, then add step to list in creator
        String stepContent = stepsField.getText();
        stepsCreator.add(stepContent);
        listUpdater(stepsCreator, stepCreatorList, stepsField);                    //adding step to recipe, must happen through adding recipe function. cause that confirms that the steps in list is correct
    }

    //!må ha validering her for at disse skal erstatte get
    public void addIngredientCreator(){
        HashMap<String, String> ingredient = new HashMap<String,String>();
        ingredient.put("name", ingredNameBar.getText());
        ingredient.put("amount", ingredAmountBar.getText());
        ingredient.put("unit", unitComboBoxRecipe.getValue());
        IngredCreator.add(ingredient);
        updateIngredCreatorList();
    }

    //!må ha validering her for at disse skal erstatte get
    public void addCategoryCreator(){//create category object with name from textfield, then add category to list in creator
        String categoryName = categoryBar.getText();
        categoryCreator.add(categoryName);
        listUpdater(categoryCreator, categCreatorList, categoryBar);;//adding category to recipe, must happen through adding recipe function. cause that confirms that the categories in list is correct
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

    //?usiker
    private Pane categComponent(Category category){
        Pane body = new Pane(); //creates pane for each category
        CheckBox checkbox = new CheckBox(category.getName()); //creates a checkbox with category name       
        checkbox.setOnAction(e -> {//on checkbox click
            if(checkbox.isSelected()){
                categoriesClicked.add(category);
            }else{
                categoriesClicked.remove(category);
            }
            filterRecipes(categoriesClicked);
        });

        body.getChildren().add(checkbox);//adds children
        styleRegion(body, "category-body", Double.MAX_VALUE, Double.MAX_VALUE);
        return body;
    }

    //?usikker
    private Pane foodComponent(String food, String amount, String unit){
        Pane foodBody = new Pane();
        Button btn = new Button("X");

        styleNode(btn, "standard-button", 10.0, 0.0);
        btn.setOnAction(e -> {
            fridge.removeFood(food);
            updatefridge();
        });

        Label label = new Label(food + ": " + amount + " " + unit);
        styleNode(label, "list-label", 80.0, 10.0);

        foodBody.getChildren().add(label);
        foodBody.getChildren().add(btn);
        return foodBody;
    }

    //?usikker
    private Button recipeComponent(Recipe recipe){
        Button recipeBtn = new Button(recipe.getName());//create button object
        styleRegion(recipeBtn, "recipeBtn", Double.MAX_VALUE, Double.MAX_VALUE);
        recipeBtn.setOnAction(e -> {//event
            viewRecipe(recipe);
        });
        return recipeBtn;
    }

    //?kan denne forenkles?
    public void viewRecipe(Recipe recipe){//opens recipe view and builds the content
        recipeList.setVisible(false);//hide grid and show recipeview
        recipeAmount.setVisible(false);
        recipeView.setVisible(true);
        styleRegion(recipeView, "recipe-view", Double.MAX_VALUE, Double.MAX_VALUE);
        initViewContent(recipe);//add children
    }

    //?kan denne forenkles?
    public void closeRecipeView() {//close recipeView
        recipeView.setVisible(false);
        recipeViewBox1.getChildren().clear();   //løser for  column 1 ved å tømme grid når den stenger og så må rekonstruere
        recipeViewBox2.getChildren().clear();   //løser for column 2 ved å tømme grid når den stenger og så må rekonstruere
        recipeList.setVisible(true);
        recipeAmount.setVisible(true);
    }
    
    //*godkjent
    private void viewLabel(String content, Object parent, int row, int column){//shorthand method for creating labels in recipe viewmode
        Label label = new Label(content);
        styleNode(label, "recipe-view-text", 80.0, 10.0);
        ((GridPane)parent).add(label, column, row);
    }

    //*godkjent https://stackoverflow.com/questions/4581407/how-can-i-convert-arraylistobject-to-arrayliststring
    public void viewList(int labelX, int labelY, int listX, int listY, String label, List<String> array){//shorthand method for creating list and fill them with ingredients in recipe viewmode
        ListView<String> listView = new ListView<String>();
        viewLabel(label, recipeViewBox2, labelX, labelY);//add steps label to grid
        for(String o : array){//shorthand method for)
            listView.getItems().add(o.toString());
        }
        listView.getStyleClass().add("recipe-view-list");
        recipeViewBox2.add(listView, listY, listX);
    }

    //?Usikker, dersom vi får inkludert ingredients i listUpdater, vil denne funksjonen kun kalles en gang
    public Pane createRemovable(String content, Button btn){
        Pane pane = new Pane();
        Label label = new Label(content);

        styleNode(label, "list-label", 80.0, 10.0); 
        pane.getChildren().add(label);
        pane.getChildren().add(btn);
        return pane;
    }

    //! SKALL VEKK fordi den erstattes av filter, FLYTTE INN I COOKBOOK.JAVA
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

    //! Skal vekk fordi den erstattes av filter FLYTTE INN I COOKBOOK.JAVA
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
        //updateAmountLabel(sortedRecipes);
    }

    //*godkjent
    public void fridgeAddFood() {
        fridge.addFood(fridgeNameInput.getText(), fridgeAmountInput.getText(), unitComboBoxFridge.getValue());
        updatefridge();
    }

    //*Godkjent
    public void load() {        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Cookbook");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage); 
        book = fileHandler.load(file);                              //if the user chooses a file, initialize the cookbook
        
        updateRecipeList();
        updateCategList();
    }

    //*Godkjent
    public void save() {                                            //saves the cookbook as a csv file
        FileChooser fileChooser = new FileChooser();                //creates a filechooser
        fileChooser.setTitle("Save Cookbook");                      //sets the title of the filechooser window
        fileChooser.getExtensionFilters().addAll(                   //adds the extension filter for csv files to the filechooser, such that the user can only save the cookbook as a csv file
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")   //the extension filter is set to csv files
        );                                  
        Stage stage = new Stage();                                  //creates a new stage 
        File file = fileChooser.showSaveDialog(stage);              //shows the filechooser window and sets the file to the file the user chooses
        fileHandler.save(file, book);                               //saves the cookbook as a csv file
    }

    //?usikker, denne kan kanskje forbedres med enda mer generelle klasser
    public Button removeList(String target, List<String> listView){
        Button btn = new Button("X");
        btn.getStyleClass().clear();
        styleNode(btn, "standard-button", 10.0, 0.0);

        btn.setOnAction(e -> {
            listView.remove(target);
            listUpdater(categoryCreator, categCreatorList, categoryBar);;
            listUpdater(stepsCreator, stepCreatorList, stepsField);
        });
        return btn;
    }
}

/*
*GOLFING
*searchbar og category filter erstattes av filter (ca.40 linjer)
*generalisering av components
*generalisering av creator updaters
*totalt

!KJENTE BUGS
!amount label er ikke oppdatert
!man kan legge inn verdier null i creator lister
!du kan skrive inn bokstaver i amount i fridge, men vi skal vel muligens fjerne amount og unit, da fridge går utifra navn?
!lage penere tostring for ingredients
!Type safety, sjekk om listviews tar inn panes eller Strings, og parametiser
!
*/