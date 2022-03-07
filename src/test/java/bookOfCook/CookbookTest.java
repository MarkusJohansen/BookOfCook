package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import BookOfCook.Cookbook;
import BookOfCook.Recipe;

public class CookbookTest {
    //JUnit tests for Cookbook class

    //Checks if cookbook contains recipe
    @Test
    public void checkIfRecipeExists() {
        Cookbook cookbook = new Cookbook("cookbook");
        Recipe recipe = new Recipe("Test recipe", 2);
        cookbook.addRecipeToCookbook(recipe);
        assertTrue(cookbook.getRecipes().contains(recipe));
    }

    //checks if cookbook contains recipe after removal
    @Test
    public void checkIfRecipeDoesNotExist() {
        Cookbook cookbook = new Cookbook("cookbook");

        Recipe recipe = new Recipe("Test recipe", 2);
        Recipe recipe2 = new Recipe("Test recipeeeeee", 2);

        cookbook.addRecipeToCookbook(recipe);
        cookbook.addRecipeToCookbook(recipe2);
        cookbook.removeRecipeFromCookbook(recipe);

        assertFalse(cookbook.getRecipes().contains(recipe));
        assertTrue(cookbook.getRecipes().contains(recipe2));
    }
}
