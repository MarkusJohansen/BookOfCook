
package BookOfCook;

import java.util.*;
import java.io.*;

public class Cookbook implements recipeContainer {
    private String name;
    private int recipeAmount;
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private ArrayList<Category> categories = new ArrayList<Category>();

    // *CONSTRUCTOR
    public Cookbook(String name) {
        setName(name);
        this.recipeAmount = 0;                                                          // amount of recipes in cookbook is always 0 at start
    }

    // *RECIPE ADD AND REMOVE METHODS
    // adds recipe to cookbook
    public void addRecipe(Recipe recipe) {
        duplicateRecipeCheck(recipe);                                                   // checks if recipe already exists
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

    //*SETTERS
    // sets name of cookbook
    public void setName(String name) {
        nameIsValid(name);                                  // checks if name is valid
        this.name = name;                                   // sets name of cookbook                   
    }

    // *GETTERS
    // ? er disse nødvendige
    // returns cookbook name
    public String getName() {
        return name; // returns cookbook name
    }

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

    // // *GET RECIPES IN CATEGORIES METHODS
    // // metode som returnerer alle recipes som inneholder minst én av kategoriene
    // public ArrayList<Recipe> getSortedRecipes(ArrayList<Category> categories){  // categories as parameter
    //     ArrayList<Recipe> sortedRecipes = new ArrayList<>();                    // create an output arraylist
    //     for (Recipe recipe : recipes) {                                         // loops through all recipes in cookbook
    //         for (Category category : categories) {                              // loops through all categories
    //             if(recipe.getCategories().contains(category)){                  // checks if recipe has this category
    //                 sortedRecipes.add(recipe);                                  // if true: add recipe to sortedRecipes
    //             }
    //         }
    //     }
    //     return sortedRecipes;
    // }

    // * denne vi bruker nå
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
                // while (scanner.hasNextLine()) {
                //     String line = scanner.nextLine();
                //     String[] parts = line.split(",");
                //     Recipe recipe = new Recipe(parts[0], parts[1], parts[2], parts[3], parts[4]);
                //     recipes.add(recipe);
                // }

                //while there is a next line(row) in csv file
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    //splits the line into parts. Each part is a string and has to be converted from that to correct data type
                    String[] parts = line.split(",");

                    //constructs object with name and serving amounts
                    Recipe recipe = new Recipe(parts[0], Integer.parseInt(parts[1]));

                    //adds categories in  parts[2] to categories
                    for (String category : parts[2].split(";")) {
                        recipe.addCategory(new Category(category));
                    }

                    //adds description string in parts[3] as description
                    recipe.setDescription(parts[3]);

                    //adds ingredients in parts[4] as ingredients
                    for (String ingredient : parts[4].split(";")) {
                        String[] ingredientParts = ingredient.split(",");
                        //parse string to double
                        recipe.addIngredient(ingredientParts[0], Double.parseDouble(ingredientParts[1]), ingredientParts[2]);
                    }

                    //adds steps in parts[5] to recipe Object
                    for (String step : parts[5].split(";")) {
                        recipe.addStep(step);
                    }

                    //adds calories double in parts[6] to recipe Object
                    recipe.setCalories(Double.parseDouble(parts[6]));

                    //sets preparing time in parts[7] to recipe Object
                    recipe.setPrepTime(parts[7]);

                    //adds recipe to cookbook
                    recipes.add(recipe);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        }
    }

    //write recipes in cookbook as csv file
    public void save(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Recipe r : recipes) {
                bufferedWriter.write(r.getName() + "," + r.getNumberOfServings() + "," + r.getCategories() + "," + r.getDescription() + "," + r.getIngredients() + "," + r.getSteps() + "," + r.getCalories() + "," + r.getPrepTime());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
}