
package BookOfCook;

import java.util.*;
import java.io.*;

public class Cookbook implements recipeContainer {
    private int recipeAmount;
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private ArrayList<Category> categories = new ArrayList<Category>();

    // *CONSTRUCTOR
    public Cookbook() {
        this.recipeAmount = 0;                                                          // amount of recipes in cookbook is always 0 at start
    }

    // *RECIPE ADD AND REMOVE METHODS
    // adds recipe to cookbook
    public void addRecipe(Recipe recipe) {
        duplicateRecipeCheck(recipe);             //! necessay ? checks if recipe already exists
        duplicateRecipeNameCheck(recipe);                                               // checks for recipes with duplicate names
        recipes.add(recipe);                                                            // adds recipe to cookbook
        recipeAmount++;                                                                 // updates amount of recipes in cookbook
    }

    // removes recipe from cookbook and updates recipe amount
    public void removeRecipe(Recipe recipe) {
        checkIfRecipeExists(recipe);                        // checks if recipe exists
        recipes.remove(recipe);                             // removes recipe from cookbook
        recipeAmount--;                                     // updates amount of recipes in cookbook
    }

    // *GETTERS
    // ? er disse nødvendige

    // returns amount of recipes in cookbook
    public int getAmount() {
        return recipeAmount;                                // returns amount of recipes in cookbook
    }

    public ArrayList<Recipe> getRecipes() {
        return new ArrayList<Recipe>(recipes);            // returns all recipes in cookbook     
    }

    public ArrayList<Category> getCategories() {
        return new ArrayList<Category>(categories);                                  // returns categories in cookbook
    }

    public boolean checkIfCategoryExist(String c){
        for(int i = 0; i < categories.size(); i++){
            if(categories.get(i).getName().equals(c)){
                return true;
            }
        }
        return false;
    }

    // *VALIDATION METHODS
    // checks if cookbook contains recipe
    private void duplicateRecipeCheck(Recipe recipe) {
        if (recipes.contains(recipe)) {                                                             // checks if recipe already exists in cookbook
            throw new IllegalArgumentException("Recipe already exists in cookbook");                // describes problem in console and throws ERROR
        }
    }

    // checks if recipe name already exists in cookbook
    private void duplicateRecipeNameCheck(Recipe recipe){
        for (Recipe r : recipes) {                                                                      // loops through recipes in cookbook
            if (r.getName().equals(recipe.getName())) {                                                 // checks there is another recipe with identical name in// cookbook
                throw new IllegalArgumentException("Recipe with same name already exists in cookbook"); // describes problem in console;                             
            }
        }
    }

    private void checkIfRecipeExists(Recipe recipe) {
        if (!recipes.contains(recipe)) {                                                                 // checks if recipe exists in cookbook
            throw new IllegalArgumentException("Recipe does not exist in cookbook");                     // describes problem in console;
        }
    }

    private void nameIsValid(String name) {
        if (name.isEmpty()) {                                                                           // checks if name is empty
            throw new IllegalArgumentException("Name cannot be empty");                                 // describes problem in console;
        }
        // checks if name contains numbers
        if (name.matches(".*\\d+.*")) {                                                                 // checks if name contains numbers
            throw new IllegalArgumentException("Name cannot contain numbers");                           // describes problem in console;
        }

        // checks if name contains special characters
        if (name.matches(".*[^a-zA-Z0-9 ].*")) {                                                       // checks if name contains special characters
            throw new IllegalArgumentException("Name cannot contain special characters");                // describes problem in console;
        }

        // checks if name contains spaces
        if (name.contains(" ")) {                                                                      // checks if name contains spaces
            throw new IllegalArgumentException("Name cannot contain spaces");                           // describes problem in console;
        }

        //checks if name starts or ends with whitespace
        if (name.startsWith(" ") || name.endsWith(" ")) {                                              // checks if name starts or ends with whitespace
            throw new IllegalArgumentException("Name cannot start or end with whitespace");             // describes problem in console;
        }

        //? checks if name is too short
        if (name.length() < 3) {                                                                       // checks if name is too short
            throw new IllegalArgumentException("Name must be at least 3 characters long");              // describes problem in console;
        }
    }

    // metode som returnerer alle recipes som inneholder alle kategoriene
    public ArrayList<Recipe> getSortedRecipesAllCategories(ArrayList<Category> categories){  // categories as parameter
        ArrayList<Recipe> sortedRecipes = new ArrayList<>();                    // create an output arraylist
        for (Recipe recipe : recipes) {                                         // loops through all recipes in cookbook
            boolean containsAllCategories = true;                              // sets temporary variable to true
            for (Category category : categories) {                              // loops through all categories
                if(!recipe.getCategories().contains(category)){                  // checks if recipe does not contain this category
                    containsAllCategories = false;                                // if: set false
                }
            }
            if(containsAllCategories){
                sortedRecipes.add(recipe);                                     // if still true, add recipe to output array
            }
        }
        System.out.println(sortedRecipes);
        return sortedRecipes;
    }

    public void categCollect(){
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

    //*RECIPE SEARCH METHODS
    // searches for recipes in cookbook with name containing searchString:
    public ArrayList<Recipe> searchRecipes(String searchString) {
        ArrayList<Recipe> searchResults = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getName().toLowerCase().contains(searchString.toLowerCase())) {
                searchResults.add(recipe);
            } 
        }
        return searchResults;
    }

    //! TOSTRING METHOD brukes denne
    @Override
    public String toString() {
        return "Cookbook [categories=" + categories + ", name=" + name + ", recipeAmount=" + recipeAmount + ", recipes="
                + recipes + "]";
    }

    public void load(File file) {
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    //line 1
                    String[] parts = line.split(",");
                    String name = parts[0];
                    int servings = Integer.parseInt(parts[1]);
                    Recipe recipe = new Recipe(name, servings);
                    recipe.setDescription(parts[2]);
                    recipe.setCalories(Double.parseDouble(parts[3]));
                    recipe.setPrepTime(parts[4]);

                    //line 2 categories
                    line = scanner.nextLine().replace("[", "").replace("]", ""); //removes the array brackets
                    parts = line.split(",");
                    for (String part : parts) {
                        recipe.addCategory(new Category(part));
                    }
                    //line 3 ingredients
                    line = scanner.nextLine().replace("[", "").replace("]", ""); //removes the array brackets
                    String[] ingredients = line.split("}"); //splits the ingredients into an array  
                    for (String ingredient : ingredients) {
                        ingredient = ingredient.replace("{", "");  //removes curly brackets

                        //if the ingredientstring starts with comma, then start from the second character in string
                        if(ingredient.startsWith(",")){
                            ingredient = ingredient.substring(0);
                        }

                        String[] ingredientParts = ingredient.split(",");   //splits the ingredient into an array of ingredient componetns
                        
                        //slice ingredName from '=' to the end
                        Double amount = Double.parseDouble(ingredientParts[0].substring(ingredientParts[0].indexOf("=")+1));
                        String unit = ingredientParts[1].substring(ingredientParts[0].indexOf("="));
                        String ingredName = ingredientParts[3].substring(ingredientParts[0].indexOf("="));

                        //add ingredient to recipe
                        recipe.addIngredient(ingredName, amount, unit);
                        System.out.println("succesfully added ingredient: " + ingredName + ' ' + amount + ' ' + unit);
                    }
                    //line 4 instructions
                    line = scanner.nextLine().replace("[", "").replace("]", ""); //removes the array brackets
                    String[] instructions = line.split("}"); //splits the instructions into an array
                    for(String step : instructions){
                        recipe.addStep(step);
                    }
                    //add recipe to cookbook
                    recipes.add(recipe);
                }

            scanner.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //write recipes in cookbook as csv file
    public void save(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Recipe r : recipes) {
                bufferedWriter.write(r.getName() + "," + r.getServings() + "," + r.getDescription() + "," + r.getCalories() + "," + r.getPrepTime());
                bufferedWriter.newLine();
                bufferedWriter.write(r.getCategories() + "");
                bufferedWriter.newLine();
                bufferedWriter.write(r.getIngredients() + "");
                bufferedWriter.newLine();
                bufferedWriter.write(r.getSteps() + "");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
}