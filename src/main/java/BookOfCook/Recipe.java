package BookOfCook;

import java.util.*;
public class Recipe {

    //*FIELDS
    private String name;
    private int numberOfServings;
    private double calories;
    private ArrayList<HashMap<String,Object>> Ingredients = new ArrayList<HashMap<String,Object>>();
    private ArrayList<Category> categories = new ArrayList<Category>(); 

    //*CONSTRUCTOR
    public Recipe(String name, int numberOfServings) {
        this.name = name;
        this.numberOfServings = numberOfServings;
    }

    //*ADDING INGREDIENTS
    //*ADDING STEP-BY-STEP
    //*ADDING PICTURE
    //*ADDING SCHOOL
}
