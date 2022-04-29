package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

import BookOfCook.Cookbook;
import BookOfCook.FileHandler;

public class FileHandlerTest {
    private Cookbook cookbook = new Cookbook();
    
    @Test
	public void testWriteToOS() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
        
        cookbook.addDummyRecipes();

        FileHandler fh = new FileHandler();

		TodoFileSupport fs = new TodoFileSupport();
		fs.writeTodoList(tl, os);
		
		String actual = new String(os.toByteArray());
		String expected = getStringRep();
		
		assertEquals(expected, actual, "Written string representation is not correct.");
	}

}
