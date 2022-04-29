package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import BookOfCook.Category;
import BookOfCook.Cookbook;
import BookOfCook.Fridge;
import BookOfCook.Recipe;

public class CookbookTest {
    //JUnit tests for Cookbook class
    private Recipe pizza;
    private Recipe hamburger;
    private Category kjøtt;
    private Category burger;
    private Category italiensk;
    private Cookbook cookbook;
    private ArrayList<Category> tCategories;

    @BeforeEach
    public void setup() {
        cookbook = new Cookbook();
        HashMap<String, String> ost = new HashMap<String, String>() {{
            put("name", "ost");
            put("amount", "1.0");
            put("unit", "kg");
        }};
        HashMap<String, String> melk = new HashMap<String, String>() {{
            put("name", "melk");
            put("amount", "2.0");
            put("unit", "L");
        }};

        HashMap<String, String> tomat = new HashMap<String, String>() {{
            put("name", "tomat");
            put("amount", "5.0");
            put("unit", "stk");
        }};

        italiensk = new Category("italiensk");
        burger = new Category("burger");
        kjøtt = new Category("kjøtt");

        pizza = new Recipe("Pizza", 2, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), new ArrayList<String>(Arrays.asList("Tiss i en kopp", "Kok øving")));
        hamburger = new Recipe("Hamburger", 1, "Hambur er godt", "30 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk, tomat)), new ArrayList<Category>(Arrays.asList(kjøtt, burger)), new ArrayList<String>(Arrays.asList("Tiss i en kopp", "Kok øving", "blabla")));
        
        tCategories = new ArrayList<Category>(Arrays.asList(italiensk, burger, kjøtt));
    }

    //*CONSTRUCTOR TEST
    @Test
    @DisplayName("Test Constructor")
    public void testConstructor() {
        Cookbook cookbook = new Cookbook();
        assertTrue(cookbook.getRecipes().isEmpty());
    }

    //*RECIPES IN COOKBOOK TESTS
    @Test
    @DisplayName("Test add recipe and recipeAmount")
    public void RecipeContainment() {
        cookbook.addRecipe(pizza, "");
        assertTrue(cookbook.getRecipes().contains(pizza));
        assertFalse(cookbook.getRecipes().contains(hamburger));
        cookbook.addRecipe(hamburger, "");
        assertTrue(cookbook.getAmount() == 2);
        assertTrue(cookbook.getRecipes().size() == 2);
        assertThrows(IllegalArgumentException.class, () -> cookbook.addRecipe(pizza, ""));
        assertThrows(NullPointerException.class, () -> cookbook.addRecipe(null, ""));
    }

    @Test
    @DisplayName("Test remove recipe")
    public void recipeRemoval() {
        cookbook.addRecipe(pizza, "");
        cookbook.addRecipe(hamburger, "");
        cookbook.removeRecipe(hamburger);
        assertThrows(IllegalArgumentException.class, () -> cookbook.removeRecipe(hamburger));
        assertFalse(cookbook.getRecipes().contains(hamburger));
        assertTrue(cookbook.getRecipes().contains(pizza));
    }

    //*CATEGORIES IN COOKBOOK TEST
    @Test
    @DisplayName("Test categories in cookbook")
    public void categoriesInCookbook() {
        cookbook.addRecipe(pizza, "");
        cookbook.addRecipe(hamburger, "");
        assertTrue(cookbook.getCategories().containsAll(tCategories));
    }

    @Test
    @DisplayName("Test filter")
    public void testFilter() {
        Fridge fridge = new Fridge();
        fridge.addFood("ost");
        fridge.addFood("melk");
        fridge.addFood("tomat");
        cookbook.addRecipe(pizza, "");
        cookbook.addRecipe(hamburger, "");

        cookbook.setFridgeCheck(true);

        ArrayList<Category> italienskArray = new ArrayList<Category>(Arrays.asList(italiensk));
        ArrayList<Category> hamburgerArray = new ArrayList<Category>(Arrays.asList(burger, kjøtt));
        ArrayList<Category> emptyArray = new ArrayList<Category>(Arrays.asList());

        assertTrue(cookbook.filter("Pizza", italienskArray, fridge).contains(pizza));
        assertTrue(cookbook.filter("burg", hamburgerArray, fridge).contains(hamburger));
        assertTrue(cookbook.filter("a", emptyArray, fridge).contains(hamburger));
        assertTrue(cookbook.filter("a", emptyArray, fridge).contains(pizza));

        Fridge tomFridge = new Fridge();
        assertFalse(cookbook.filter("a", emptyArray, tomFridge).contains(hamburger));

        cookbook.setFridgeCheck(false);
        
        assertTrue(cookbook.filter("a", emptyArray, tomFridge).contains(hamburger));
        assertTrue(cookbook.filter("", emptyArray, tomFridge).contains(pizza));
    }
}
