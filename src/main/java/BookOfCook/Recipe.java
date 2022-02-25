package BookOfCook;

import java.util.*;

public class Recipe {
    private String name;                                                                                    //name of recipe
    private int numberOfServings;                                                                           //number persons this recipe serves        
    private double calories;                                                                                //total calories in recipe
    private double caloriesPerPerson;                                                                       //calories per person                                                          
    private ArrayList<HashMap<String,Object>> Ingredients = new ArrayList<HashMap<String,Object>>();        //uses <String, Object> to store the ingredient name-, amount- and unit-strings, but at the same time be able to set the key equal to differnt datatypes
    private ArrayList<Category> categories = new ArrayList<Category>();                                     //stores the categories of the recipe                                    
    private ArrayList<String> steps = new ArrayList<String>();  

    //*CONSTRUCTOR                                                                                          
    public Recipe(String name, int numberOfServings) {                      //constructor for recipe demands that recipe has a defined name and number of servings
        this.name = name;                                                   //sets name of recipe
        this.numberOfServings = numberOfServings;                           //sets number of servings so we can do logical operations linked to scaling of the recipe
    }

    //*GETTERS               
    //get name of recipe   
    public String getName() {
        return name;                                                        //returns name of recipe
    }    

    //*CHANGING NAME
    //change name of recipe
    public void setName(String name) {
        this.name = name;                                                   //sets name of recipe if you want to change name
    }

    //*ADDING, REMOVING AND INGREDIENTS
    //add ingredient to list of ingredients in recipe
    public void addIngredient(String name, double amount, String unit) {    
        HashMap<String,Object> ingredient = new HashMap<String,Object>();   //Creates a hasmap called ingredient that stores different properties of the ingredient
        ingredient.put("name", name);                                       //adds the name of the ingredient to the ingredient hashmap
        ingredient.put("amount", amount);                                   //adds the amount key, value pair to the ingrdient hashmap . describes the amount of the ingredient
        ingredient.put("unit", unit);                                       //adds the "unit" key, value pair to the ingredient hashmap. describes the unit of the ingredient
        Ingredients.add(ingredient);                                        //adds the ingredient to the list of ingredients
    }

    // remove ingredient
    public void removeIngredient(String name) {                 
        for (int i = 0; i < Ingredients.size(); i++) {                      //loops through all ingredients
            if (Ingredients.get(i).get("name").equals(name)) {              //looks for a ingredient that mathces the name of the element to be removed
                Ingredients.remove(i);                                      //removes the ingredient if it matches
                break;                                                      //breaks if match found so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    //change ingredient in ingredients list
    public void changeIngredient(String name, double amount, String unit) {
        for (int i = 0; i < Ingredients.size(); i++) {                      //loops through all ingredients
            if (Ingredients.get(i).get("name").equals(name)) {              //if ingredient matches name
                Ingredients.get(i).put("amount", amount);                   //changes amount to new amount
                Ingredients.get(i).put("unit", unit);                       //changes unit to new unit
                break;  	                                                //breaks so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    //change ingredient name to new name
    public void changeIngredientName(String name, String newName) {
        for (int i = 0; i < Ingredients.size(); i++) {                      //loops through all ingredients
            if (Ingredients.get(i).get("name").equals(name)) {              //if ingredient matches name of ingredient to be changed
                Ingredients.get(i).put("name", newName);                    //changes name to new name
                break;  	                                                //breaks so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    //*SETTING AND CHANGING CALORIES
    //set calories
    public void setCalories(double calories) {                          
        this.calories = calories;                                           //sets the total calories of the recipe
        setCaloriesPerPerson();                                             //sets calories per person based on the new value of calories and number of servings
    }

    //set calories per person
    public void setCaloriesPerPerson() {          
        this.caloriesPerPerson = calories / numberOfServings;               //sets calories per person based on the new value of calories and number of servings
    }

    //*SCALING AND CHANGING SERVINGS
    //change number of servings
    public void changeNumberOfServings(int newNumberOfServings) {   
        numberOfServings = newNumberOfServings;                             //changes number of servings to new number of servings
    }

    //when numbers of servings change, scale amounts
    public void scaleRecipe(int newNumberOfServings) {  
        double ratio = newNumberOfServings / numberOfServings;              //the ratio describes how many times the recipe has been scaled

        for (int i = 0; i < Ingredients.size(); i++) {                      //loops through all ingredients
            double amount = (double) Ingredients.get(i).get("amount");      //gets amount of every ingredient as a double 
            amount = amount * ratio;                                        //scales amount of ingredient by multiplying itself with the ratio 
            Ingredients.get(i).put("amount", amount);                       //sets the new amount of ingredient i in the recipe
        }

        setCalories(calories * ratio);                                      //scales calories by multiplying itself with the ratio
        numberOfServings = newNumberOfServings;                             //updates value of number of servings to the new value
        setCaloriesPerPerson();                                             //sets calories per person based on the new value of number of servings
    }

    //*CATEGORY METHODS
    //adds category to recipe and vice versa
    public void categorizeRecipe(Category category) {                   
        categories.add(category);                                           //adds category to list of categorys in recipe
        category.addRecipe(this);                                           //adds recipe to list of recipes in category. establishes the n to n relationship between category and recipe
    }

    //*ADD STEPS TO RECIPE
    //add step to recipe steps
    public void addStep(String step) {
        steps.add(step);
    }

    //*PARSED RECCIPE
    //writes parsed string that can be written to txt file
    public String parsedRecipe() {
        String parsedRecipe = "";                                                               //Creates empty string that will be built up
        String recipeSection = "============================================\n";                //Creates a FATLINE to separate different sections.

        parsedRecipe += recipeSection;                                                          //adds FATLINE to parsed recipe start                                        
        parsedRecipe += name.toUpperCase() + "\n\n";                                            //adds name of recipe to parsed recipe
        parsedRecipe += "Oppskriften er beregnet på: " + numberOfServings + " personer\n\n";    //adds number of servings to parsed recipe
        parsedRecipe += "Calories: " + calories + "\n";                                         //adds calories to parsed recipe
        parsedRecipe += "Calories per person: " + caloriesPerPerson + "\n\n";                   //adds calories per person to parsed recipe
        parsedRecipe += "Ingredients: \n";                                                      //adds ingredients section for parsed recipe

        for (int i = 0; i < Ingredients.size(); i++) {                                          //loops through all ingredients
            parsedRecipe += "  " + Ingredients.get(i).get("name") + ": " + Ingredients.get(i).get("amount") + " " + Ingredients.get(i).get("unit") + "\n"; //adds indented ingredient to parsed recipe
        }

        parsedRecipe += "\n";                                                                   //adds new line to parsed recipe

        parsedRecipe += "Categories: \n";                                                       //adds categories section for parsed recipe
        for (int i = 0; i < categories.size(); i++) {                                           //loops through all categories
            parsedRecipe += "  " + categories.get(i).name + "\n";                               //adds category to parsed recipe
        }

        parsedRecipe += "\n";                                                                   //adds new line to parsed recipe

        for (int i = 0; i < steps.size(); i++) {                                                //loops through all steps
            parsedRecipe += "\n" + steps.get(i);                                                //adds step to parsed recipe
        }

        parsedRecipe += "\n";                                                                   //adds new line to parsed recipe

        parsedRecipe += recipeSection + "\n";                                                   //adds FATLINE to parsed recipe end and an extra new line

        return parsedRecipe;                                                                    //returns parsed recipe
    }

    //*TOSTRING METHOD
    @Override
    public String toString() {
        String ingredientList = "";
        for (int i = 0; i < Ingredients.size(); i++) {
            ingredientList += "\n" + Ingredients.get(i).get("name") + ": " + Ingredients.get(i).get("amount") + " " + Ingredients.get(i).get("unit");
        }
        return "Recipe: " + name + " serves " + numberOfServings + " people. It contains " + ingredientList + "\nand is categorized as: " + categories + "\nCalories: " + calories + " kcal" + "\nwhich is " + caloriesPerPerson + " kcal per person" + "\n" ;
    }
}