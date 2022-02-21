package BookOfCook;

import java.util.ArrayList;
public class Recipe {

    //*FIELDS
    private String name;
    private int numberOfServings;
    private double calories;
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    private ArrayList<Category> categories = new ArrayList<Category>();


    //*CONSTRUCTOR
    public Recipe(String name) {
        this.name = name;
    }

    //*NAME
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //*NUMBER
    public int getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(int numberOfServings) {
        this.numberOfServings = numberOfServings;
    }


    //*CALORIES
    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }


    //*INGREDIENTS
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }


    //*CATEGORIES
    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(Category category) {
        categories.add(category);
    }

}
