package BookOfCook;

import java.util.*;
public class Recipe {

    //*FIELDS
    private String name;
    private int numberOfServings;
    private double calories;
    private ArrayList<HashMap<String,Object>> Ingredients = new ArrayList<HashMap<String,Object>>();
    private ArrayList<Category> categories = new ArrayList<Category>(); 

    //*CONSTRUCTOR
    public Recipe(String name, int numberOfServings) {
        this.name = name;
        this.numberOfServings = numberOfServings;
    }

    //*ADDING INGREDIENTS
    //*ADDING STEP-BY-STEP
    //*ADDING PICTURE
    //*ADDING SCHOOL
    //*RECIPE TO JSON
    
    //to json format method 
    public String toJSON() {
        String json = "{";
        json += "\"name\":\"" + this.name + "\",";
        json += "\"numberOfServings\":\"" + this.numberOfServings + "\",";
        json += "\"calories\":\"" + this.calories + "\",";
        json += "\"category\":\"" + this.categories + "\",";
        json += "\"Ingredients\":\"" + this.Ingredients + "\"";
        json += "}";
        return json;
    }
    
    //recipes to json format method
    public static String recipesToJSON(ArrayList<Recipe> recipes) {
        String json = "[";
        for (Recipe recipe : recipes) {
            json += recipe.toJSON() + ",";
        }
        json = json.substring(0, json.length() - 1);
        json += "]";
        return json;
    }

    

    //method for testing
    public static void main(String[] args) {
        Recipe recipe = new Recipe("test", 1);
        recipe.addIngredient("test", 1, 1, 1);
        System.out.println(recipe.toJSON());
    }
}
