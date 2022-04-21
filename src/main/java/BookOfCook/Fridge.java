package BookOfCook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Fridge extends Validator{
    // *FIELDS
    private ArrayList<HashMap<String, String>> foodInFridge = new ArrayList<HashMap<String, String>>(); // an arraylist of hasmaps containing name, amount and unit of measurement ex.: [{"name": "tomat","amount": "3","unit":stk},{...}]

    // *CONSTRUCTOR
    public Fridge() {}

    // *GETTERS
    public ArrayList<HashMap<String, String>> getFood() {
        return new ArrayList<HashMap<String, String>>(foodInFridge);
    }

    //*ADDING AND REMOVING FOOD 
    public void addFood(String name, double amount, String unit) {
        ingredientExists(foodInFridge, name);

        System.out.println("Adding " + amount + " " + unit + " of " + name + " to fridge.");
        
        HashMap<String, String> ingredient = new HashMap<String, String>();
        ingredient.put("name", name.toLowerCase());     // adds the name of the ingredient to this ingredient hashmap
        ingredient.put("amount", amount);               // adds the amount key, value pair to this ingrdient hashmap, describes the amount of the ingredient
        ingredient.put("unit", unit);                   // adds the "unit" key, value pair to this ingredient hashmap, describes the unit of the ingredient
        foodInFridge.add(ingredient);                   // adds the ingredient to the list of ingredients
    }   

    public void removeFood(String name) {
        boolean removedFood = false;
        for (int i = 0; i < foodInFridge.size(); i++){                          // iterates through foodInFridge
            if(foodInFridge.get(i).get("name") == name){                        // checks if food is equal to name
                System.out.println("Removing " + foodInFridge.get(i));          
                foodInFridge.remove(i);                                         // removes i food
                removedFood = true;
            }
        }
        if (!removedFood) {                                                     // checks if any food removed at all
            System.out.println("No food matching input '" + name + "' in fridge");
        }
    }

    //fridge filter
    public ArrayList<Recipe> filter(ArrayList<Recipe> recipesToFilter) {
        //for recipe in recipesToFilter, check if all ingredients in recipe is in fridge return true, else return false
        Predicate<Recipe> criteria = (Recipe recipe) -> {
            for (HashMap<String, String> ingredient : recipe.getIngredients()) {
                boolean ingredientInFridge = false;
                for (HashMap<String, String> food : foodInFridge) {
                    if (ingredient.get("name").equals(food.get("name"))) {
                        ingredientInFridge = true;
                    }
                }
                if (!ingredientInFridge) {
                    return false;
                }
            }
            return true;
        }; 

        //add recipes that matches criteria to result with stream
        return recipesToFilter.stream().filter(criteria).collect(Collectors.toCollection(ArrayList::new));
    } 

    @Override
    public String toString() {
        return "Fridge [foodInFridge=" + foodInFridge + "]";
    }
}
