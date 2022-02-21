package BookOfCook;

import java.util.ArrayList;

public class Cookbook {
    String name;
    int recipeAmount;
    ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    //*CONSTRUCTOR
    public Cookbook(String name) {
        this.name = name;
        this.recipeAmount = 0;
    }

    //*NAME
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //*RECIPE AMOUNT
    public int getRecipeAmount() {
        return recipeAmount;
    }
    public void updateRecipeAmount() {
        this.recipeAmount = recipes.size();
    }

    //*RECIPE GETTERS
    public ArrayList<Recipe> getAllRecipes() {
        return recipes;
    }
    
    public Recipe getRecipe(int recipeNumber){
        return recipes.get(recipeNumber);
    }

    //*ADDING AND REMOVING RECIPES
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipes.indexOf(recipe));
    }

    public void removeRecipe(int recipeNumber){
        recipes.remove(recipeNumber);
    }

}
