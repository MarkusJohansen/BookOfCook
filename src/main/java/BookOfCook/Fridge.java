package BookOfCook;

import java.util.ArrayList;
import java.util.HashMap;

public class Fridge extends Validator{
    // *FIELDS
    private ArrayList<HashMap<String, Object>> foodInFridge = new ArrayList<HashMap<String, Object>>(); // an arraylist of hasmaps containing name, amount and unit of measurement ex.: [{"name": "tomat","amount": "3","unit":stk},{...}]

    // *CONSTRUCTOR
    public Fridge() {}

    // *GETTERS
    public ArrayList<HashMap<String, Object>> getFood() {
        return new ArrayList<HashMap<String, Object>>(foodInFridge);
    }

    //*ADDING AND REMOVING FOOD 
    public void addFood(String name, double amount, String unit) {
        if(isFoodInFridge(name)){
            throw new IllegalStateException("Food " + name + " is already in fridge.");
        }

        System.out.println("Adding " + amount + " " + unit + " of " + name + " to fridge.");
        
        HashMap<String, Object> ingredient = new HashMap<String, Object>();
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
    
    // *VALIDATION
    public boolean isFoodInFridge(String food){
        for (int i = 0; i < foodInFridge.size(); i++) {                         // iterates through foodInFridge
            if (foodInFridge.get(i).get("name").equals(food.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Fridge [foodInFridge=" + foodInFridge + "]";
    }

    public static void main(String[] args) {
        Fridge fridge = new Fridge();
        
        fridge.addFood("milk", 1.0, "L");
        fridge.addFood("pasta", 0.5, "Kg");

        HashMap<String, Object> milk = new HashMap<String, Object>();
        milk.put("name", "milk");
        milk.put("amount", 1);
        milk.put("unit", "L");

        HashMap<String, Object> pasta = new HashMap<String, Object>();
        milk.put("name", "pasta");
        milk.put("amount", 0.5);
        milk.put("unit", "kg");

        System.out.println(fridge.getFood());
        System.out.println(fridge.getFood().contains(milk));

        fridge.removeFood(milk.get("name").toString());

        System.out.println(fridge.getFood());
    }
}
