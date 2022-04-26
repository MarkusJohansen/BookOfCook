package BookOfCook;

import java.util.ArrayList;
import java.util.HashMap;

abstract class Validator {

    //*GENERAL METHODS
    //! validates index sliter med arrayet
    protected void validIndex(ArrayList<Object> collection, int index) {   
        if (index < 0 || index > collection.size()) {
            throw new IllegalArgumentException("index is out of bounds");
        }
    }

    // checks if null, empty and if string, also trailing and leading whitespace
    protected void nullOrEmpty(Object object) {
        if (object == null) {                                      // if recipe string is null or empty
            throw new IllegalArgumentException("string cannot be null");     // throw error
        }
        if (object instanceof String) {                                                             // if object is a string
            if (((String) object).trim().equals("")) {                                              // if string is empty
                throw new IllegalArgumentException("string cannot be empty or just whitespace");   // throw error
            }
            if (((String) object).trim().length() != ((String) object).length()) {                  // if string                                 // if string is empty
                throw new IllegalArgumentException("String: \"" + ((String) object) + "\"cannot have trailing or leading whitespace"); // throw error// throw error
            }
        }
        if (object instanceof ArrayList) {                                                           // if object is an arraylist
            if (((ArrayList) object).isEmpty()) {                                                    // if arraylist is empty
                throw new IllegalArgumentException("ArrayList cannot be empty");                      // throw error
            }
        }
    }

    // validates number
    protected void negative(double calories) {                                
        if (calories < 0) {                                                         // if calories is negative
            throw new IllegalArgumentException("number cannot be negative");      // throw error
        }
    }

    // validates Ingredient
    protected void validateIngredient(HashMap<String, String> ingredient, ArrayList<HashMap<String, String>> ingredients) {
        //loop through Ingredient keys and validate
        for (String key : ingredient.keySet()) {
            nullOrEmpty(ingredient.get(key));
            if (key.equals("amount")) {
                negativeOrZero(Double.parseDouble(ingredient.get(key)));
            }
            if (key.equals("name")) {
                for(HashMap<String, String> i : ingredients) {
                    if(ingredient.get(key).equals(i.get(key))) {
                        throw new IllegalArgumentException("Ingredient already exists");
                    }
                }
            }
        }
        ingredRegex(ingredient);                                                     // checks if ingredient has invalid characters                 // checks if ingredient unit has white spaces
    }

    // checks if values in ingredient are valid according to regex patterns
    protected void ingredRegex(HashMap<String, String> Ingredient) {
        if (!(Ingredient.get("amount").toString().matches("[0-9]*\\.?[0-9]*") || Ingredient.get("amount").toString().matches("[0-9]*"))) {      // if ingredient amount is not a number
            throw new IllegalArgumentException("amount is not a Double or integer");                                                            // throw error
        }
        try {
            numbersOrSpecials((String) Ingredient.get("name"));
            numbersOrSpecials((String) Ingredient.get("unit"));
        } catch (Exception e) {
            throw new IllegalArgumentException("name or unit contains invalid characters");
        }
    }

    // validates recipe name
    protected void numbersOrSpecials(String string) {
        nullOrEmpty(string);                                                                                                                          // checks if s is null or empty
        if (!(string.matches("[a-zA-ZæøåÆØÅ\\- ]+"))) {                                                   // if name contains invalid characters
            throw new IllegalArgumentException("String cannot contain numbers or special characters");    // throw error
        }
    }

    // validates number of servings
    protected void negativeOrZero(Double numberOfServings) {
        if (numberOfServings <= 0) {                                                                    // if number of servings is less than or equal to 0
            throw new IllegalArgumentException("number cant be negative or zero"); // throw error            // throw error
        }
    }

    protected void ingredientExists(ArrayList<String> foodContainer, String foodName){
        for (int i = 0; i < foodContainer.size(); i++) {                         // iterates through foodInFridge
            String checkName = (String) foodContainer.get(i);
            if (checkName.toUpperCase().equals(foodName.toUpperCase())) {
                throw new IllegalArgumentException(checkName + " already exists within food container");
            }
        }
    }

    // checks if recipe name already exists in cookbook
    protected void duplicateRecipeName(ArrayList<Recipe> recipes, Recipe recipe){
        for (Recipe r : recipes) {    
            if (r.getName().toUpperCase().equals(recipe.getName().toUpperCase())) {     
                throw new IllegalArgumentException("Recipe with same name already exists in cookbook"); // describes problem in console;                             
            }
        }
    }

    protected void recipeExists(ArrayList<Recipe> recipes, Recipe recipe) {
        if (!recipes.contains(recipe)) {                                                                 // checks if recipe exists in cookbook
            throw new IllegalArgumentException("Recipe does not exist in cookbook");                     // describes problem in console;
        }
    }

    protected boolean checkIfCategoryExist(String categoryName, ArrayList<Category> categories){
        for(int i = 0; i < categories.size(); i++){
            if( categories.get(i).getName().toUpperCase().equals(categoryName.toUpperCase())){
                return true;
            }
        }
        return false;
    }

    protected boolean stringExistsArray(String string, ArrayList<String> arraylist){
        for (String i : arraylist) {
            if(i.toUpperCase().equals(string.toUpperCase())){
                return true;
            }
        }
        return false;
    }

    // *GENERAL TOOLBOX METHODS
    // capitalizes first letter and makes rest of string lowercase. good for displayed names in app
    protected String capitalize(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();             // capitalizes first letter of string s and returns it
    }
}