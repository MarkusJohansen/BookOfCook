package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import BookOfCook.Cookbook;
import BookOfCook.Recipe;

public class CookbookTest {
    //JUnit tests for Cookbook class
    private Cookbook cookbook;
    private Recipe recipe;
    private Recipe recipe2;

    @BeforeEach
    public void setup() {
        Cookbook cookbook = new Cookbook("cookbook");
        Recipe recipe = new Recipe("Test recipe", 2);
        Recipe recipe2 = new Recipe("Test recipeeeeee", 2);
    }

    @Test
    @DisplayName("Test add recipe")
    public void RecipeContainment() {
        cookbook.addRecipe(recipe);
        assertTrue(cookbook.getRecipes().contains(recipe));
    }

    @Test
    @DisplayName("Test remove recipe")
    public void recipeRemoval() {
        cookbook.addRecipe(recipe);
        cookbook.addRecipe(recipe2);
        cookbook.removeRecipe(recipe);

        assertFalse(cookbook.getRecipes().contains(recipe));
        assertTrue(cookbook.getRecipes().contains(recipe2));
    }
}
