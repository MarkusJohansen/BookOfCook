package BookOfCook;

public class Ingredient {
    
    //*FIELDS
    String name;
    double calories;

    //*CONSTRUCTOR
    public Ingredient(String name){
        this.name = name;
    }

    public Ingredient(String name, double cal){
        this.name = name;
        this.calories = cal;
    }

    //*CALORIES
    public void setCalories(double cal){
        calories = cal;
    }
}
