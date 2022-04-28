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

public class BookOfCookController extends FXcomponents{
    private Cookbook book;
    private Fridge fridge;
    private ArrayList<Category> categoriesClicked = new ArrayList<Category>();
    private ArrayList<String> stepsCreator, categoryCreator, fridgeFood;
    private ArrayList<HashMap<String, String>> ingredCreator;
    private FileHandler fileHandler;
    private List<String> ingredients;

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
        ingredients = new ArrayList<String>();
        book.addDummyRecipes();
        fridge.addDummyFood();

        unitComboBoxRecipe.getItems().addAll("stk", "L", "g", "dL", "kg", "cl");
        timeUnitComboBoxRecipe.getItems().addAll("minutes", "hours", "days");
        stepsCreator = new ArrayList<String>();
        categoryCreator = new ArrayList<String>();
        ingredCreator = new ArrayList<HashMap<String, String>>();

        updateCategList(); // fyller inn kategorilista 
        initFridgeFood();
        updateRecipeList();
    }

    public void checkbox(){                                                                                     // sjekker om checkbox er krysset av, og oppdaterer recipesList
        book.setFridgeCheck(fridgeCheckbox.isSelected());
        updateRecipeList();
    }

    private void initRecipeComponents() {                                                                       //inititializes the recipe components in the GUI, from the cookbook
        for (Recipe recipe : book.filter(book.getRecipes(), searchBar.getText(), categoriesClicked, fridge)) {
            recipeList.getItems().add(recipeComponent(recipe));
        }
    }
    
    private void initFridgeFood(){                                                                              //initializes the food in fridge
        fridgeFood = new ArrayList<String>();
        fridgeFood.addAll(fridge.getFood());
        for(String food : fridgeFood){
            fridgeList.getItems().add(foodComponent(food.toString())); 
        }
    }

    public void updateRecipeList(){
        recipeList.getItems().clear();
        initRecipeComponents();
        recipeAmount.setText(String.valueOf("Currently showing " + book.getDisplayedAmount()+ "/" + book.getAmount() + " recipes"));
    }

    private void listUpdater(List<String> array ,ListView<Pane> list, boolean ingredMode, TextField...textControl){         
        for(TextField text : textControl){                                                                       //for every textcontrol object passed in method
            ((TextInputControl) text).clear();                                                                   //tømmer felter i recipefiels                                                           //clear the textcontrol
        }
        list.getItems().clear();                                                                                 //clear listview
        for(Object element : array){                                                                             //for every element in array                                   
            Pane pane = new Pane();
            pane.getChildren().add(listLabel(element.toString()));
            pane.getChildren().add(removeList(element.toString(), array, ingredMode));                                
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
        stepCreatorList.getItems().clear();     //clear the list for next use

        updateRecipeList();
        updateCategList();
    }

    private void updateIngredCreatorList() {
        ingredients = ingredCreator.stream().map(object -> object.get("name") + " " + object.get("amount") + " " + object.get("unit")).collect(Collectors.toList()); 
        listUpdater(ingredients, ingredCreatorList, true, ingredNameBar, ingredAmountBar);     
    }

    public void addStepCreator(){                   //create step object with name from textfield, then add step to list in creator
        validateTextField('a',stepsCreator, null, stepsField);
        stepsCreator.add(stepsField.getText());
        listUpdater(stepsCreator, stepCreatorList, false, stepsField);;//adding category to recipe, must happen through adding recipe function. cause that confirms that the categories in list is correct
    }

    public void addIngredientCreator(){
        validateTextField('b', null, unitComboBoxRecipe, ingredNameBar, ingredAmountBar);
        ingredCreator.add(createIngredientFromTextFields( ingredNameBar.getText(), ingredAmountBar.getText(), unitComboBoxRecipe.getValue()));
        updateIngredCreatorList();
    }

    public void addCategoryCreator(){//create category object with name from textfield, then add category to list in creator
        validateTextField('a', categoryCreator, null, categoryBar);
        categoryCreator.add(categoryBar.getText());
        listUpdater(categoryCreator, categCreatorList, false, categoryBar);;//adding category to recipe, must happen through adding recipe function. cause that confirms that the categories in list is correct
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
            updateRecipeList();
        });
        body.getChildren().add(checkbox);//adds children
        styleRegion(body, "category-body", Double.MAX_VALUE, Double.MAX_VALUE);
        return body;
    }
    
    private void updateCategList(){
        categList.getItems().clear();
        for(Category category : book.getCategories()){                             
            categList.getItems().add(categComponent(category));
        }
    }

    //*FRIDGE
    private Pane foodComponent(String food){
        Pane foodBody = new Pane();
        Button btn = xButton();

        btn.setOnAction(e -> {
            fridge.removeFood(food);
            updatefridge(); 
        });

        foodBody.getChildren().add(listLabel(food));
        foodBody.getChildren().add(btn);
        return foodBody;
    }

    public void fridgeAddFood() {
        validateTextField('c',fridge.getFood() , null, fridgeNameInput);
        fridge.addFood(fridgeNameInput.getText());
        updatefridge();
    }

    public void updatefridge(){
        fridgeList.getItems().clear();
        initFridgeFood();
        updateRecipeList();
    }

    //*RECIPE DISPLAY
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
        
        viewLabel(recipe.getName(), recipeViewBox1, 0, 0);
        viewLabel(recipe.getDesc(), recipeViewBox1, 1, 0); 
        viewLabel("Serves: " + recipe.getServings(), recipeViewBox1, 2, 0); 
        viewLabel("Prep time: " + recipe.getPrepTime(), recipeViewBox1, 3, 0);
        viewLabel("Calories: " + recipe.getCal(), recipeViewBox1, 4, 0);

        List<String> categories = recipe.getCategories().stream().map(object -> Objects.toString(object, null)).collect(Collectors.toList());
        List<String> steps = recipe.getSteps();
        List<String> Ingredients = recipe.getIngredients().stream().map(object -> object.get("name") + " " + object.get("amount") + object.get("unit")).collect(Collectors.toList());

        viewList(0, 0, 1, 0, "Categories", categories, recipeViewBox2);
        viewList(4, 0, 5, 0, "steps", steps, recipeViewBox2);
        viewList(2, 0, 3, 0, "Ingredients", Ingredients, recipeViewBox2);

        closeBtn();
        removeBtn(recipe);
    }

    public void closeRecipeView() {//close recipeView
        recipeView.setVisible(false);
        recipeViewBox1.getChildren().clear();   //løser for  column 1 ved å tømme grid når den stenger og så må rekonstruere
        recipeViewBox2.getChildren().clear();   //løser for column 2 ved å tømme grid når den stenger og så må rekonstruere
        recipeList.setVisible(true);
        recipeAmount.setVisible(true);
    }

    private void closeBtn(){                            //creates a close Btn for closing recipe view
        Button btn = new Button("Close");               //creates button object
        styleNode(btn, "standard-button", 80.0, 170.0);
        btn.setOnAction(e -> {
            closeRecipeView();
        });
        recipeViewContent.add(btn, 0, 4);               //adds to grid
    }

    private void removeBtn(Recipe recipe){                  //creates a remove Btn in recipe view, for removing the recipe from the cookbook
        Button btn = new Button("Remove");
        styleNode(btn, "standard-button", 80.0, 170.0);
        btn.setOnAction(e -> {
            book.removeRecipe(recipe);
            updateRecipeList();
            updateCategList();
            closeRecipeView();
        });
        recipeViewContent.add(btn, 1, 4);
    }

    //*FILBEHANDLING
    public void load() {        
        FileChooser fileChooser = csvFileChooser("Load cookbook");
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage); 
        book = fileHandler.load(file);                              //if the user chooses a file, initialize the cookbook
        updateRecipeList();
        updateCategList();
    }

    public void save() {                                            //saves the cookbook as a csv file
        FileChooser fileChooser = csvFileChooser("Save cookbook");                 
        Stage stage = new Stage();                                  //creates a new stage 
        File file = fileChooser.showSaveDialog(stage);              //shows the filechooser window and sets the file to the file the user chooses
        fileHandler.save(file, book);                               //saves the cookbook as a csv file
    }

    public Button removeList(String target, List<String> listView, boolean ingredMode){
        Button btn = xButton();                        
        btn.setOnAction(e -> {
            System.out.println("fjerner fra list rcreator");

            if(ingredMode){
                ingredCreator.remove(convertStringToHashMap(target));
            }else{
                listView.remove(target);
            }

            listUpdater(categoryCreator, categCreatorList, false, categoryBar);
            listUpdater(stepsCreator, stepCreatorList, false, stepsField);
            updateIngredCreatorList();                                  
        }); 
        return btn;
    }
}
