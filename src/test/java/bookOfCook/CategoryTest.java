package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import BookOfCook.Category;
import BookOfCook.Recipe;

public class CategoryTest {
     //JUnit tests for Cookbook class
     private Category category1;
     private Recipe recipe1;
 
     @BeforeEach
     public void setup() {
        category1 = new Category("Category1");
        recipe1 = new Recipe("Milkshake", 1);
     }
 
     @Test
     @DisplayName("Test add recipe and recipeAmount")
     public void testAddRecipe() {
        category1.addRecipe(recipe1);
        assertTrue(category1.getRecipes().contains(recipe1));
        category1.removeRecipe(recipe1);
        assertFalse(category1.getRecipes().contains(recipe1));
}
}
