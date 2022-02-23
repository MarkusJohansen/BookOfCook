package BookOfCook;

import java.util.ArrayList;

public class Category {
    //*FIELDS
    String name;                                                    // name of category
    ArrayList<Recipe> recipesInCategory = new ArrayList<Recipe>();  // recipes in the category

    //*CONSTRUCTOR
    public Category(String name) {
        this.name = name.toLowerCase();
    }

    //*METHODS
    public void addRecipe(Recipe recipe){
        recipesInCategory.add(recipe);
    }

    //tostring method for category
    @Override
    public String toString() {
        String recipes = "";
        for (int i = 0; i < recipesInCategory.size(); i++) {
            recipes += "\n" + recipesInCategory.get(i).getName();
        }
        return "Category: " + name + " has " + recipesInCategory.size() + " recipes. those are recipes for: " + recipes + "\n";
    }
}
