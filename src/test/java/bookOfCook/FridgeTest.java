package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import BookOfCook.Cookbook;
import BookOfCook.Fridge;

//*all funksjonalitet dekkes av disse tester
public class FridgeTest {   
    private Cookbook cookbook;
    private Fridge fridge;

    @BeforeEach
    public void setup() {
        cookbook = new Cookbook();
        fridge = new Fridge();
        cookbook.addDummy();
    }

    @Test
    @DisplayName("Test add and remove food")
    public void testAddRemoveFood() {
        fridge.addFood("milk");
        assertTrue(fridge.getFood().contains("milk"));
        assertFalse(fridge.getFood().contains("pasta"));
        fridge.addFood("pasta");
        fridge.removeFood("milk");
        assertFalse(fridge.getFood().contains("milk"));
    }

    //! yet å sette opp
    @Test
    @DisplayName("Test filter")
    public void testFilter() {
        fridge.addFood("pasta");
        assertTrue(fridge.filter(cookbook.getRecipes()).size() == 0);
        fridge.addFood("tomat");
        fridge.addFood("ost");
        assertTrue(fridge.filter(cookbook.getRecipes()).size() == 1);
        fridge.addFood("hamburgerbrød");
        assertTrue(fridge.filter(cookbook.getRecipes()).size() == 2);
    }
}
