package BookOfCook;

import java.util.ArrayList;

public class Category implements recipeContainer {
    // *FIELDS
    private String name;                                            // name of category
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();    // recipes in the category

    // *CONSTRUCTOR
    public Category(String name) {
        this.name = name;
    }

    // *GETTERS
    public String getName() {
        return name;
    }

    public ArrayList<Recipe> getRecipes() {
        return new ArrayList<Recipe>(recipes);
    }

    //! TRENGER VI DENNE? VI BRUKER DEN ALDRI
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

    @Override
    public String toString() {
        return name;
    }
}
