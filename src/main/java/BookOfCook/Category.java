package BookOfCook;

import java.util.ArrayList;

public class Category extends Validator {
    private String name;                                            // name of category
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();    // recipes in the category

    public Category(String name) {                                  // constructor
        setName(name);                                              // set name of category
    }

    private void setName(String name) {                             // set name of category
        nullOrEmpty(name);                                          // check if name is null or empty
        numbersOrSpecials(name);                                    // check if name contains numbers or special characters
        this.name = name;                                           // set name of category
    }

    public String getName() {                                       // get name of category
        return name;                                                // return name of category
    }

    public ArrayList<Recipe> getRecipes() {                         // get recipes in category
        return new ArrayList<Recipe>(recipes);                      // return recipes in category
    }

    public void addRecipe(Recipe recipe) {                          // add recipe to category
        if (recipes.contains(recipe)) {                             // checks if recipes field already contains recipe
            return;                                                 // return if recipes field already contains recipe
        }                                                           //  else
        recipes.add(recipe);                                        // add recipe to recipes field
        recipe.addCategory(this);                                   // add category to recipe
    }

    public void removeRecipe(Recipe recipe) {                       // remove recipe from category
        if (!recipes.contains(recipe)) {                            // checks if recipes field does not contain recipe
            return;                                                 // return if recipes field does not contain recipe
        }
        recipes.remove(recipe);                                     // remove recipe from recipes field
    }

    @Override
    public String toString() {                                      // toString method
        return name;
    }
}
