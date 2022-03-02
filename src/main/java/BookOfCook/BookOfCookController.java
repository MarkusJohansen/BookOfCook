package BookOfCook;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class BookOfCookController {

    private Cookbook cookbook;
    private ArrayList<Recipe> dummyRecipes;

    @FXML
    public TextField newFoodName;

    @FXML
    public Button addToFridge, removeFromFridge, addRecipe, removeRecipe, save, load, editRecipe, viewRecipe, closeRecipe;

    @FXML
    public TextArea fridgeView, caregoryView, recipeView;

    @FXML
    public Pane listRecipeBody;

    //*INITIALIZATION
    public void initialize() {
        initializeDummyRecipes();
        initializeCookBook();
    }

    private void initializeCookBook() {
        cookbook = new Cookbook("Book of Cook");
        for(Recipe recipe : dummyRecipes){
            cookbook.addRecipeToCookbook(recipe);
        }
    }

    private void initializeDummyRecipes(){
        dummyRecipes = new ArrayList<Recipe>();
        dummyRecipes.addAll(List.of(
            new Recipe ("pasta carbonara", 3),
            new Recipe ("pizza", 2),
            new Recipe ("chicken", 1),
            new Recipe ("pork", 1)
        ));
    }

    //*UPDATERS
    private void updateRecipes() {
        for(Recipe recipe : cookbook.getRecipes()){
            addRecipe(recipe);
        }

        /*
        overskriver alle recipes i recipe viewet og skriver den på nytt

        kalles etter at en recipe er lagt til eller fjernet

        Oppgaven er å oppdatere recipe lista med nye recipes.
        */
    }

    private void updateCategories() {
        /*
        skal kjøres når en en recipe er lagt til eller fjernet fra cookbook.
        sjekker de oppskriftene som er lagt til gjennom den nye oppskriften i cookbook.
        ser om de finnes allerede, for da legger den de ikke til.

        enkleste er å skrive alle kategoriene på nytt men det er treigere.

        kalles av public metoder
        addRecipe(Recipe recipe)
        removeRecipe(Recipe recipe)

        oppgaven er å oppdatere kategori displayet
        */
    }

    private void updateFridge() {
        /*
        sletter alt som er i fridge
        skriver den på nytt med endringer som er gjort

        skal kalles fra public metoder som brukes til å endre fridge:
        public void removeFromFridge(Ingredient ingredient)
        public void addToFridge(Ingredient ingredient)'

        oppgaven er å oppdatere fridge displayet

        */

        fridgeView.setText("insane in the brain");
    }

    //*FRIDGE CONTROLS
    public void addToFridge() {
        //add ingredient to fridge
        updateFridge();
    }

    public void removeFromFridge() {
        //remove ingredient from fridge
        updateFridge();
    }


    //*RECIPE CONTROLS
    //adds recipe to cookbook 
    public void addRecipe(Recipe r) {

        //create view button
        createViewButton(r);
        updateRecipes();
        updateCategories();
    }

    public void removeRecipe(Recipe r) {
        //remove recipe from cookbook
        updateRecipes();
        updateCategories();
    }

    public void closeRecipe(Recipe recipe) {
        //close recipe
        updateRecipes();
        updateCategories();
    }

    public void editRecipe(Recipe recipe) {
        //edit recipe
        updateRecipes();
        updateCategories();
    }

    public void viewRecipe(Recipe r) {
        //view recipe
        updateRecipes();
        updateCategories();
    }


    //*RECIPE BUTTON
    //create editButton
    private void createEditButton(Recipe r) {
        Button editButton = new Button("Edit");
        editButton.setOnAction((event) -> {
            editRecipe(r);
        });
    }

    //create deleteButton
    private void createDeleteButton(Recipe r) {
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((event) -> {
            removeRecipe(r);
        });
    }

    //create viewButton
    private void createViewButton(Recipe r) {
        Button viewButton = new Button("View");
        viewButton.setOnAction((event) -> {
            viewRecipe(r);
        });
    }

    //create closeButton
    private void createCloseButton(Recipe r) {
        Button closeButton = new Button("Close");
        closeButton.setOnAction((event) -> {
            closeRecipe(r);
        });
    }


}
