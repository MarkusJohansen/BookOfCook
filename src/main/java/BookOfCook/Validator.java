package BookOfCook;

import java.util.ArrayList;
import java.util.HashMap;

public class Validator {


    //*GENERAL METHODS
    // validates index
    public void validIndex(ArrayList<Object> collection, int index) {
        if (index < 0 || index > collection.size()) {
            throw new IllegalArgumentException("index is out of bounds");
        }
    }

    // checks if null, empty and if string, also trailing and leading whitespace
    public void nullOrEmpty(Object object) {
        if (object == null) {                                      // if recipe string is null or empty
            throw new IllegalArgumentException("string cannot be null");     // throw error
        }
        if (object instanceof String) {                                                             // if object is a string
            if (((String) object).trim().equals("")) {                                              // if string is empty
                throw new IllegalArgumentException("string cannot be empty or just whitespace");   // throw error
            }
            if (((String) object).trim().length() == ((String) object).length()) {                  // if string                                 // if string is empty
                throw new IllegalArgumentException("string cannot have trailing or leading whitespace"); // throw error// throw error
            }
        }
    }

    // validates number
    public void negativeCheck(double calories) {                                
        if (calories < 0) {                                                         // if calories is negative
            throw new IllegalArgumentException("number cannot be negative");      // throw error
        }
    }

    // validates Ingredient
    public void validateIngredient(HashMap<String,Object> Ingredient) {
        //loop through Ingredient keys and validate
        for (String key : Ingredient.keySet()) {
            nullOrEmpty(Ingredient.get(key));
        }
        regexCheck(Ingredient);                                                     // checks if ingredient has invalid characters                 // checks if ingredient unit has white spaces
    }

    // checks if values in ingredient are valid according to regex patterns
    public void regexCheck(HashMap<String,Object> Ingredient) {
        if (!(Ingredient.get("amount").toString().matches("[0-9]*\\.?[0-9]*") || Ingredient.get("amount").toString().matches("[0-9]*"))) {      // if ingredient amount is not a number
            throw new IllegalArgumentException("amount is not a Double or integer");                                                            // throw error
        }
        if (!(Ingredient.get("unit").toString().matches("[a-z]+"))) {                                                                           // if ingredient unit is not a lowercase string
            throw new IllegalArgumentException("unit contains invalid characters");                                                             // throw error
        }
        if (!(Ingredient.get("name").toString().matches("[A-ZæøåÆØÅ\\- ]+"))) {  // if ingredient name is not a string of uppercase letters, dashes and spaces
            throw new IllegalArgumentException("name or displayName contains invalid characters");                                              // throw error
        }
        noNumbersOrSpecials(Ingredient.get("name"));
    }

    // validates recipe name
    public void noNumbersOrSpecials(String name) {
        nullOrEmpty(name);                                                                                                                          // checks if name is null or empty                                                                                            
        if (!(name.matches("[a-zA-ZæøåÆØÅ\\- ]+"))) {                                                   // if name contains invalid characters
            throw new IllegalArgumentException("Name cannot contain numbers or special characters");    // throw error
        }
    }

    //*RECIPE METHODS
    // validates number of servings
    public void validServings(int numberOfServings) {
        if (numberOfServings <= 0) {                                                                    // if number of servings is less than or equal to 0
            throw new IllegalArgumentException("Number of servings must be greater than 0");            // throw error
        }
        if (numberOfServings > 100) {                                                                   // ! skal vi sette constraint? if number of servings is greater than 100
            throw new IllegalArgumentException("Number of servings must be less than 100");             // throw error
        }
    }
    
    // *GENERAL TOOLBOX METHODS
    // capitalizes first letter and makes rest of string lowercase. good for displayed names in app
    public String capitalize(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();             // capitalizes first letter of string s and returns it
    }

}