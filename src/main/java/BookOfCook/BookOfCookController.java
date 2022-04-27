package BookOfCook;

import java.io.File;
import javafx.fxml.FXML;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BookOfCookController extends Validator{
    private Cookbook book;
    private Fridge fridge;
    private ArrayList<Category> categoriesClicked = new ArrayList<Category>();
    private FileHandler fileHandler;
    private ArrayList<String> stepsCreator, categoryCreator, fridgeFood;
    private ArrayList<HashMap<String, String>> ingredCreator;
    private FXcomponents fxComponents;

    @FXML
    private Label number, label, recipeAmount;
    @FXML
    private GridPane recipeViewContent, recipeViewBox1, recipeViewBox2;
    @FXML
    private Pane recipeView, recipeStepView;
    @FXML
    private ListView<Pane> fridgeList, categList, categCreatorList, ingredCreatorList, stepCreatorList;
    @FXML
    private ListView<Button> recipeList;
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
        fxComponents = new FXcomponents();
        book.addDummyRecipes();
        fridge.addDummyFood();

        unitComboBoxRecipe.getItems().addAll("stk", "L", "g", "dL", "kg", "cl");
        timeUnitComboBoxRecipe.getItems().addAll("minutes", "hours", "days");
        
        for(Category category : book.getCategories()){          // fyller inn kategorilista
            categList.getItems().add(categComponent(category));
        }  

        stepsCreator = new ArrayList<String>();
        categoryCreator = new ArrayList<String>();
        ingredCreator = new ArrayList<HashMap<String, String>>();

        initFridgeFood();
        updateRecipeList();

        recipeAmount.setText(String.valueOf("Currently showing " + book.getRecipes().size() + "/" + book.getAmount() + " recipes.")); 
    }

    public void checkbox(){                             // sjekker om checkbox er krysset av, og oppdaterer recipesList
        book.setFridgeCheck(fridgeCheckbox.isSelected());
        updateRecipeList();
    }

    //?usikker
    private void initRecipeComponents() {                                                   //inititializes the recipe components in the GUI, from the cookbook
        for (Recipe recipe : book.filter(book.getRecipes(), searchBar.getText(), categoriesClicked, fridge)) {
            recipeList.getItems().add(recipeComponent(recipe));
        }
    }
    
    //?usikker
    private void initFridgeFood(){                              //initializes the food in fridge
        fridgeFood = new ArrayList<String>();
        fridgeFood.addAll(fridge.getFood());
        for(String food : fridgeFood){
            fridgeList.getItems().add(foodComponent(food.toString())); 
        }
    }

    public void updateRecipeList(){
        recipeList.getItems().clear();
        updateCategList();
        initRecipeComponents();
        recipeAmount.setText(String.valueOf("Currently showing " + book.getDisplayedAmount()+ "/" + book.getAmount() + " recipes."));
    }

    private void listUpdater(List<String> array, ListView<Pane> list, TextField...textControl){                   //!bruker varargs for å kunne ta inn flere textfields. HVORFOR? BRUKER JO ALDRI METOEN MED MER ENN ETT TEXTFIELD
        for(TextField text : textControl){                                                                       //for every textcontrol object passed in method
            ((TextInputControl) text).clear();                                                                   //clear the textcontrol
        }
        list.getItems().clear();                                                                                 //clear listview
        for(Object element : array){                                                                             //for every element in array                                   
            Pane pane = new Pane();
            pane.getChildren().add(fxComponents.listLabel(element.toString()));
            pane.getChildren().add(removeList(element.toString(), array));
            list.getItems().add(pane);
        }
    }

    //*RECIPE CREATOR
    public void addRecipe() {
            Recipe recipe = new Recipe(recipeNameBar.getText().toUpperCase(), Integer.parseInt(servesPeopleBar.getText()),  descriptionArea.getText(), prepTimeBar.getText() + " " + timeUnitComboBoxRecipe.getValue(), ingredCreator, book.createNewCategoriesOnly(categoryCreator), stepsCreator);
            book.addRecipe(recipe, caloriesBar.getText());

            categoryCreator.clear();                //clear the list for next use
            categCreatorList.getItems().clear();    //clear the list for next use
            ingredCreator.clear();                  //clear the list for next use
            ingredCreatorList.getItems().clear();   //clear the list for next use
            stepsCreator.clear();                   //clear the list for next use
            stepCreatorList.getItems().clear();    //clear the list for next use

            updateRecipeList();             
            updateCategList();
    }

    //?
    public Button removeCategoryList(String target){
        Button btn = fxComponents.xButton();
        btn.setOnAction(e -> {
            categoryCreator.remove(target);
            listUpdater(categoryCreator, categCreatorList, categoryBar);;
        });
        return btn;
    }

    public Button removeIngredientList(HashMap<String, String> target){
        Button btn = fxComponents.xButton();
        btn.setOnAction(e -> {
            ingredCreator.remove(target); 
            updateingredCreatorList();    
        });   
        return btn;
    }

    private void updateingredCreatorList() {
        List<String> ingredients = ingredCreator.stream().map(object -> object.get("name") + " " + object.get("amount") + object.get("unit")).collect(Collectors.toList()); 
        listUpdater(ingredients, ingredCreatorList, ingredNameBar, ingredAmountBar);                                                                                       
    }

    //!nesten bare backend
    public void addStepCreator(){                   //create step object with name from textfield, then add step to list in creator
        if(!(stringExistsArray(stepsField.getText(), stepsCreator))){
            nullOrEmpty(stepsField);
            stepsCreator.add(stepsField.getText());
            listUpdater(stepsCreator, stepCreatorList, stepsField);;//adding category to recipe, must happen through adding recipe function. cause that confirms that the categories in list is correct
        }
    }

    //!nesten bare backend
    public void addIngredientCreator(){
        nullOrEmpty(ingredNameBar.getText());
        nullOrEmpty(ingredAmountBar.getText());
        nullOrEmpty(unitComboBoxRecipe.getValue());

        HashMap<String, String> ingredient = new HashMap<String,String>();
        ingredient.put("name", ingredNameBar.getText());
        ingredient.put("amount", ingredAmountBar.getText());
        ingredient.put("unit", unitComboBoxRecipe.getValue());

        ingredCreator.add(ingredient); 
        updateingredCreatorList();
    }

    //!nesten bare backend
    public void addCategoryCreator(){//create category object with name from textfield, then add category to list in creator
        String categoryName = categoryBar.getText();
        if(!(stringExistsArray(categoryName, categoryCreator))){
            nullOrEmpty(categoryName);
            categoryCreator.add(categoryName);
            listUpdater(categoryCreator, categCreatorList, categoryBar);;//adding category to recipe, must happen through adding recipe function. cause that confirms that the categories in list is correct
        }
    }

    //*CATEGORIES
    private Pane categComponent(Category category){
        Pane body = new Pane(); //creates pane for each category
        CheckBox checkbox = new CheckBox(category.getName()); //creates a checkbox with category name       
        checkbox.setOnAction(e -> {//on checkbox click
            if(checkbox.isSelected()){
                categoriesClicked.add(category);
            }else{
                categoriesClicked.remove(category);
            }
            recipeList.getItems().clear();
            initRecipeComponents();
            //filterRecipes(categoriesClicked);
        });
        body.getChildren().add(checkbox);//adds children
        fxComponents.styleRegion(body, "category-body", Double.MAX_VALUE, Double.MAX_VALUE);
        return body;
    }
    
    //?passer denne inn i noen shorthands
    private void updateCategList(){
        categList.getItems().clear();
        for(Category category : book.getCategories()){                             
            categList.getItems().add(categComponent(category));
        }
    }

    //*FRIDGE
    private Pane foodComponent(String food){
        Pane foodBody = new Pane();
        Button btn = fxComponents.xButton();

        btn.setOnAction(e -> {
            fridge.removeFood(food);
            updatefridge(); 
        });

        foodBody.getChildren().add(fxComponents.listLabel(food));
        foodBody.getChildren().add(btn);
        return foodBody;
    }

    public void fridgeAddFood() {
        fridge.addFood(fridgeNameInput.getText());
        updatefridge();
    }

    //?usikker kan listupdater kjøres her? den er ganske kort, så lav prioritering.
    public void updatefridge(){
        fridgeList.getItems().clear();
        initFridgeFood();
        updateRecipeList();
    }

    //*RECIPE DISPLAY
    private Button recipeComponent(Recipe recipe){
        Button recipeBtn = new Button(recipe.getName());//create button object
        fxComponents.styleRegion(recipeBtn, "recipeBtn", Double.MAX_VALUE, Double.MAX_VALUE);
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
        fxComponents.styleRegion(recipeView, "recipe-view", Double.MAX_VALUE, Double.MAX_VALUE);
        
        fxComponents.viewLabel(recipe.getName(), recipeViewBox1, 0, 0);
        fxComponents.viewLabel(recipe.getDesc(), recipeViewBox1, 1, 0); 
        fxComponents.viewLabel("Serves: " + recipe.getServings(), recipeViewBox1, 2, 0); 
        fxComponents.viewLabel("Prep time: " + recipe.getPrepTime(), recipeViewBox1, 3, 0);
        fxComponents.viewLabel("Calories: " + recipe.getCal(), recipeViewBox1, 4, 0);

        List<String> categories = recipe.getCategories().stream().map(object -> Objects.toString(object, null)).collect(Collectors.toList());
        List<String> steps = recipe.getSteps();
        List<String> Ingredients = recipe.getIngredients().stream().map(object -> object.get("name") + " " + object.get("amount") + object.get("unit")).collect(Collectors.toList());

        fxComponents.viewList(0, 0, 1, 0, "Categories", categories, recipeViewBox2);
        fxComponents.viewList(4, 0, 5, 0, "steps", steps, recipeViewBox2);
        fxComponents.viewList(2, 0, 3, 0, "Ingredients", Ingredients, recipeViewBox2);

        closeBtn();
        removeBtn(recipe);
    }

    //?usikker
    public void closeRecipeView() {//close recipeView
        recipeView.setVisible(false);
        recipeViewBox1.getChildren().clear();   //løser for  column 1 ved å tømme grid når den stenger og så må rekonstruere
        recipeViewBox2.getChildren().clear();   //løser for column 2 ved å tømme grid når den stenger og så må rekonstruere
        recipeList.setVisible(true);
        recipeAmount.setVisible(true);
    }

    //?
    private void closeBtn(){                            //creates a close Btn for closing recipe view
        Button btn = new Button("Close");               //creates button object
        fxComponents.styleNode(btn, "standard-button", 80.0, 170.0);
        btn.setOnAction(e -> {
            closeRecipeView();
        });
        recipeViewContent.add(btn, 0, 4);               //adds to grid
        // viewBtn("Close", () -> closeRecipeView(), 0, 4);
    }

    //?
    private void removeBtn(Recipe recipe){                  //creates a remove Btn in recipe view, for removing the recipe from the cookbook
        Button btn = new Button("Remove");
        fxComponents.styleNode(btn, "standard-button", 80.0, 170.0);
        btn.setOnAction(e -> {
            book.removeRecipe(recipe);
            updateRecipeList();
            closeRecipeView();
        });
        recipeViewContent.add(btn, 1, 4);
    }

    //*FILBEHANDLING
    public void load() {        
        FileChooser fileChooser = fxComponents.csvFileChooser("Load cookbook");
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage); 
        book = fileHandler.load(file);                              //if the user chooses a file, initialize the cookbook
        updateRecipeList();
        updateCategList();
    }

    public void save() {                                            //saves the cookbook as a csv file
        FileChooser fileChooser = fxComponents.csvFileChooser("Save cookbook");                 
        Stage stage = new Stage();                                  //creates a new stage 
        File file = fileChooser.showSaveDialog(stage);              //shows the filechooser window and sets the file to the file the user chooses
        fileHandler.save(file, book);                               //saves the cookbook as a csv file
    }

    public Button removeList(String target, List<String> listView){
        Button btn = fxComponents.xButton();

        btn.setOnAction(e -> {
            System.out.println("fjerner fra list rcreator");
            listView.remove(target);
            System.out.println(listView);
            listUpdater(categoryCreator, categCreatorList, categoryBar);;
            listUpdater(stepsCreator, stepCreatorList, stepsField);
            listUpdater(ingredCreator, ingredCreatorList, stepsField);
            updateingredCreatorList();  //!fungerer ikke ordentlig
        }); 
        return btn;
    }
}
/*
!KJENTE BUGS
!amount label er ikke oppdatert
!man kan legge inn verdier null i creator lister
!du kan skrive inn bokstaver i amount i fridge, men vi skal vel muligens fjerne amount og unit, da fridge går utifra navn?
!Type safety, sjekk om listviews tar inn panes eller Strings, og parametiser
!fridge fungerer ikke helt

?FORSLAG: 
?få til møte med studass på hvordan flytte mer frontend til backend. 
?kan man lage backend som tar seg av mye av javaFX dynamikken?
*/