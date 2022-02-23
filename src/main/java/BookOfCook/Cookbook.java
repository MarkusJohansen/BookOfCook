
/*
TODO
- shall writing to file be done in cookbook?
- shall method for updating file be in cookbook?
- where shall category filter be actiated?
- add category filter
*/


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
    
    //*RECIPE ADD AND REMOVE METHODS
    public void addRecipeToCookbook(Recipe recipe) { //add recipe to list of recipes in cookbook
        recipes.add(recipe);
        recipeAmount++;
    }

    public void removeRecipeFromCookbook(Recipe recipe) { //remove recipe from list of recipes in cookbook
        recipes.remove(recipe);
        recipeAmount--;
    }

    //*TOSTRING METHODS
    @Override
    public String toString() {
        return "Cookbook: " + name + " has " + recipeAmount + " recipes. The recipes are " + recipes;
    }

    //*MAIN METHOD
        public static void main(String[] args) {
        Cookbook cookbook = new Cookbook("Cookbook");
        Recipe recipe1 = new Recipe("Recipe 1", 2);
        Recipe recipe2 = new Recipe("Recipe 2", 2);

        //add recipes to cookbook testing 
        System.out.println(cookbook);
        cookbook.addRecipeToCookbook(recipe1);
        cookbook.addRecipeToCookbook(recipe2);
        System.out.println(cookbook);

        //remove recipes from cookbook testing
        cookbook.removeRecipeFromCookbook(recipe1);
        System.out.println(cookbook);
    }
}
