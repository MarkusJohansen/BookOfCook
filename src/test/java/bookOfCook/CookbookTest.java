package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import BookOfCook.Category;
import BookOfCook.Cookbook;
import BookOfCook.Recipe;

public class CookbookTest {
    //JUnit tests for Cookbook class
    private Cookbook cookbook;
    private Recipe recipe1;
    private Recipe recipe2;
    private Category category1 = new Category("Category1");
    Category category2 = new Category("Category2");

    @BeforeEach
    public void setup() {
        cookbook = new Cookbook("Book");
        recipe1 = new Recipe("Milkshake", 3);
        recipe2 = new Recipe("Pizza", 2);
    }

    @Test
    @DisplayName("Test add recipe and recipeAmount")
    public void RecipeContainment() {
        cookbook.addRecipe(recipe1);
        assertTrue(cookbook.getRecipes().contains(recipe1));
        assertFalse(cookbook.getRecipes().contains(recipe2));
        cookbook.addRecipe(recipe2);
        assertTrue(cookbook.getAmount() == 2);
        assertTrue(cookbook.getRecipes().size() == 2);

        //throw exceptions if recipe is duplicate
        assertThrows(IllegalArgumentException.class, () -> cookbook.addRecipe(recipe1));
        assertThrows(NullPointerException.class, () -> cookbook.addRecipe(null));
        assertThrows(IllegalArgumentException.class, () -> cookbook.addRecipe(new Recipe("Milkshake", 2))); 
    }

    @Test
    @DisplayName("Test remove recipe")
    public void recipeRemoval() {
        cookbook.addRecipe(recipe1);
        cookbook.addRecipe(recipe2);

        //check if throw exception if recipe not in cookbook
        cookbook.removeRecipe(recipe2);
        assertThrows(IllegalArgumentException.class, () -> cookbook.removeRecipe(recipe2));

        assertFalse(cookbook.getRecipes().contains(recipe2));
        assertTrue(cookbook.getRecipes().contains(recipe1));
    }

    @Test
    @DisplayName("Test Constructor")
    public void testConstructor() {
        Cookbook cookbook = new Cookbook("cookbook");
        assertTrue(cookbook.getRecipes().isEmpty());
    }

    @Test
    @DisplayName("Test searchRecipes")
    public void testSearchRecipe() {
        cookbook.addRecipe(recipe1);
        cookbook.addRecipe(recipe2);
        assertFalse(cookbook.searchRecipes("Pizza").contains(recipe1));
        assertTrue(cookbook.searchRecipes("Pizza").contains(recipe2));
    }
    //!__________________________________________________________________

    //! trenger vi setname funksjonen?
    //! synes ikke det, vi bruker den vel ikke heller
    @Test
    @DisplayName("Test set name og get name")
    public void testSetName() {
        cookbook.setName("cookbook");
        assertTrue(cookbook.getName().equals("cookbook"));
    }

    //! skjønner ikke hva denne funksjonen gjør, kan du forklare? evt endre navn
    //! funksjonen filtrerer ut recipene etter gitte kategorier
    @Test
    @DisplayName("Test getSortedRecipesAllCategories")
    public void testGetSortedRecipesAllCategories() {
        recipe1.addCategory(category1);
        recipe1.addCategory(category2);
        recipe2.addCategory(category1);
        cookbook.addRecipe(recipe1);
        cookbook.addRecipe(recipe2);
        assertEquals(cookbook.getSortedRecipesAllCategories(new ArrayList<>(Arrays.asList(category1))), new ArrayList<>(Arrays.asList(recipe1, recipe2)));
        assertEquals(cookbook.getSortedRecipesAllCategories(new ArrayList<>(Arrays.asList(category1, category2))), new ArrayList<>(Arrays.asList(recipe1)));
    }

    //! skjønner ikke hva denne funksjonen gjør, kan du forklare? evt endre navn
    //! funksjonen henter alle kategoriene til recipene i kokeboka
    @Test
    @DisplayName("Test categCollect")
    public void testcollectCategories() {
        cookbook.addRecipe(recipe1);
        recipe1.addCategory(category1);
        cookbook.categCollect();
        assertEquals(cookbook.getCategories(), new ArrayList<>(Arrays.asList(category1)));
    }

    //?ikke satt opp properly
    @Test
    @DisplayName("Test Save")
    public void testSave() {
        cookbook.addRecipe(recipe1);
        cookbook.addRecipe(recipe2);

        File file = new File("cookbook.csv");
        cookbook.save(file);

        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.canRead());
        assertTrue(file.canWrite());
        assertTrue(file.canExecute());
        assertTrue(file.length() > 0);

        //!Hvordan fikse dette: open file and check if it contains the recipe
        //assertTrue(file.contains(recipe));
        //assertTrue(file.contains(recipe2));
    }

    //?hvordan teste?
    @Test
    @DisplayName("Test Load")
    public void testLoad() {
        cookbook.addRecipe(recipe1);
        cookbook.addRecipe(recipe2);
        cookbook.save();
        cookbook.load();
        assertTrue(cookbook.getRecipes().contains(recipe1));
        assertTrue(cookbook.getRecipes().contains(recipe2));
    }
}
