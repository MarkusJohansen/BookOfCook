package BookOfCook;

import java.util.*;

public class Recipe {
    private String name, description, prepTime;             // name of recipe that will be dealt with back end, as well as name displayed to user    
    private int numberOfServings;                           // number persons this recipe serves        
    private double calories, CalPerServing;                 // optional for user.
    private ArrayList<HashMap<String,Object>> ingredients;  // uses <String, Object> to store the ingredient name-, amount- and unit-strings, but at the same time be able to set the key equal to differnt datatypes
    private ArrayList<Category> categories;                 // stores the categories of the recipe                                    
    private ArrayList<String> steps;                        // stores the steps of how to make the recipe

    // *CONSTRUCTOR                                                                                          
    public Recipe(String name, int numberOfServings, String description ,String prepTime, ArrayList<HashMap<String,Object>> ingredients, ArrayList<Category> categories, ArrayList<String> steps) {              // constructor for recipe demands that recipe has a defined name and number of servings
        setName(name);
        setServings(numberOfServings);
        setDescription(description);
        setPrepTime(prepTime);

        this.ingredients = new ArrayList<HashMap<String,Object>>();
        for (HashMap<String,Object> ingredient : ingredients) {
            addIngredient((String) ingredient.get("name"), (Double) ingredient.get("amount"), (String) ingredient.get("unit")); //*Fikk med casting her
        } 

        this.categories = new ArrayList<Category>();
        for (Category category : categories) {
            addCategory(category);
        }

        this.steps = new ArrayList<String>();
        for (String step : steps) {
            addStep(step);
        }
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
    public String getPrepTime() {
        return prepTime;                                            // returns prepTime
    }
    
    // get number of servings
    public int getServings() {
        return numberOfServings;                                    // returns number of servings
    }

    // get calories
    public double getCalories() {
        return calories;                                            // returns calories
    }

    // get calories per person
    public double getCalPerServing() {
        return CalPerServing;                                       // returns calories per person
    }

    // get ingredients
    public ArrayList<HashMap<String,Object>> getingredients() {
        return new ArrayList<HashMap<String,Object>>(ingredients);  // returns ingredients
    }

    // get categories
    public ArrayList<Category> getCategories() {
        return new ArrayList<Category>(categories);                 // returns categories
    }

    // get steps
    public ArrayList<String> getSteps() {
        return new ArrayList<String>(steps);                        // returns steps
    }


    // *SETTERS
    // change name of recipe
    public void setName(String name) {
        validRecipeName(name);                                      // checks if name is valid
        this.name = name.toUpperCase();                             // sets name of recipe
    }

    // change number of servings
    public void setServings(int numberOfServings) {
        validServings(numberOfServings);                            // checks if number of servings is valid
        this.numberOfServings = numberOfServings;                   // sets number of servings
    }

    // set calories
    public void setCalories(double calories) {   
        validateCalories(calories);                                 // checks if calories are valid
        this.calories = calories;                                   // sets the total calories of the recipe
        setCalPerServing();                                         // sets calories per person based on the new value of calories and number of servings
    }

    private void setCalPerServing() {          
        this.CalPerServing = calories / numberOfServings;           // sets calories per person based on the new value of calories and number of servings
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrepTime(String time) {                          //prep time in hours
        prepTime = time;                                            // sets
    }


    // *Add and remvoe ingredients
    public void addIngredient(String name, double amount, String unit) {    
        HashMap<String,Object> ingredient = new HashMap<String,Object>();   // Creates a hashmap called ingredient that stores different properties of the ingredient
        
        ingredient.put("name", name.toUpperCase());                         // adds the name of the ingredient to the ingredient hashmap
        ingredient.put("amount", amount);                                   // adds the amount key, value pair to the ingrdient hashmap . describes the amount of the ingredient
        ingredient.put("unit", unit.toLowerCase());                         // adds the "unit" key, value pair to the ingredient hashmap. describes the unit of the ingredient
        
        validateIngredient(ingredient);                                     // checks if ingredient is valid
        ingredients.add(ingredient);                                        // adds the ingredient to the list of ingredients
    }

    public void removeIngredient(String name) {                 
        for (HashMap<String, Object> Ingredient : ingredients) {            // loops through all ingredients
            if (Ingredient.get("name").equals(name.toUpperCase())) {        // looks for a ingredient that mathces the name of the element to be removed
                ingredients.remove(Ingredient);                             // removes the ingredient if it matches
                return;                                                     // return if match found so it doesn't look for more matches. saves prossessing power
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
        validateStep(step);                                                 // checks if step is valid
        steps.add(capitalize(step));                                        // adds step to list of steps in recipe
    }

    // remove step from recipe steps by index
    public void removeStep(int step) {
        validateStepIndex(step);                                                          
        steps.remove(step);                                                 // removes step from list of steps in recipe
    }


    //! *SCALE RECIPE har ikke støtte men burde ha det, da dette er det mest mattematisk logiske vi dealer med
    // when numbers of servings change, scale amounts
    public void scale(int newNumberOfServings) { 
        validServings(newNumberOfServings);                                 // validates new number of servings¨
        double ratio = newNumberOfServings / numberOfServings;              // the ratio describes how many times the recipe has been scaled

        for (int i = 0; i < ingredients.size(); i++) {                      // loops through all ingredients
            double amount = (double) ingredients.get(i).get("amount");      // gets amount of every ingredient as a double 
            amount = amount * ratio;                                        // scales amount of ingredient by multiplying itself with the ratio 
            ingredients.get(i).put("amount", amount);                       // sets the new amount of ingredient i in the recipe
        }

        setCalories(calories * ratio);                                      // scales calories by multiplying itself with the ratio
        setServings(newNumberOfServings);                           // sets new number of servings
        setCalPerServing();                                             // sets calories per person based on the new value of number of servings
    }


    //! *VALIDATIONS burde disse flyttes over i egen klasse og kunne brukes i andre klasser
    //! kan føre til mindre validerings metoder hvis man sjekker det samme om og om igjen
    //! kan se mer ryddig ut
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
        if (!(Ingredient.get("name").toString().matches("[A-ZæøåÆØÅ\\- ]+") || Ingredient.get("displayName").toString().matches("[a-zA-Z\\- ]+"))) {  // if ingredient name is not a string of uppercase letters, dashes and spaces
            throw new IllegalArgumentException("name or displayName contains invalid characters");                                              // throw error
        }
    }

    // validates recipe name
    private void validRecipeName(String name) {
        if (name == null || name.equals("")) {                                                          // if name is null or empty
            throw new IllegalArgumentException("Name cannot be null or empty");                         // throw error
        }                                                                                               
        if (!(name.matches("[a-zA-ZæøåÆØÅ\\- ]+"))) {                                                   // if name contains invalid characters
            throw new IllegalArgumentException("Name cannot contain numbers or special characters");    // throw error
        }
        whiteSpaceCheck(name);                                                                          // checks if name has white spaces trailing or leading
    }

    // validates number of servings
    private void validServings(int numberOfServings) {
        if (numberOfServings <= 0) {                                                                    // if number of servings is less than or equal to 0
            throw new IllegalArgumentException("Number of servings must be greater than 0");            // throw error
        }
        if (numberOfServings > 100) {                                                                   // ! skal vi sette constraint? if number of servings is greater than 100
            throw new IllegalArgumentException("Number of servings must be less than 100");             // throw error
        }
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
    @Override
    public String toString() {
        return "Recipe [name=" + name + "]";
    }
}