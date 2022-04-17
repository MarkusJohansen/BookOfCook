package BookOfCook;

import java.util.ArrayList;

public class Category implements recipeContainer {
    // *FIELDS
    private String name;                                            // name of category
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();    // recipes in the category

    // *CONSTRUCTOR
    public Category(String name) {
        this.name = name.toLowerCase();
    }

    // *GETTERS
    public String getName() {
        return name;
    }

    public ArrayList<Recipe> getRecipes() {
        return new ArrayList<Recipe>(recipes);
    }

    public int getAmount() {
        return recipes.size();
    }

    // *METHODS
    public void addRecipe(Recipe recipe) {
        if (recipes.contains(recipe)) {     // checks if recipes already contains recipes
            return;
        }

        recipes.add(recipe);
        recipe.addCategory(this);
    }

    public void removeRecipe(Recipe recipe) {
        if (!recipes.contains(recipe)) {
            return;
        }
        recipes.remove(recipe);
        recipe.removeCategory(this);
    }

    // tostring method for category
    /* @Override
    public String toString() {
        String r = "";
        for (int i = 0; i < recipes.size(); i++) {
            r += "\n" + recipes.get(i).getName();
        }
        return "Category: " + name + " has " + recipes.size() + " recipes. those are recipes for: " + r + "\n";
    } */

    @Override
    public String toString() {
        return name;
    }
    
    public static void main(String[] args) {
        Category italiensk = new Category("italiensk");
        Recipe pasta_carbonara = new Recipe("pasta carbonara", 3);

        italiensk.addRecipe(pasta_carbonara);

        System.out.println(italiensk);

        System.out.println(pasta_carbonara);
    }
}
