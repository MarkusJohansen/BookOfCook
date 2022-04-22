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
import BookOfCook.Recipe;

public class RecipeTest {
    private Recipe pizza;
    private Recipe hamburger;
    private Category kjøtt;
    private Category burger;
    private Category italiensk;
    private Cookbook cookbook;

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
    }

    @Test
    @DisplayName("Test pizza name")
    public void checkpizzaName() {
        pizza.setName("Fransk løk suppe");
        assertTrue(pizza.getName().equals("Fransk løk suppe".toUpperCase()));

        //test if invalid pizzaname throws exception
        assertThrows(IllegalArgumentException.class, () -> pizza.setName("Fransk123"));
        assertThrows(IllegalArgumentException.class, () -> pizza.setName("Fransk "));
        assertThrows(IllegalArgumentException.class, () -> pizza.setName(" Fransk"));
        assertThrows(IllegalArgumentException.class, () -> pizza.setName("Fransk#@%&!"));

        assertFalse(pizza.getName().equals("Fransk #@%&!".toUpperCase()));
    }

    @Test
    @DisplayName("Test pizza servings")
    public void checkpizzaServings() {
        assertTrue(pizza.getServings() == 3);
        pizza.setServings(2);
        assertTrue(pizza.getServings() == 2);

        //test if invalid servings throws exception
        assertThrows(IllegalArgumentException.class, () -> pizza.setServings(0));
        assertThrows(IllegalArgumentException.class, () -> pizza.setServings(-1));
    }

    @Test
    @DisplayName("Test pizza description")
    public void checkpizzaDescription() {
        assertTrue(pizza.getDescription() == null);
        pizza.setDescription("Milkshake is a drink made from milk and sugar.");
        assertTrue(pizza.getDescription() == "Milkshake is a drink made from milk and sugar.");
    }

    //! hvorfor funker ikke denne? feil i koden i funksjonen?
    @Test
    @DisplayName("Test Calorie methods")
    public void checkCalorieMethods() {
        assertTrue(pizza.getCalories() == 0);
        pizza.setCalories(100);
        assertTrue(pizza.getCalories() == 100);
        pizza.setServings(2);
        assertTrue(pizza.getCalPerServing() == 50);
        assertThrows(IllegalArgumentException.class, () -> pizza.setCalories(-1));
        assertThrows(IllegalArgumentException.class, () -> pizza.setCalories(0));
    }

    @Test
    @DisplayName("Test scaling of pizza")
    public void checkScaling() {
        pizza.setServings(2);
        pizza.setCalories(100);
        pizza.addIngredient("Milk", "2", "l");
        assertTrue(pizza.getCalories() == 100);
        assertTrue(pizza.getServings() == 2);
        // assertTrue(pizza.getIngredientAmount("Milk") == 2);

        pizza.scale(4);
        assertTrue(pizza.getServings() == 4);
        assertTrue(pizza.getCalories() == 200);
        assertTrue(pizza.getCalPerServing() == 100);
        // assertTrue(pizza.getIngredientAmount("Milk") == 4);

        //check illegal new servings
        assertThrows(IllegalArgumentException.class, () -> pizza.scale(0));
        assertThrows(IllegalArgumentException.class, () -> pizza.scale(-1));
        assertThrows(IllegalArgumentException.class, () -> pizza.scale(1));
    }

    @Test
    @DisplayName("Test pizza ingredients")
    public void checkpizzaIngredients() {
        pizza.addIngredient("Milk", "2", "l");
        assertTrue(pizza.getIngredients().size() == 1);
        pizza.addIngredient("Sugar", "1", "tsk");
        assertTrue(pizza.getIngredients().size() == 2);

        // assertTrue(pizza.getIngredients().get(0).getName().equals("Milk"));
        // assertTrue(pizza.getIngredients().get(0).getAmount() == 2);
        // assertTrue(pizza.getIngredients().get(0).getUnit().equals("l"));

        //test if invalid ingredient throws exception
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk", "-1", "l"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk", "0", "l"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk", "2", ""));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk123", "2", "l"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk", "2", "l123"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk!#¤%", "2", "l"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk", "2", "l#¤%&"));

        pizza.removeIngredient("Milk");
        assertTrue(pizza.getIngredients().size() == 1);
        // assertTrue(pizza.getIngredients().get(0).getName().equals("Sugar"));
    }
    
    @Test
    @DisplayName("Test Category methods for pizza")
    public void checkCategoryMethods() {
        Category category = new Category("italiensk");
        pizza.addCategory(category);
        assertTrue(category.getRecipes().contains(pizza));
        assertTrue(pizza.getCategories().contains(category));
        pizza.removeCategory(category);
        assertFalse(category.getRecipes().contains(pizza));
        assertFalse(pizza.getCategories().contains(category));
       //?vi har dobbelt opp med metoder her. ser at vi har add/remove category metoder i pizza og vice versa i category
    }


    @Test
    @DisplayName("Test pizza instructions")
    public void checkpizzaInstructions() {
        assertTrue(pizza.getSteps() == null);
        assertFalse(pizza.getSteps().contains("Mix"));
        pizza.addStep("Mix");
        assertTrue(pizza.getSteps().contains("Mix"));
        pizza.addStep("Bake");
        assertTrue(pizza.getSteps().contains("Bake"));
        pizza.removeStep(0);
        assertFalse(pizza.getSteps().contains("Mix"));
        assertTrue(pizza.getSteps().contains("Bake"));

        //test if invalid step throws exception
        assertThrows(IllegalArgumentException.class, () -> pizza.addStep(""));
        assertThrows(IllegalArgumentException.class, () -> pizza.addStep(null));
        assertThrows(IllegalArgumentException.class, () -> pizza.removeStep(-1));
    }
}
