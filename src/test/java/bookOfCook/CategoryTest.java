package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import BookOfCook.Category;
import BookOfCook.Recipe;

public class CategoryTest {
    //JUnit tests for Cookbook class
    private Recipe pizza;
    private Category italiensk;

    @BeforeEach
    public void setup() {
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

        italiensk = new Category("italiensk");
        pizza = new Recipe("Pizza", 2, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), new ArrayList<String>(Arrays.asList("Tiss i en kopp", "Kok øving")));
    }

    @Test
    @DisplayName("Test add and remove recipe from category")
    public void testAddRemoveRecipe() {
        italiensk.addRecipe(pizza);
        assertTrue(italiensk.getRecipes().contains(pizza));
        italiensk.removeRecipe(pizza);
        assertFalse(italiensk.getRecipes().contains(pizza));
    }
}
