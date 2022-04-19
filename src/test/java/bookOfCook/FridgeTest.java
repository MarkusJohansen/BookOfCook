package bookOfCook;

public class FridgeTest {
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
