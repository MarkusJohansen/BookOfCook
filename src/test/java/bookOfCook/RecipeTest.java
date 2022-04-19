package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import BookOfCook.Category;
import BookOfCook.Recipe;

public class RecipeTest {
    private Recipe recipe;
    private Recipe recipe2;
    private Recipe recipe3;

    @BeforeEach 
    public void setup() {
        //set up before each test
        Recipe recipe = new Recipe("Milkshake", 3);
        Recipe recipe2 = new Recipe("Pizza", 2);
        Recipe recipe3 = new Recipe("Burger", 2);
    }

    @Test
    @DisplayName("Test recipe name")
    public void checkRecipeName() {
        recipe = new Recipe("Milkshake", 3); //!setup fungerer ikke
        recipe.setName("Fransk løk suppe");
        assertTrue(recipe.getName().equals("Fransk løk suppe".toUpperCase()));

        //test if invalid recipename throws exception
        assertThrows(IllegalArgumentException.class, () -> recipe.setName("Fransk123"));
        assertThrows(IllegalArgumentException.class, () -> recipe.setName("Fransk "));
        assertThrows(IllegalArgumentException.class, () -> recipe.setName(" Fransk"));
        assertThrows(IllegalArgumentException.class, () -> recipe.setName("Fransk#@%&!"));

        assertFalse(recipe.getName().equals("Fransk #@%&!".toUpperCase()));
    }

    @Test
    @DisplayName("Test recipe servings")
    public void checkRecipeServings() {
        assertTrue(recipe.getServings() == 3);
        recipe.setServings(2);
        assertTrue(recipe.getServings() == 2);

        //test if invalid servings throws exception
        assertThrows(IllegalArgumentException.class, () -> recipe.setServings(0));
        assertThrows(IllegalArgumentException.class, () -> recipe.setServings(-1));
    }

    @Test
    @DisplayName("Test recipe description")
    public void checkRecipeDescription() {
        assertTrue(recipe.getDescription() == null);
        recipe.setDescription("Milkshake is a drink made from milk and sugar.");
        assertTrue(recipe.getDescription() == "Milkshake is a drink made from milk and sugar.");
    }

    @Test
    @DisplayName("Test Calorie methods")
    public void checkCalorieMethods() {
        assertTrue(recipe.getCalories() == 0);
        recipe.setCalories(100);
        assertTrue(recipe.getCalories() == 100);
        recipe.setServings(2);
        assertTrue(recipe.getCalPerServing() == 50);
        assertThrows(IllegalArgumentException.class, () -> recipe.setCalories(-1));
        assertThrows(IllegalArgumentException.class, () -> recipe.setCalories(0));
    }

    @Test
    @DisplayName("Test scaling of recipe")
    public void checkScaling() {
        recipe.setServings(2);
        recipe.setCalories(100);
        recipe.addIngredient("Milk", 2, "l");
        assertTrue(recipe.getCalories() == 100);
        assertTrue(recipe.getServings() == 2);
        assertTrue(recipe.getIngredientAmount("Milk") == 2);

        recipe.scale(4);
        assertTrue(recipe.getServings() == 4);
        assertTrue(recipe.getCalories() == 200);
        assertTrue(recipe.getCalPerServing() == 100);
        assertTrue(recipe.getIngredientAmount("Milk") == 4);

        //check illegal new servings
        assertThrows(IllegalArgumentException.class, () -> recipe.scale(0));
        assertThrows(IllegalArgumentException.class, () -> recipe.scale(-1));
        assertThrows(IllegalArgumentException.class, () -> recipe.scale(1));
    }

    @Test
    @DisplayName("Test recipe ingredients")
    public void checkRecipeIngredients() {
        recipe.addIngredient("Milk", 2, "l");
        assertTrue(recipe.getIngredients().size() == 1);
        recipe.addIngredient("Sugar", 1, "tsk");
        assertTrue(recipe.getIngredients().size() == 2);

        assertTrue(recipe.getIngredients().get(0).getName().equals("Milk"));
        assertTrue(recipe.getIngredients().get(0).getAmount() == 2);
        assertTrue(recipe.getIngredients().get(0).getUnit().equals("l"));

        //test if invalid ingredient throws exception
        assertThrows(IllegalArgumentException.class, () -> recipe.addIngredient("Milk", -1, "l"));
        assertThrows(IllegalArgumentException.class, () -> recipe.addIngredient("Milk", 0, "l"));
        assertThrows(IllegalArgumentException.class, () -> recipe.addIngredient("Milk", 2, ""));
        assertThrows(IllegalArgumentException.class, () -> recipe.addIngredient("Milk123", 2, "l"));
        assertThrows(IllegalArgumentException.class, () -> recipe.addIngredient("Milk", 2, "l123"));
        assertThrows(IllegalArgumentException.class, () -> recipe.addIngredient("Milk!#¤%", 2, "l"));
        assertThrows(IllegalArgumentException.class, () -> recipe.addIngredient("Milk", 2, "l#¤%&"));

        recipe.removeIngredient("Milk");
        assertTrue(recipe.getIngredients().size() == 1);
        assertTrue(recipe.getIngredients().get(0).getName().equals("Sugar"));
    }
    
    @Test
    @DisplayName("Test Category methods for recipe")
    public void checkCategoryMethods() {
        Category category = new Category("italiensk");
        recipe.addCategory(category);
        assertTrue(category.getRecipes().contains(recipe));
        assertTrue(recipe.getCategories().contains(category));
        recipe.removeCategory(category);
        assertFalse(category.getRecipes().contains(recipe));
        assertFalse(recipe.getCategories().contains(category));

       //?vi har dobbelt opp med metoder her. ser at vi har add/remove category metoder i recipe og vice versa i category
    }


    @Test
    @DisplayName("Test recipe instructions")
    public void checkRecipeInstructions() {
        assertTrue(recipe.getInstructions().contains("Mix"));
        assertTrue(recipe.getInstructions().contains("Bake"));
    }
}
