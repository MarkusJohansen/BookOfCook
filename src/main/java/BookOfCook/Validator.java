package BookOfCook;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

abstract class Validator {

    protected void isNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("object is null");
        }
    }

    protected void nullOrEmpty(Object object) {
        if (object == null) {                                      
            throw new NullPointerException("string cannot be null");     
        }
        if (object instanceof String) {                                                             
            if (((String) object).trim().equals("")) {                                             
                throw new IllegalArgumentException("string cannot be empty or just whitespace");  
            }
            if (((String) object).trim().length() != ((String) object).length()) {                                                  
                throw new IllegalArgumentException("String: \"" + ((String) object) + "\"cannot have trailing or leading whitespace");
            }
        }
        if (object instanceof ArrayList) {                                                           
            if (((ArrayList) object).isEmpty()) {                                                    
                throw new IllegalArgumentException("ArrayList cannot be empty");                     
            }
        }
    }

    // validates number
    protected void negative(double calories) {                                
        if (calories < 0) {                                                         
            throw new IllegalArgumentException("number cannot be negative");     
        }
    }

    // validates Ingredient
    protected void validateIngredient(HashMap<String, String> ingredient, ArrayList<HashMap<String, String>> ingredients) {
        for (String key : ingredient.keySet()) {
            nullOrEmpty(ingredient.get(key));
            if (key.equals("amount")) {
                negativeOrZero(Double.parseDouble(ingredient.get(key)));
                if (ingredient.get(key).contains(",")) {
                    throw new IllegalArgumentException("amount cannot contain a comma");
                }
                if (ingredient.get(key).contains(".")) {
                    if (ingredient.get(key).indexOf(".") != ingredient.get(key).lastIndexOf(".")) {
                        throw new IllegalArgumentException("amount cannot contain more than one decimal point");
                    }
                }   
            }
            if (key.equals("name")) {
                for(HashMap<String, String> i : ingredients) {
                    if(ingredient.get(key).equals(i.get(key))) {
                        throw new IllegalArgumentException("Ingredient already exists");
                    }
                }
            }
        }
        ingredRegex(ingredient);                                                     
    }

    // checks if values in ingredient are valid according to regex patterns
    protected void ingredRegex(HashMap<String, String> Ingredient) {
        if (!(Ingredient.get("amount").toString().matches("[0-9]*\\.?[0-9]*") || Ingredient.get("amount").toString().matches("[0-9]*"))) {     
            throw new IllegalArgumentException("amount is not a Double or integer");                                                           
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
        nullOrEmpty(string);                                                                                                                         
        if (!(string.matches("[a-zA-ZæøåÆØÅ\\- ]+"))) {                                                  
            throw new IllegalArgumentException("String cannot contain numbers or special characters");   
        }
    }

    // validates number of servings
    protected void negativeOrZero(Double numberOfServings) {
        if (numberOfServings <= 0) {                                                                    
            throw new IllegalArgumentException("number cant be negative or zero");           
        }
    }

    protected void validTime(String string) {
        String[] split = string.split(" ");
        if (!(split[0].matches("[0-9]+"))) {
            throw new IllegalArgumentException("time must be expressed as a number");
        }
        if (split[1] == null || split[1].equals("") || split[1].equals("null")) {
            throw new IllegalArgumentException("time must have unit");
        }
    }

    protected void ingredientExists(ArrayList<String> foodContainer, String foodName){
        for (int i = 0; i < foodContainer.size(); i++) {                         
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
                throw new IllegalArgumentException("Recipe with same name already exists in cookbook");                          
            }
        }
    }

    protected void recipeExists(ArrayList<Recipe> recipes, Recipe recipe) {
        if (!recipes.contains(recipe)) {                                                                 
            throw new IllegalArgumentException("Recipe does not exist in cookbook");                     
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
                System.out.println(i.toUpperCase().equals(string.toUpperCase()));
                return true;
            }
        }
        return false;
    }

    protected String capitalize(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();            
    }

    protected String arrayStripper(String s) {
        return s.replace("[", "").replace("]", "").replace("{", "").replace("},", ";").replace("}","");                                                   
    }

    //*VALIDATION OF USER INPUT IN RECIPE EDITOR AND FRIDGE (GIVES USER CONSTRAINTS FOR INPUT)
    protected void validateTextField(char mode, ArrayList<String> array, ComboBox<String> box, TextField...textField){
        switch (mode) {
            case 'a':
                System.out.println("validateTextField: a");
                isNull(array);
                for(TextField t : textField){
                    nullOrEmpty(t.getText());
                    if(stringExistsArray(t.getText(), array)){
                        throw new IllegalArgumentException("Category already exists");
                    }
                }
                break;
            case 'b':
                isNull(box);
                nullOrEmpty(box.getValue());
                for(TextField t : textField){
                    //textfield contains spaces
                    if(t.getText().contains(" ")){
                        throw new IllegalArgumentException("Textfield contains spaces");
                    }
                    nullOrEmpty(t.getText());
                }
                break;
        }
    }

    protected HashMap<String, String> convertStringToHashMap(String string){
        String[] targetParts = string.split(" ");
        HashMap<String, String> ingred = new HashMap<>();
        ingred.put("name", targetParts[0]);
        ingred.put("amount", targetParts[1]);
        ingred.put("unit", targetParts[2]);
        return ingred;
    }

    protected HashMap<String,String> createIngredientFromTextFields(String name, String amount, String unit){
        HashMap<String,String> ingredient = new HashMap<String,String>();
        ingredient.put("name", name);
        ingredient.put("amount", amount);
        ingredient.put("unit", unit);
        return ingredient;
    }
}