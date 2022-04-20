package BookOfCook;

import java.util.*;

public class Recipe extends Validator{
    private String name, description, prepTime;             // name of recipe that will be dealt with back end, as well as name displayed to user    
    private int numberOfServings;                           // number persons this recipe serves        
    private double calories, CalPerServing;                 // optional for user.
    private ArrayList<HashMap<String,Object>> ingredients;  // uses <String, Object> to store the ingredient name-, amount- and unit-strings, but at the same time be able to set the key equal to differnt datatypes
    private ArrayList<Category> categories;                 // stores the categories of the recipe                                    
    private ArrayList<String> steps;                        // stores the steps of how to make the recipe

    // *CONSTRUCTOR                                                                                          
    public Recipe(String name, int numberOfServings, String description , String prepTime, ArrayList<HashMap<String,Object>> ingredients, ArrayList<Category> categories, ArrayList<String> steps) {              // constructor for recipe demands that recipe has a defined name and number of servings
        setName(name);
        setServings(numberOfServings);
        setDescription(description);
        setPrepTime(prepTime);

        this.ingredients = new ArrayList<HashMap<String,Object>>();
        for (HashMap<String,Object> ingredient : ingredients) {
            addIngredient((String)ingredient.get("name"), (double) ingredient.get("amount"), (String)ingredient.get("unit")); //*Fikk med casting her
        } 

        this.categories = new ArrayList<Category>();
        for (Category category : categories) {
            addCategory(category);
        }

        this.steps = new ArrayList<String>();
        for (String step : steps) {
            addStep(step);
        }
    }

    // !SJEKK HVEM SOM FAKTISK BRUKES
    // *GETTERS               
    // get name of recipe   
    public String getName() {
        return name;                                                // returns name of recipe
    } 

    //get description of recipe
    public String getDescription() {
        return description;                                         // returns description of recipe
    }

    //get prepTime
    public String getPrepTime() {
        return prepTime;                                            // returns prepTime
    }
    
    // get number of servings
    public int getServings() {
        return numberOfServings;                                    // returns number of servings
    }

    // get calories
    public double getCalories() {
        return calories;                                            // returns calories
    }

    // get calories per person
    public double getCalPerServing() {
        return CalPerServing;                                       // returns calories per person
    }

    // get ingredients
    public ArrayList<HashMap<String,Object>> getIngredients() {
        return new ArrayList<HashMap<String,Object>>(ingredients);  // returns ingredients
    }

    // get categories
    public ArrayList<Category> getCategories() {
        return new ArrayList<Category>(categories);                 // returns categories
    }

    // get steps
    public ArrayList<String> getSteps() {
        return new ArrayList<String>(steps);                        // returns steps
    }


    // *SETTERS
    // change name of recipe
    public void setName(String name) {
        numbersOrSpecials(name);                                      // checks if name is valid
        this.name = name.toUpperCase();                             // sets name of recipe
    }

    // change number of servings
    public void setServings(int numberOfServings) {
        negativeOrZero((double) numberOfServings);                            // checks if number of servings is valid
        this.numberOfServings = numberOfServings;                   // sets number of servings
    }

    // set calories
    public void setCalories(double calories) {   
        negative(calories);                                 // checks if calories are valid
        this.calories = calories;                                   // sets the total calories of the recipe
        setCalPerServing();                                         // sets calories per person based on the new value of calories and number of servings
    }

    private void setCalPerServing() {          
        this.CalPerServing = calories / numberOfServings;           // sets calories per person based on the new value of calories and number of servings
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrepTime(String time) {                          //prep time in hours
        prepTime = time;                                            // sets
    }


    // *Add and remvoe ingredients
    public void addIngredient(String name, double amount, String unit) {    
        HashMap<String,Object> ingredient = new HashMap<String,Object>();   // Creates a hashmap called ingredient that stores different properties of the ingredient
        
        ingredient.put("name", name.toUpperCase());                         // adds the name of the ingredient to the ingredient hashmap
        ingredient.put("amount", amount);                                   // adds the amount key, value pair to the ingrdient hashmap . describes the amount of the ingredient
        ingredient.put("unit", unit.toLowerCase());                         // adds the "unit" key, value pair to the ingredient hashmap. describes the unit of the ingredient
        
        validateIngredient(ingredient);                                     // checks if ingredient is valid
        ingredients.add(ingredient);                                        // adds the ingredient to the list of ingredients
    }

    public void removeIngredient(String name) {                 
        for (HashMap<String, Object> Ingredient : ingredients) {            // loops through all ingredients
            if (Ingredient.get("name").equals(name.toUpperCase())) {        // looks for a ingredient that mathces the name of the element to be removed
                ingredients.remove(Ingredient);                             // removes the ingredient if it matches
                return;                                                     // return if match found so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    // !needs validation
    // establishes a relation between a category and a recipe
    public void addCategory(Category category) { 
        if(categories.contains(category)){                                  // if the category already exists in the recipe
            return;                                                         // returns to avoid adding the category again
        }          
        categories.add(category);                                           // adds category to list of categorys in recipe
        category.addRecipe(this);                                           // adds recipe to list of recipes in category. establishes the n to n relationship between category and recipe
    }

    // !needs validation
    // removes relation between a category and a recipe
    public void removeCategory(Category category) {
        if(!categories.contains(category)){                                 // if the category does not exist in the recipe
            return;                                                         // returns to avoid removing non existing fcategory                 
        }
        categories.remove(category);                                        // removes category from list of categories in recipe
        category.removeRecipe(this);                                        // removes recipe from list of recipes in category. 
    }

    // add step to recipe steps
    public void addStep(String step) {
        nullOrEmpty(step);                                                 // checks if step is valid
        steps.add(capitalize(step));                                        // adds step to list of steps in recipe
    }

    // remove step from recipe steps by index
    public void removeStep(int step) {
        validIndex(steps, step);                                            //! error her removes step from list of steps in recipe                                                          
        steps.remove(step);                                                 // removes step from list of steps in recipe
    }


    //! *SCALE RECIPE har ikke støtte men burde ha det, da dette er det mest mattematisk logiske vi dealer med
    // when numbers of servings change, scale amounts
    public void scale(int newNumberOfServings) { 
        negativeOrZero((double) newNumberOfServings);                                 // validates new number of servings¨
        double ratio = newNumberOfServings / numberOfServings;              // the ratio describes how many times the recipe has been scaled

        for (int i = 0; i < ingredients.size(); i++) {                      // loops through all ingredients
            double amount = (double) ingredients.get(i).get("amount");      // gets amount of every ingredient as a double 
            amount = amount * ratio;                                        // scales amount of ingredient by multiplying itself with the ratio 
            ingredients.get(i).put("amount", amount);                       // sets the new amount of ingredient i in the recipe
        }

        setCalories(calories * ratio);                                      // scales calories by multiplying itself with the ratio
        setServings(newNumberOfServings);                           // sets new number of servings
        setCalPerServing();                                             // sets calories per person based on the new value of number of servings
    }


    //! *TOSTRING METHOD brukes ikke
    @Override
    public String toString() {
        return "Recipe [name=" + name + "]";
    }
}