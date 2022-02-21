package BookOfCook;

import java.util.ArrayList;

public class Category {
    //*FIELDS
    String name;
    ArrayList<Recipe> recipesInCategory = new ArrayList<Recipe>();

    //*CONSTRUCTOR
    public Category(String name) {
        this.name = name;
    }

    //*METHODS
    public void addRecipe(Recipe recipe){
        recipesInCategory.add(recipe);
    }
}
