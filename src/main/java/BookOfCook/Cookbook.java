
package BookOfCook;

import java.util.*;

public class Cookbook extends Validator {
    private int recipeAmount;
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private ArrayList<Category> categories = new ArrayList<Category>();

    public Cookbook() {
        this.recipeAmount = 0;  // amount of recipes in cookbook is always 0 at start
    }

    //*add and remove recipes to/from cookbook
    public void addRecipe(Recipe recipe) {
        duplicateRecipeName(recipes, recipe);   // checks for recipes with duplicate names
        recipes.add(recipe);                    // adds recipe to cookbook
        recipeAmount++;                         // updates amount of recipes in cookbook
    }

    public void removeRecipe(Recipe recipe) {   // removes recipe from cookbook and updates recipe amount
        recipeExists(recipes, recipe);          //? er denne nødvendig med tanke på hvoradn grensesnittet er satt opp?  if recipe exists
        recipes.remove(recipe);                 // removes recipe from cookbook
        recipeAmount--;                         // updates amount of recipes in cookbook
    }

    //*getter methods
    public int getAmount() {
        return recipeAmount;    // returns amount of recipes in cookbook
    }

    public ArrayList<Recipe> getRecipes() {
        return new ArrayList<Recipe>(recipes);  // returns all recipes in cookbook     
    }

    public ArrayList<Category> getCategories() {
        return new ArrayList<Category>(categories); // returns categories in cookbook
    }

    public ArrayList<Recipe> filterByCategories(ArrayList<Category> categories){    // metode som returnerer alle recipes som inneholder alle kategoriene
        ArrayList<Recipe> sortedRecipes = new ArrayList<>();                        // create an output arraylist
        for (Recipe recipe : recipes) {                                             // loops through all recipes in cookbook
            boolean containsAllCategories = true;                                   // sets temporary variable to true
            for (Category category : categories) {                                  // loops through all categories
                if(!recipe.getCategories().contains(category)){                     // checks if recipe does not contain this category
                    containsAllCategories = false;                                  // if: set false
                }
            }
            if(containsAllCategories){
                sortedRecipes.add(recipe);                                          // if still true, add recipe to output array
            }
        }
        System.out.println(sortedRecipes);
        return sortedRecipes;
    }

    public void collectCategories(){
        ArrayList<Category> collectedCategories = new ArrayList<>();    // create an output arraylist
        for (Recipe recipe : recipes) {                                 // lopps through all recipes
            for (Category category : recipe.getCategories()) {          // loops through all categories in recipe
                if(!collectedCategories.contains(category)){            // if not category already collected
                    collectedCategories.add(category);                  // collect category
                    //System.out.println("collected " + category);
                }
            }
        }
        categories = collectedCategories;
    }

    //*Searchbar
    public ArrayList<Recipe> searchRecipes(String searchString, ArrayList<Recipe> filterTarget) {   // searches for recipes in cookbook with name containing searchString:
        ArrayList<Recipe> searchResults = new ArrayList<>();                                        // initializes an output arraylist
        for (Recipe recipe : filterTarget) {                                                        // loops through all recipes
            if (recipe.getName().toLowerCase().contains(searchString.toLowerCase())) {              // if recipe name contains searchString
                searchResults.add(recipe);                                                          // add recipe to output arraylist
            }
        }
        return searchResults;                                                                       // return output arraylist
    }

    //*Cookbook filtration
    public ArrayList<Recipe> filter(ArrayList<Recipe> recipes, String searchText/*, ArrayList<Category> categories*/, Fridge fridge){
        ArrayList<Recipe> filteredRecipes = new ArrayList<>();

        if(true){ // KJØLESKAP
            filteredRecipes = fridge.filter(recipes);
        }

        if(true){ // KATEGORIER
            // tar inn den enda mindre filteredrecipes
            //resultatet blir en enda enda mindre filteredRecipes
        }

        if(true){ // SØKE
            //tar inn den enda mindre filteredrecipes
            //resultatet blir en enda enda mindre filteredRecipes
            filteredRecipes = searchRecipes(searchText, filteredRecipes);
        }

        return filteredRecipes;                         //returnerer filtrasjonsproduktet
    }
}