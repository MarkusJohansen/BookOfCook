package BookOfCook;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Fridge {
    //*FIELDS
    private ArrayList<Ingredient> foodInFridge = new ArrayList<Ingredient>();
    private LocalDateTime lastUpdateOfFridge;

    //*CONSTRUCTOR
    public Fridge() {
        
    }

    //*GETTERS
    public ArrayList<Ingredient> displayFridge() {
        return foodInFridge;
    }

    //*METHODS FOR FOOD IN FRIDGE
    public void addFood(Ingredient food) {
        foodInFridge.add(food);
    }

    public void emptyFridge() {
        foodInFridge.clear();
    }
}
