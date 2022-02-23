
package BookOfCook;

import java.util.*;
import java.io.*;

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

    //*GETTERS
    public String getName() {
        return name;
    }

    public int getRecipeAmount() {
        return recipeAmount;
    }

    //*WRITE TO FILE .txt
    //write parsed recipe to file
    public void writeToFile() {
        try {
            FileWriter fw = new FileWriter(name + ".txt");
            for (Recipe r : recipes) {
                fw.write(r.parsedRecipe());
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //*TOSTRING METHOD
    @Override
    public String toString() {
        String recipelist = "";
        for (int i = 0; i < recipes.size(); i++) {
            recipelist += "\n" + recipes.get(i).getName();
        }
        return "Cookbook: " + name + " has " + recipeAmount + " recipes. those are recipes for: " + recipelist + "\n";
    }

    //main method for testing
    public static void main(String[] args) {
        Cookbook cookbook = new Cookbook("Cookbook");
        Recipe recipe1 = new Recipe("Recipe1", 2);
        Recipe recipe2 = new Recipe("Recipe2", 4);
        Recipe recipe3 = new Recipe("Recipe3", 1);
        cookbook.addRecipeToCookbook(recipe1);
        cookbook.addRecipeToCookbook(recipe2);
        cookbook.addRecipeToCookbook(recipe3);
        System.out.println(cookbook);

        cookbook.writeToFile();
    }
}
