package BookOfCook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Fridge extends Validator{
    private ArrayList<String> foodInFridge = new ArrayList<String>(); // an arraylist of hasmaps containing name, amount and unit of measurement ex.: [{"name": "tomat","amount": "3","unit":stk},{...}]

    public Fridge() {
        addFood("tomat");//dummy food
        addFood("melk");
        addFood("ost");
        addFood("egg");
        addFood("kjøttdeig");
        addFood("hamburgerbrød");
        addFood("rødløk");
        addFood("sylteagurk");
    }

    public ArrayList<String> getFood() {
        return new ArrayList<String>(foodInFridge);
    }

    public void addFood(String name) {
        nullOrEmpty(name);
        ingredientExists(foodInFridge, name);        
        foodInFridge.add(name.toLowerCase());                   // adds the ingredient to the list of ingredients
    }   

    public void removeFood(String name) {
        boolean removedFood = false;
        for (int i = 0; i < foodInFridge.size(); i++){                          // iterates through foodInFridge
            if(foodInFridge.get(i) == name){                        // checks if food is equal to name
                System.out.println("Removing " + foodInFridge.get(i));          
                foodInFridge.remove(i);                                         // removes i food
                removedFood = true;
            }
        }
        if (!removedFood) {                                                     // checks if any food removed at all
            System.out.println("No food matching input '" + name + "' in fridge");
        }
    }

    public ArrayList<Recipe> filter(ArrayList<Recipe> recipesToFilter) {
        Predicate<Recipe> criteria = (Recipe recipe) -> {
            for (HashMap<String, String> ingredient : recipe.getIngredients()) {
                boolean ingredientInFridge = false;
                for (String food : foodInFridge) {
                    if (ingredient.get("name").toUpperCase().equals(food.toUpperCase())) {
                        ingredientInFridge = true;
                    }
                }
                if (!ingredientInFridge) {
                    return false;
                }
            }
            return true;
        }; 
        return recipesToFilter.stream().filter(criteria).collect(Collectors.toCollection(ArrayList::new));
    } 
}
