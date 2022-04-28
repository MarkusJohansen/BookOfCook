package BookOfCook;

import java.util.*;

public class Recipe extends Validator{
    private String name, description, prepTime;                                 // name of recipe that will be dealt with back end, as well as name displayed to user    
    private int numberOfServings;                                               // number persons this recipe serves        
    private double calories;                                     // optional for user.
    private ArrayList<HashMap<String, String>> ingredients;                     // uses <String, Object> to store the ingredient name-, amount- and unit-strings, but at the same time be able to set the key equal to differnt datatypes
    private ArrayList<Category> categories;                                     // stores the categories of the recipe                                    
    private ArrayList<String> steps;                                            // stores the steps of how to make the recipe

    public Recipe(String name, int numberOfServings, String description , String prepTime, ArrayList<HashMap<String, String>> ingredients, ArrayList<Category> categories, ArrayList<String> steps) {                       // constructor for recipe demands that recipe has a defined name and number of servings
        setName(name);
        setServings(numberOfServings);
        setDescription(description);
        setPrepTime(prepTime);

        this.ingredients = new ArrayList<HashMap<String, String>>();
        for (HashMap<String, String> ingredient : ingredients) {
            addIngredient(ingredient.get("name"), ingredient.get("amount"), ingredient.get("unit"));
        }
        nullOrEmpty(ingredients);

        this.categories = new ArrayList<Category>();
        for (Category category : categories) {
            addCategory(category);
        }
        nullOrEmpty(categories);

        this.steps = new ArrayList<String>();
        for (String step : steps) {
            addStep(step);
        }
        nullOrEmpty(steps);
    }

    public String getName() {
        return name;                                                            // returns name of recipe
    } 

    public String getDescription() {
        return description;                                                     // returns description of recipe
    }

    public String getPrepTime() {
        return prepTime;                                                        // returns prepTime
    }
    
    public int getServings() {
        return numberOfServings;                                                // returns number of servings
    }

    public double getCal() {
        return calories;                                                        // returns calories
    }

    public ArrayList<HashMap<String, String>> getIngredients() {
        return new ArrayList<HashMap<String, String>>(ingredients);             // returns ingredients
    }

    public ArrayList<Category> getCategories() {
        return new ArrayList<Category>(categories);                             // returns categories
    }

    public ArrayList<String> getSteps() {
        return new ArrayList<String>(steps);                                    // returns steps
    }

    public void setName(String name) {
        numbersOrSpecials(name);                                                // checks if name is valid
        this.name = name.toUpperCase();                                         // sets name of recipe
    }

    public void setServings(int numberOfServings) {
        negativeOrZero((double) numberOfServings);                              // checks if number of servings is valid
        this.numberOfServings = numberOfServings;                               // sets number of servings
    }

    public void setCalories(double calories) {   
        negative(calories);                                                     // checks if calories are valid
        this.calories = calories;                                               // sets the total calories of the recipe
    }

    public void setDescription(String description) {                            // sets description of recipe
        this.description = description;                                         
    }

    private void setPrepTime(String time) {
        nullOrEmpty(time);  
        validTime(time);                         // prep time in hours
        prepTime = time;                                                        // sets
    }

    public void addIngredient(String name, String amount, String unit) {    
        HashMap<String, String> ingredient = new HashMap<String, String>();     // Creates a hashmap called ingredient that stores different properties of the ingredient
        
        ingredient.put("name", capitalize(name));                               // adds the name of the ingredient to the ingredient hashmap
        ingredient.put("amount", amount);                                       // adds the amount key, value pair to the ingrdient hashmap . describes the amount of the ingredient
        ingredient.put("unit", unit.toLowerCase());                             // adds the "unit" key, value pair to the ingredient hashmap. describes the unit of the ingredient
        
        validateIngredient(ingredient, ingredients);                            // checks if ingredient is valid
        ingredients.add(ingredient);                                            // adds the ingredient to the list of ingredients
    }

    public void addCategory(Category category) { 
        if(categories.contains(category)){                                      // if the category already exists in the recipe
            return;                                                             // returns to avoid adding the category again
        }          
        categories.add(category);                                               // adds category to list of categorys in recipe
        category.addRecipe(this);                                               // adds recipe to list of recipes in category. establishes the n to n relationship between category and recipe
    }

    public void addStep(String step) {
        nullOrEmpty(step);                                                      // checks if step is valid
        steps.add(capitalize(step.trim()));                                            // adds step to list of steps in recipe
    }
}