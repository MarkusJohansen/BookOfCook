package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import BookOfCook.Recipe;

public class RecipeTest {
    //JUnit tests for Recipe class
    @Test
    public void checkRecipeName() {
        Recipe recipe = new Recipe("TeSt ReCiPe", 2);
        assertTrue(recipe.getName().equals("Test recipe".toUpperCase()));
        assertTrue(recipe.getDisplayedName().equals("Test recipe"));
    }
}
