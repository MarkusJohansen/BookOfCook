package BookOfCook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Fridge extends Validator{
    private ArrayList<String> foodInFridge = new ArrayList<String>();   // an arraylist of strings representing ingredient names

    public Fridge() {                                                   // constructor
        addFood("tomat");                                               //dummy food
        addFood("melk");                                                //dummy food
        addFood("ost");                                                 //dummy food
        addFood("egg");                                                 //dummy food
        addFood("kjøttdeig");                                           //dummy food
        addFood("hamburgerbrød");                                       //dummy food
        addFood("rødløk");                                              //dummy food
        addFood("sylteagurk");                                          //dummy food
    }

    public ArrayList<String> getFood() {                                // returns foodInFridge
        return new ArrayList<String>(foodInFridge);                     // returns a copy of foodInFridge
    }

    public void addFood(String name) {                                  // adds food to fridge
        nullOrEmpty(name);                                              // validates name by calling nullOrEmpty
        numbersOrSpecials(name);                                        // validates name by calling numbersOrSpecials
        ingredientExists(foodInFridge, name);                           // validates if ingredient already exists in fridge
        foodInFridge.add(name.toLowerCase());                           // adds the ingredient to the list of ingredients
    }   

    public void removeFood(String name) {                               // removes food from fridge
        boolean removedFood = false;                                    // boolean to check if food was removed
        for (int i = 0; i < foodInFridge.size(); i++){                  // iterates through foodInFridge
            if(foodInFridge.get(i) == name){                            // checks if food is equal to name
                foodInFridge.remove(i);                                 // removes i food
                removedFood = true;
            }
        }
        if (!removedFood) {                                             // checks if any food removed at all
            System.out.println("No food matching '" + name);            // prints error message
        }
    }

    public ArrayList<Recipe> filter(ArrayList<Recipe> target) {      
        Predicate<Recipe> criteria = (Recipe recipe) -> {                                           // predicate to check if all ingredients in recipe are in fridge
            for (HashMap<String, String> ingredient : recipe.getIngredients()) {                    // iterates through ingredients in recipe
                boolean ingredientInFridge = false;                                                 // boolean to check if ingredient is in fridge
                for (String food : foodInFridge) {                                                  // iterates through foodInFridge
                    if (ingredient.get("name").toUpperCase().equals(food.toUpperCase())) {          // checks if ingredient name is equal to food in fridge
                        ingredientInFridge = true;                                                  // sets ingredientInFridge to true
                    }                                                                       
                }
                if (!ingredientInFridge) {                                                          // checks if ingredientInFridge is false
                    return false;                                                                   // returns false
                }
            }
            return true;                                                                            // returns true
        }; 
        return target.stream().filter(criteria).collect(Collectors.toCollection(ArrayList::new));   // returns a filtered list of recipes, by filtration of recipes thorugh criteria
    } 
}
