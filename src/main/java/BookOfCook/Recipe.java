package BookOfCook;

import java.util.*;

public class Recipe {
    private String name, displayedName, description;                                                        // name of recipe that will be dealt with back end, as well as name displayed to user    
    private int numberOfServings, prepTime;                                                                 // number persons this recipe serves        
    private double calories, caloriesPerPerson;                                                             // calories in recipe, calories per person                                                                 
    private ArrayList<HashMap<String,Object>> Ingredients = new ArrayList<HashMap<String,Object>>();        // uses <String, Object> to store the ingredient name-, amount- and unit-strings, but at the same time be able to set the key equal to differnt datatypes
    private ArrayList<Category> categories = new ArrayList<Category>();                                     // stores the categories of the recipe                                    
    private ArrayList<String> steps = new ArrayList<String>();                                              // stores the steps of how to make the recipe

    // *CONSTRUCTOR                                                                                          
    public Recipe(String name, int numberOfServings) {              // constructor for recipe demands that recipe has a defined name and number of servings
        setName(name);
        setNumberOfServings(numberOfServings);
    }

    // !SJEKK HVEM SOM FAKTISK BRUKES
    // *GETTERS               
    // get name of recipe   
    public String getName() {
        return name;                                                // returns name of recipe
    } 

    //get description of recipe
    public String getDescription() {
        return description;                                         // returns description of recipe
    }

    //get prepTime
    public int getPrepTime() {
        return prepTime;                                            // returns prepTime
    }
    
    // get number of servings
    public int getNumberOfServings() {
        return numberOfServings;                                    // returns number of servings
    }

    // get calories
    public double getCalories() {
        return calories;                                            // returns calories
    }

    // get calories per person
    public double getCaloriesPerPerson() {
        return caloriesPerPerson;                                   // returns calories per person
    }

    // get ingredients
    public ArrayList<HashMap<String,Object>> getIngredients() {
        return Ingredients;                                         // returns ingredients
    }

    // get categories
    public ArrayList<Category> getCategories() {
        return categories;                                          // returns categories
    }

    // get steps
    public ArrayList<String> getSteps() {
        return steps;                                               // returns steps
    }

    // get displayed name (BEING USED IN COOKBOOK ATM)
    public String getDisplayedName() {
        return displayedName;                                       // returns displayed name
    }


    // *SETTERS
    // change name of recipe
    public void setName(String name) {
        validRecipeName(name);                                      // checks if name is valid
        this.name = name.toUpperCase();                             // sets name of recipe
        this.displayedName = capitalize(name);                      // sets displayname of recipe
    }

    // change number of servings
    public void setNumberOfServings(int numberOfServings) {
        validServings(numberOfServings);                           // checks if number of servings is valid
        this.numberOfServings = numberOfServings;                  // sets number of servings
    }

    // set calories
    public void setCalories(double calories) {   
        validateCalories(calories);                                 // checks if calories are valid
        this.calories = calories;                                   // sets the total calories of the recipe
        setCaloriesPerPerson();                                     // sets calories per person based on the new value of calories and number of servings
    }

    // !set calories per person should be combined with setCalories
    private void setCaloriesPerPerson() {          
        this.caloriesPerPerson = calories / numberOfServings;       // sets calories per person based on the new value of calories and number of servings
    }


    // *CHANGE AND MANIPULATE DATA
    // add ingredient to list of ingredients in recipe
    public void addIngredient(String name, double amount, String unit) {    
        HashMap<String,Object> ingredient = new HashMap<String,Object>();   // Creates a hashmap called ingredient that stores different properties of the ingredient
        
        ingredient.put("displayName", capitalize(name));                    // adds the displayName of the ingredient to the ingredient hashmap
        ingredient.put("name", name.toUpperCase());                         // adds the name of the ingredient to the ingredient hashmap
        ingredient.put("amount", amount);                                   // adds the amount key, value pair to the ingrdient hashmap . describes the amount of the ingredient
        ingredient.put("unit", unit.toLowerCase());                         // adds the "unit" key, value pair to the ingredient hashmap. describes the unit of the ingredient
        
        validateIngredient(ingredient);                                     // checks if ingredient is valid
        Ingredients.add(ingredient);                                        // adds the ingredient to the list of ingredients
    }

    // remove ingredient
    public void removeIngredient(String name) {                 
        for (HashMap<String, Object> Ingredient : Ingredients) {            // loops through all ingredients
            if (Ingredient.get("name").equals(name.toUpperCase())) {        // looks for a ingredient that mathces the name of the element to be removed
                Ingredients.remove(Ingredient);                             // removes the ingredient if it matches
                return;                                                     // return if match found so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    // change ingredient amount and unit in ingredients list
    public void changeIngredient(String name, double amount, String unit) {
        for (HashMap<String, Object> Ingredient : Ingredients) {            // loops through all ingredients
            HashMap<String, Object> copyOfIngredient = Ingredient;          // creates a copy of the ingredient

            if (Ingredient.get("name").equals(name.toUpperCase())) {        // if ingredient matches name
                Ingredient.replace("amount", amount);                       // changes the amount of the ingredient
                Ingredient.replace("unit", unit.toLowerCase());             // changes the unit of the ingredient

                try{
                    validateIngredient(Ingredient);                                 // validates the  to check new values
                } catch (IllegalArgumentException e) {                              // if validation fails 
                    Ingredient = copyOfIngredient;                                  // set the ingredient back to the old values
                    System.out.println(e.getMessage() + ": No changes was made.");  // print error message
                }

                return;  	                                                        // return so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    // change ingredient name to new name
    public void changeIngredientName(String name, String newName) {
        for (HashMap<String, Object> Ingredient : Ingredients) {        // loops through all ingredients
            HashMap<String, Object> copyOfIngredient = Ingredient;      // creates a copy of the ingredient

            if (Ingredient.get("name").equals(name.toUpperCase())) {    // if ingredient matches name of ingredient to be changed
                Ingredient.put("name", newName.toUpperCase());          // changes name to new name
                Ingredient.put("displayName", capitalize(newName));     // changes displayname to new name

                try {
                    validateIngredient(Ingredient);                                 // validates the  to check new values
                } catch (IllegalArgumentException e) {                              // if validation fails 
                    Ingredient = copyOfIngredient;                                  // set the ingredient back to the old values
                    System.out.println(e.getMessage() + ": No changes was made.");  // print error message
                }

                return;  	                                                        // returns so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    // !needs validation
    // establishes a relation between a category and a recipe
    public void addCategory(Category category) { 
        if(categories.contains(category)){                                  // if the category already exists in the recipe
            return;                                                         // returns to avoid adding the category again
        }          
        categories.add(category);                                           // adds category to list of categorys in recipe
        category.addRecipe(this);                                           // adds recipe to list of recipes in category. establishes the n to n relationship between category and recipe
    }

    // !needs validation
    // removes relation between a category and a recipe
    public void removeCategory(Category category) {
        if(!categories.contains(category)){                                 // if the category does not exist in the recipe
            return;                                                         // returns to avoid removing non existing fcategory                 
        }
        categories.remove(category);                                        // removes category from list of categories in recipe
        category.removeRecipe(this);                                        // removes recipe from list of recipes in category. 
    }

    // add step to recipe steps
    public void addStep(String step) {
        validateStep(capitalize(step));                                            
        steps.add(capitalize(step));                                        // adds step to list of steps in recipe
    }

    // remove step from recipe steps by index
    public void removeStep(int step) {
        validateStepIndex(step);                                                          
        steps.remove(step);                                                 // removes step from list of steps in recipe
    }


    // *SCALE RECIPE
    // when numbers of servings change, scale amounts
    public void scaleRecipe(int newNumberOfServings) { 
        validServings(newNumberOfServings);                                 // validates new number of servings¨
        double ratio = newNumberOfServings / numberOfServings;              // the ratio describes how many times the recipe has been scaled

        for (int i = 0; i < Ingredients.size(); i++) {                      // loops through all ingredients
            double amount = (double) Ingredients.get(i).get("amount");      // gets amount of every ingredient as a double 
            amount = amount * ratio;                                        // scales amount of ingredient by multiplying itself with the ratio 
            Ingredients.get(i).put("amount", amount);                       // sets the new amount of ingredient i in the recipe
        }

        setCalories(calories * ratio);                                      // scales calories by multiplying itself with the ratio
        setNumberOfServings(newNumberOfServings);                           // sets new number of servings
        setCaloriesPerPerson();                                             // sets calories per person based on the new value of number of servings
    }


    // *VALIDATIONS
    // validates step index
    private void validateStepIndex(int step) {
        if (step < 0 || step > steps.size()) {
            throw new IllegalArgumentException("step index is out of bounds");
        }
    }

    // validates step
    private void validateStep(String step) {
        if (step == null || step.equals("")) {                                      // if recipe step is null or empty
            throw new IllegalArgumentException("Step cannot be null or empty");     // throw error
        }
        whiteSpaceCheck(step);                                                      // checks if step contains white spaces trailing or leading
    }

    // validates Calories
    private void validateCalories(double calories) {                                
        if (calories < 0) {                                                         // if calories is negative
            throw new IllegalArgumentException("calories cannot be negative");      // throw error
        }
    }

    // validates Ingredient
    private void validateIngredient(HashMap<String,Object> Ingredient) {
        nullValueCheck(Ingredient);                                                 // checks if ingredient has null values
        regexCheck(Ingredient);                                                     // checks if ingredient has invalid characters
        whiteSpaceCheck(Ingredient.get("name").toString());                         // checks if ingredient name has white spaces
        whiteSpaceCheck(Ingredient.get("unit").toString());                         // checks if ingredient unit has white spaces
    }

    // checks if values in ingredient is null
    private void nullValueCheck(HashMap<String,Object> Ingredient) {                
        if (Ingredient.get("name") == null || Ingredient.get("amount") == null || Ingredient.get("unit") == null) {     // if ingredient has null values
            throw new IllegalArgumentException("One or more values of the ingredient is null.");                        // throw error
        }
    }

    // checks if values in ingredient are valid according to regex patterns
    private void regexCheck(HashMap<String,Object> Ingredient) {
        if (!(Ingredient.get("amount").toString().matches("[0-9]*\\.?[0-9]*") || Ingredient.get("amount").toString().matches("[0-9]*"))) {      // if ingredient amount is not a number
            throw new IllegalArgumentException("amount is not a Double or integer");                                                            // throw error
        }
        if (!(Ingredient.get("unit").toString().matches("[a-z]+"))) {                                                                           // if ingredient unit is not a lowercase string
            throw new IllegalArgumentException("unit contains invalid characters");                                                             // throw error
        }
        if (!(Ingredient.get("name").toString().matches("[A-Z\\- ]+") || Ingredient.get("displayName").toString().matches("[a-zA-Z\\- ]+"))) {  // if ingredient name is not a string of uppercase letters, dashes and spaces
            throw new IllegalArgumentException("name or displayName contains invalid characters");                                              // throw error
        }
    }

    // validates recipe name
    public void validRecipeName(String name) {
        if (name == null || name.equals("")) {                                                          // if name is null or empty
            throw new IllegalArgumentException("Name cannot be null or empty");                         // throw error
        }                                                                                               
        if (!(name.matches("[a-zA-Z\\- ]+"))) {                                                         // if name contains invalid characters
            throw new IllegalArgumentException("Name cannot contain numbers or special characters");    // throw error
        }
        whiteSpaceCheck(name);                                                                          // checks if name has white spaces trailing or leading
    }

    // validates number of servings
    public void validServings(int numberOfServings) {
        if (numberOfServings <= 0) {                                                                    // if number of servings is less than or equal to 0
            throw new IllegalArgumentException("Number of servings must be greater than 0");            // throw error
        }
        if (numberOfServings > 100) {                                                                   // ! skal vi sette constraint? if number of servings is greater than 100
            throw new IllegalArgumentException("Number of servings must be less than 100");             // throw error
        }
    }


    // *PARSING RECIPE FOR WRITING TO .txt FILE
    public String parsedRecipe() {
        String parsedRecipe = "";                                                               // Creates empty string that will be built up
        String recipeSection = "============================================\n";                // Creates a FATLINE to separate different sections.

        parsedRecipe += recipeSection;                                                          // adds FATLINE to parsed recipe start                                        
        parsedRecipe += name.toUpperCase() + "\n\n";                                            // adds name of recipe to parsed recipe
        parsedRecipe += "Categories:";                                                          // adds categories section for parsed recipe

        for(Category category : categories) {                                                   // loops through all categories
            parsedRecipe += "  " + category.name + ",";                                         // adds category to parsed recipe
        }

        parsedRecipe += "\n";  
        parsedRecipe += "Oppskriften er beregnet på: " + numberOfServings + " personer\n\n";    // adds number of servings to parsed recipe
        parsedRecipe += "Calories: " + calories + "\n";                                         // adds calories to parsed recipe
        parsedRecipe += "Calories per person: " + caloriesPerPerson + "\n\n";                   // adds calories per person to parsed recipe
        parsedRecipe += "Ingredients: \n";                                                      // adds ingredients section for parsed recipe

        for(HashMap<String, Object> Ingredient : Ingredients) {                                                                     // loops through all ingredients
            parsedRecipe += "  " + Ingredient.get("displayName") + ": " + Ingredient.get("amount") + " " + Ingredient.get("unit") + "\n";  // adds indented ingredient to parsed recipe
        }
        parsedRecipe += "\n";                                                                   // adds new line to parsed recipe

        parsedRecipe += "Steps:";                                                               // adds Steps section for parsed recipe
        for(String step : steps) {                                                              // loops through all steps
            parsedRecipe += "\n* " + step;                                                      // adds step to parsed recipe
        }
        parsedRecipe += "\n";                                                                   // adds new line to parsed recipe

        parsedRecipe += recipeSection + "\n";                                                   // adds FATLINE to parsed recipe end and an extra new line
        return parsedRecipe;                                                                    // returns parsed recipe
    }


    // *GENERAL TOOLBOX METHODS
    // capitalizes first letter and makes rest of string lowercase. good for displayed names in app
    private String capitalize(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();             // capitalizes first letter of string s and returns it
    }

    // check if string starts or ends with multiple white spaces
    private void whiteSpaceCheck(String s){
        if (s.startsWith(" ") || s.endsWith(" ")) {
            throw new IllegalArgumentException("String starts or ends with white spaces");
        }
    }


    // *TOSTRING METHOD
    /* @Override
    public String toString() {
        String ingredientList = "";
        for(HashMap<String,Object> ingredient : Ingredients) {
            ingredientList += "\n" + ingredient.get("name") + ": " + ingredient.get("amount") + " " + ingredient.get("unit");
            // !System.out.println(ingredient.get("displayName")); virker ikke?
        }
        return "Recipe: " + name + " serves " + numberOfServings + " people. It contains " + ingredientList + "\nand is categorized as: " + categories + "\nCalories: " + calories + " kcal" + "\nwhich is " + caloriesPerPerson + " kcal per person" + "\n" ;
    } */

    @Override
    public String toString() {
        return "Recipe [name=" + name + "]";
    }


    // *MAIN METHOD FOR TESTING
    public static void main(String[] args) {
        Recipe recipe = new Recipe("Sukker-pizza", 3);
        Category pizza = new Category("Pizza");
        Category italian = new Category("Italian");
        Category dessert = new Category("Dessert");

        // adds base ingredients and calories to recipe
        recipe.setCalories(1000);
        recipe.addIngredient("tomater", 100, "gram");
        recipe.addIngredient("sukker", 6, "ss");
        recipe.addIngredient("vann", 3, "l");
        recipe.addIngredient("mel", 100 , "kg");
        recipe.addIngredient("pizza kulsyre", 1, "stk");
        System.out.println(recipe.parsedRecipe());

        // testing av skalering og endring av ingrdiens
        recipe.scaleRecipe(4);
        recipe.changeIngredient("mel", 80, "kg");
        recipe.changeIngredientName("tomater", "tomat");
        System.out.println(recipe.parsedRecipe());

        // tester fjerning av ingredient og legge til kategorier
        recipe.removeIngredient("tomat");
        recipe.addCategory(pizza);
        recipe.addCategory(italian);
        recipe.addCategory(dessert);
        recipe.removeCategory(dessert);
        System.out.println(recipe.parsedRecipe());

        // legger til steg in recipe
        recipe.addStep("Putt tomater i en kasse");
        recipe.addStep("Putt sukker i en kasse");
        recipe.addStep("Putt vann i en kasse");
        recipe.addStep("Putt mel i en kasse");
        recipe.addStep("Kjøh!");
        recipe.addStep("ferri");
        recipe.removeStep(0);
        System.out.println(recipe.parsedRecipe());
    }
}