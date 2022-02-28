
package BookOfCook;

import java.util.*;
import java.io.*;

public class Cookbook {
    private String name;
    private int recipeAmount;
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();


    //*CONSTRUCTOR
    //constructor for cookbook demands name and is always empty at creation
    public Cookbook(String name) {
        this.name = name;                                               //name of cookbook
        this.recipeAmount = 0;                                          //amount of recipes in cookbook
    }


    //*RECIPE ADD AND REMOVE METHODS
    //adds recipe to cookbook
    public void addRecipeToCookbook(Recipe recipe) {
        if (recipes.contains(recipe)) {                                 //checks if recipe already exists in cookbook
            System.out.println("Recipe already exists in cookbook");    //describes problem in console  
            return;                                                     //return to prevent adding already existing recipe again
        }

        for(Recipe r : recipes){                                                        //loops through recipes in cookbook           
            if(r.getName().equals(recipe.getName())){                                   //checks there is another recipe with identical name in cookbook  
                System.out.println("Recipe with same name already exists in cookbook"); //describes problem in console
                return;                                                                 //return to prevent adding recipe with same name again
            }
        }

        recipes.add(recipe);                                                            //adds recipe to cookbook
        recipeAmount++;                                                                 //updates amount of recipes in cookbook
    }

    //removes recipe from cookbook and updates recipe amount
    public void removeRecipeFromCookbook(Recipe recipe) {
        if (!recipes.contains(recipe)) {                                                //checks if recipe exists in cookbook                      
            return;                                                                     //return to prevent removing non-existing recipe
        }
        recipes.remove(recipe);                                                         //removes recipe from cookbook
        recipeAmount--;                                                                 //updates amount of recipes in cookbook
    }


    //*GETTERS
    //? er disse nødvendige
    //returns cookbook name
    public String getName() {
        return name;                                //returns cookbook name
    }

    //returns amount of recipes in cookbook
    public int getRecipeAmount() {
        return recipeAmount;                        //returns amount of recipes in cookbook
    }


    //*WRITE TO FILE .txt
    //write recipes in cookbook as parsed strings to file
    public void writeToFile() {
        try {                                                       //prøv å skrive filen
            FileWriter fileWriter = new FileWriter(name + ".txt");  //lager et filskriver objekt som spesifiserer at filen skal hete "navnet til cookbook".txt

            for (Recipe recipe : recipes) {                         //looper gjennom alle matoppskrifter i cookbook      
                fileWriter.write(recipe.parsedRecipe());            //skriver matoppskriften til filen som en parset string gjennom parsedRecipe() metoden
            }

            fileWriter.close();                                     //lukker skriveren og indikerer at filen er ferdigskrevet.
        }
        
        catch (IOException exception) {                             //dersom det oppstår en feil ved skriving av filen fanger den opp feilen
            exception.printStackTrace();                            //skriver ut feilen til konsollen
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


    //*MAIN METHOD
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
