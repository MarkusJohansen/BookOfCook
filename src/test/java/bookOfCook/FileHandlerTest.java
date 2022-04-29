package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import BookOfCook.Cookbook;
import BookOfCook.FileHandler;

public class FileHandlerTest {
    private Cookbook cookbook = new Cookbook();
    
	@Test
	@DisplayName("save() and load()")
	public void testSaveLoad() {
		cookbook.addDummyRecipes();
		FileHandler fh = new FileHandler();
		File file = new File("test.csv");
		fh.save(file , cookbook);
		assertTrue(file.exists());

		//load cookbook from file that was saved, and see if cookbook data was altered or loaded correctly
		Cookbook loadedCookbook = fh.load(file);
		assertEquals(loadedCookbook.getRecipes().size(), cookbook.getRecipes().size());
		assertEquals(loadedCookbook.getRecipes().get(0).getName(), cookbook.getRecipes().get(0).getName());
		assertEquals(loadedCookbook.getRecipes().get(0).getDescription(), cookbook.getRecipes().get(0).getDescription());
		assertEquals(loadedCookbook.getRecipes().get(0).getPrepTime(), cookbook.getRecipes().get(0).getPrepTime());
		assertEquals(loadedCookbook.getRecipes().get(0).getServings(), cookbook.getRecipes().get(0).getServings());
		assertEquals(loadedCookbook.getRecipes().get(0).getIngredients(), cookbook.getRecipes().get(0).getIngredients());
		assertEquals(loadedCookbook.getRecipes().get(0).getSteps(), cookbook.getRecipes().get(0).getSteps());
		assertEquals(loadedCookbook.getRecipes().get(0).getCategories(), cookbook.getRecipes().get(0).getCategories());
	}

}
