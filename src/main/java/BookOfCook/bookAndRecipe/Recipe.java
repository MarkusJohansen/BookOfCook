package BookOfCook.bookAndRecipe;

import java.util.*;

import BookOfCook.Category;
public class Recipe {

    //*FIELDS
    private String name;
    private int numberOfServings;
    private double calories;
    private double caloriesPerPerson;
    private ArrayList<HashMap<String,Object>> Ingredients = new ArrayList<HashMap<String,Object>>();
    private ArrayList<Category> categories = new ArrayList<Category>();

    //*CONSTRUCTOR
    public Recipe(String name, int numberOfServings) {
        this.name = name;
        this.numberOfServings = numberOfServings;
    }

    //*CHANGING NAME
    public void setName(String name) {
        this.name = name;
    }

    //*ADDING, REMOVING AND INGREDIENTS
    //add ingredient to list of ingredients in recipe
    public void addIngredient(String name, double amount, String unit) {
        HashMap<String,Object> ingredient = new HashMap<String,Object>();
        ingredient.put("name", name);
        ingredient.put("amount", amount);
        ingredient.put("unit", unit);
        Ingredients.add(ingredient);
    }

    // remove ingredient
    public void removeIngredient(String name) {                 
        for (int i = 0; i < Ingredients.size(); i++) {                  //loops through all ingredients
            if (Ingredients.get(i).get("name").equals(name)) {          //looks for a ingredient that mathces the name of the element to be removed
                Ingredients.remove(i);                                  //removes the ingredient if it matches
                break;                                                  //breaks if match found so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    //change ingredient in ingredients list
    public void changeIngredient(String name, double amount, String unit) {
        for (int i = 0; i < Ingredients.size(); i++) {                      //loops through all ingredients
            if (Ingredients.get(i).get("name").equals(name)) {              //if ingredient matches name
                Ingredients.get(i).put("amount", amount);                   //changes amount to new amount
                Ingredients.get(i).put("unit", unit);                       //changes unit to new unit
                break;  	                                                //breaks so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    //change ingredient name to new name
    public void changeIngredientName(String name, String newName) {
        for (int i = 0; i < Ingredients.size(); i++) {                      //loops through all ingredients
            if (Ingredients.get(i).get("name").equals(name)) {              //if ingredient matches name of ingredient to be changed
                Ingredients.get(i).put("name", newName);                    //changes name to new name
                break;  	                                                //breaks so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    //*SETTING AND CHANGING CALORIES
    //set calories
    public void setCalories(double calories) {
        this.calories = calories;
        setCaloriesPerPerson();
    }

    //set calories per person
    public void setCaloriesPerPerson() {
        this.caloriesPerPerson = calories / numberOfServings;
        
    }

    //*SCALING AND CHANGING SERVINGS
    //change number of servings
    public void changeNumberOfServings(int newNumberOfServings) {
        numberOfServings = newNumberOfServings;
    }

    //when numbers of servings change, change amount of ingredients and calories
    public void scaleRecipe(int newNumberOfServings) {  
        double ratio = newNumberOfServings / numberOfServings;              //the ratio describes how many times the recipe has been scaled     
        for (int i = 0; i < Ingredients.size(); i++) {                      //loops through all ingredients
            double amount = (double) Ingredients.get(i).get("amount");      //gets amount of every ingredient as a double 
            amount = amount * ratio;                                        //scales amount of ingredient by multiplying itself with the ratio 
            Ingredients.get(i).put("amount", amount);                       //sets the new amount of ingredient i in the recipe
        }
        setCalories(calories * ratio);                                       //scales calories by multiplying itself with the ratio
        numberOfServings = newNumberOfServings;                             //updates value of number of servings to the new value
        setCaloriesPerPerson();
    }

    //*TOSTRING METHOD
    @Override
    public String toString() {
        String ingredientList = "";
        for (int i = 0; i < Ingredients.size(); i++) {
            ingredientList += "\n" + Ingredients.get(i).get("name") + ": " + Ingredients.get(i).get("amount") + " " + Ingredients.get(i).get("unit");
        }
        return "Recipe: " + name + " serves " + numberOfServings + " people. It contains " + ingredientList + "\nand is categorized as: " + categories + "\nCalories: " + calories + " kcal" + "\nwhich is " + caloriesPerPerson + " kcal per person" + "\n" ;
    }

    public static void main(String[] args) {
        Recipe recipe = new Recipe("Sukker-pizza", 3);
        recipe.addIngredient("tomater", 100, "gram");
        recipe.addIngredient("sukker", 6, "ss");
        recipe.addIngredient("vann", 3, "l");
        recipe.addIngredient("mel", 4, "kg");
        recipe.setCalories(1500);
        System.out.println(recipe);

        recipe.scaleRecipe(9);
        System.out.println(recipe);
    }
}