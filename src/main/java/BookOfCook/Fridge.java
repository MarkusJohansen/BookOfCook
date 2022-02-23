package BookOfCook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Fridge {
    // *FIELDS
    private ArrayList<HashMap<String, Object>> foodInFridge = new ArrayList<HashMap<String, Object>>();
    private LocalDateTime lastUpdateOfFridge;

    // *CONSTRUCTOR
    public Fridge() {

    }

    // *GETTERS
    public ArrayList<HashMap<String, Object>> displayFridge() {
        return foodInFridge;
    }

    // *METHODS FOR FOOD IN FRIDGE
    public void addFood(String name, double amount, String unit) {
        HashMap<String, Object> ingredient = new HashMap<String, Object>();
        ingredient.put("name", name.toLowerCase());                         // adds the name of the ingredient to the ingredient hashmap
        ingredient.put("amount", amount);                                   // adds the amount key, value pair to the ingrdient hashmap . describes the   
        ingredient.put("unit", unit);                                       // unit of the ingredient
        foodInFridge.add(ingredient);
    }

    public void emptyFridge() {
        foodInFridge.clear();
    }

    public void removeFood(String name) {
        boolean removedFood = false;

        for (int i = 0; i < foodInFridge.size(); i++) {
            if (foodInFridge.get(i).get("name").equals(name.toLowerCase())) {
                System.out.println("Removing " + foodInFridge.get(i));
                foodInFridge.remove(i);
                removedFood = true;
            }
        }

        if (!removedFood) {
            System.out.println("No food matching input " + name);
        }
    }

    @Override
    public String toString() {
        return "Fridge [foodInFridge=" + foodInFridge + ", lastUpdateOfFridge=" + lastUpdateOfFridge + "]";
    }

    public static void main(String[] args) {
        Fridge f = new Fridge();
        f.addFood("Tomat", 2, "stk");
        f.addFood("Pepperoni", 500, "gram");

        f.removeFood("Tomat");
    }
}
