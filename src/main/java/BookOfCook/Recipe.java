/*
TODO: Possible scenarios:
- Forskjellige capitalisering av samme ingrediens
- legge til samme ingrediens to ganger
- negativt antall calorier, servings
- Illegal chars in Recipe name
- Illegal chars in Ingredient name
- Kun tall i ingrdent amount
- ikke tall i units

?MULIGE LØSNINGER:
- ikke skille mellom Tomat, tomat og tOmAt
- solve issue with illegal symbols by regex
*/

package BookOfCook;

import java.util.*;

public class Recipe {
    private String name;                                                                                    //name of recipe
    private int numberOfServings;                                                                           //number persons this recipe serves        
    private double calories;                                                                                //total calories in recipe
    private double caloriesPerPerson;                                                                       //calories per person                                                          
    private ArrayList<HashMap<String,Object>> Ingredients = new ArrayList<HashMap<String,Object>>();        //uses <String, Object> to store the ingredient name-, amount- and unit-strings, but at the same time be able to set the key equal to differnt datatypes
    private ArrayList<Category> categories = new ArrayList<Category>();                                     //stores the categories of the recipe                                    
    private ArrayList<String> steps = new ArrayList<String>();                                              //stores the steps of how to make the recipe


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
    
    //get number of servings
    public int getNumberOfServings() {
        return numberOfServings;                                            //returns number of servings
    }

    //get calories
    public double getCalories() {
        return calories;                                                    //returns calories
    }

    //get calories per person
    public double getCaloriesPerPerson() {
        return caloriesPerPerson;                                           //returns calories per person
    }

    //get ingredients
    public ArrayList<HashMap<String,Object>> getIngredients() {
        return Ingredients;                                                //returns ingredients
    }

    //get categories
    public ArrayList<Category> getCategories() {
        return categories;                                                 //returns categories
    }

    //get steps
    public ArrayList<String> getSteps() {
        return steps;                                                      //returns steps
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
        for (HashMap<String, Object> Ingredient : Ingredients) {            //loops through all ingredients
            if (Ingredient.get("name").equals(name)) {                      //looks for a ingredient that mathces the name of the element to be removed
                Ingredients.remove(Ingredient);                             //removes the ingredient if it matches
                break;                                                      //breaks if match found so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    //change ingredient in ingredients list
    public void changeIngredient(String name, double amount, String unit) {
        for (HashMap<String, Object> Ingredient : Ingredients) {            //loops through all ingredients
            if (Ingredient.get("name").equals(name)) {                      //if ingredient matches name
                Ingredient.put("amount", amount);                           //changes amount to new amount
                Ingredient.put("unit", unit);                               //changes unit to new unit
                break;  	                                                //breaks so it doesn't look for more matches. saves prossessing power
            }
        }
    }

    //change ingredient name to new name
    public void changeIngredientName(String name, String newName) {
        for (HashMap<String, Object> Ingredient : Ingredients) {            //loops through all ingredients
            if (Ingredient.get("name").equals(name)) {                      //if ingredient matches name of ingredient to be changed
                Ingredient.put("name", newName);                            //changes name to new name
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
    //establishes a relation between a category and a recipe
    public void addCategory(Category category) { 
        if(categories.contains(category)){                                  //if the category already exists in the recipe
            return;                                                         //returns to avoid adding the category again
        }          
        categories.add(category);                                           //adds category to list of categorys in recipe
        category.addRecipe(this);                                           //adds recipe to list of recipes in category. establishes the n to n relationship between category and recipe
    }

    //removes relation between a category and a recipe
    public void removeCategory(Category category) {
        if(!categories.contains(category)){                                 //if the category does not exist in the recipe
            return;                                                         //returns to avoid removing non existing fcategory                 
        }
        categories.remove(category);                                        //removes category from list of categories in recipe
        category.removeRecipe(this);                                        //removes recipe from list of recipes in category. 
    }


    //*STEP METHODS
    //add step to recipe steps
    public void addStep(String step) {
        steps.add(step);                                                    //adds step to list of steps in recipe
    }

    //remove step from recipe steps by index
    public void removeStep(int step) {
        steps.remove(step);                                                //removes step from list of steps in recipe
    }


    //*PARSING RECIPE FOR WRITING TO .txt FILE
    //writes parsed string that can be written to txt file
    public String parsedRecipe() {
        String parsedRecipe = "";                                                               //Creates empty string that will be built up
        String recipeSection = "============================================\n";                //Creates a FATLINE to separate different sections.

        parsedRecipe += recipeSection;                                                          //adds FATLINE to parsed recipe start                                        
        parsedRecipe += name.toUpperCase() + "\n\n";                                            //adds name of recipe to parsed recipe
        parsedRecipe += "Categories:";                                                          //adds categories section for parsed recipe

        for(Category category : categories) {                                                   //loops through all categories
            parsedRecipe += "  " + category.name + ",";                                         //adds category to parsed recipe
        }

        parsedRecipe += "\n";  
        parsedRecipe += "Oppskriften er beregnet på: " + numberOfServings + " personer\n\n";    //adds number of servings to parsed recipe
        parsedRecipe += "Calories: " + calories + "\n";                                         //adds calories to parsed recipe
        parsedRecipe += "Calories per person: " + caloriesPerPerson + "\n\n";                   //adds calories per person to parsed recipe
        parsedRecipe += "Ingredients: \n";                                                      //adds ingredients section for parsed recipe

        for(HashMap<String, Object> Ingredient : Ingredients) {                                                                     //loops through all ingredients
            parsedRecipe += "  " + Ingredient.get("name") + ": " + Ingredient.get("amount") + " " + Ingredient.get("unit") + "\n";  //adds indented ingredient to parsed recipe
        }
        parsedRecipe += "\n";                                                                   //adds new line to parsed recipe

        parsedRecipe += "Steps:";                                                               //adds Steps section for parsed recipe
        for(String step : steps) {                                                              //loops through all steps
            parsedRecipe += "\n* " + step;                                                      //adds step to parsed recipe
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


    //*MAIN METHOD FOR TESTING
    public static void main(String[] args) {
        Recipe recipe = new Recipe("Sukker-pizza", 3);
        Category pizza = new Category("Pizza");
        Category italian = new Category("Italian");
        Category dessert = new Category("Dessert");

        
        //adds base ingredients and calories to recipe
        recipe.setCalories(1000);
        recipe.addIngredient("tomater", 100, "gram");
        recipe.addIngredient("sukker", 6, "ss");
        recipe.addIngredient("vann", 3, "l");
        recipe.addIngredient("mel", 100 , "kg");
        recipe.addIngredient("pizza-kulsyre", 1, "stk");
        System.out.println(recipe.parsedRecipe());


        //testing av skalering og endring av ingrdiens
        recipe.scaleRecipe(4);
        recipe.changeIngredient("mel", 8, "g");
        recipe.changeIngredientName("tomater", "tomat");
        System.out.println(recipe.parsedRecipe());

        //tester fjerning av ingredient og legge til kategorier
        recipe.removeIngredient("tomat");
        recipe.addCategory(pizza);
        recipe.addCategory(italian);
        recipe.addCategory(dessert);
        recipe.removeCategory(dessert);
        System.out.println(recipe.parsedRecipe());


        //legger til steg in recipe
        recipe.addStep("Putt tomater i en kasse");
        recipe.addStep("Putt sukker i en kasse");
        recipe.addStep("Putt vann i en kasse");
        recipe.addStep("Putt mel i en kasse");
        recipe.addStep("Kjøh!");
        recipe.addStep("ferri");
        recipe.removeStep(0);
        System.out.println(recipe.parsedRecipe());
    }
}