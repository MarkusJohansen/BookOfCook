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
        if(recipesInCategory.contains(recipe)){     // checks if  recipesInCategory already contains recipes
            return;
        }

        recipesInCategory.add(recipe);

        recipe.addCategory(this);
    }

    public void removeRecipe(Recipe recipe){
        if(!recipesInCategory.contains(recipe)){
            return;
        }
        recipesInCategory.remove(recipe);
        recipe.removeCategory(this);
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

    public static void main(String[] args) {
        Category italiensk = new Category("italiensk");
        Recipe pasta_carbonara = new Recipe("pasta carbonara", 3);

        italiensk.addRecipe(pasta_carbonara);

        System.out.println(italiensk);

        System.out.println(pasta_carbonara);
    }
}
