package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import BookOfCook.Fridge;

public class FridgeTest {
        //JUnit tests for Cookbook class
        private Fridge fridge;
        
        @BeforeEach
        public void setup() {
           fridge = new Fridge();
        }
     
        @Test
        @DisplayName("Test add and remove food")
        public void testAddRemoveFood() {
            fridge.addFood("milk", 1, "L");

            assertTrue(fridge.isFoodInFridge("milk"));
            assertFalse(fridge.isFoodInFridge("pasta"));

            fridge.removeFood("milk");

            assertFalse(fridge.isFoodInFridge("milk"));
        }
}
