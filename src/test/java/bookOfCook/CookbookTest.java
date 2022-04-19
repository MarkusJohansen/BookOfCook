package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import BookOfCook.Cookbook;
import BookOfCook.Recipe;

public class CookbookTest {
    //JUnit tests for Cookbook class
    private Cookbook cookbook = new Cookbook("Book");
    private Recipe recipe = new Recipe("Milkshake", 3);
    private Recipe recipe2 = new Recipe("Pizza", 2);

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
        assertFalse(cookbook.getRecipes().contains(recipe2));
    }

    @Test
    @DisplayName("Test remove recipe")
    public void recipeRemoval() {
        cookbook.addRecipe(recipe);
        cookbook.addRecipe(recipe2);

        //check if throw exception if recipe not in cookbook
        cookbook.removeRecipe(recipe2);
        assertThrows(IllegalArgumentException.class, () -> cookbook.removeRecipe(recipe2));

        assertFalse(cookbook.getRecipes().contains(recipe2));
        assertTrue(cookbook.getRecipes().contains(recipe));
    }

    @Test
    @DisplayName("Test Constructor")
    public void testConstructor() {
        Cookbook cookbook = new Cookbook("cookbook");
        assertTrue(cookbook.getRecipes().isEmpty());
    }

    //! trenger vi setname funksjonen?
    @Test
    @DisplayName("Test set name og get name")
    public void testSetName() {
        cookbook.setName("cookbook");
        assertTrue(cookbook.getName().equals("cookbook"));
    }

    //! skjønner ikke hva denne funksjonen gjør, kan du forklare? evt endre navn
    @Test
    @DisplayName("Test getSortedRecipesAllCategories")
    public void testGetSortedRecipesAllCategories() {
        cookbook.addRecipe(recipe);
        cookbook.addRecipe(recipe2);
        assertTrue(cookbook.getSortedRecipesAllCategories().contains(recipe));
        assertTrue(cookbook.getSortedRecipesAllCategories().contains(recipe2));
    }

    //! skjønner ikke hva denne funksjonen gjør, kan du forklare? evt endre navn
    @Test
    @DisplayName("Test categCollect")
    public void testCategCollect() {
        cookbook.addRecipe(recipe);
        cookbook.addRecipe(recipe2);
        assertTrue(cookbook.getCategCollect().contains(recipe.getCategory()));
        assertTrue(cookbook.getCategCollect().contains(recipe2.getCategory()));
    }

    @Test
    @DisplayName("Test searchRecipes")
    public void testSearchRecipe() {
        cookbook.addRecipe(recipe);
        cookbook.addRecipe(recipe2);
        assertFalse(cookbook.searchRecipes("Pizza").contains(recipe));
        assertTrue(cookbook.searchRecipes("Pizza").contains(recipe2));
    }

    //?ikke satt opp properly
    @Test
    @DisplayName("Test Save")
    public void testSave() {
        cookbook.addRecipe(recipe);
        cookbook.addRecipe(recipe2);
        cookbook.save();
        assertTrue(cookbook.getRecipes().contains(recipe));
        assertTrue(cookbook.getRecipes().contains(recipe2));
    }

    //?ikke satt opp properly
    @Test
    @DisplayName("Test Load")
    public void testLoad() {
        cookbook.addRecipe(recipe);
        cookbook.addRecipe(recipe2);
        cookbook.save();
        cookbook.load();
        assertTrue(cookbook.getRecipes().contains(recipe));
        assertTrue(cookbook.getRecipes().contains(recipe2));
    }
}
