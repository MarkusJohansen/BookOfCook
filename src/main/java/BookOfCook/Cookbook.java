
package BookOfCook;

import java.util.*;

public class Cookbook extends Validator {
    private int recipeAmount;
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private ArrayList<Category> categories = new ArrayList<Category>();
    private boolean fridgeCheck;

    public Cookbook() {
        this.recipeAmount = 0;  // amount of recipes in cookbook is always 0 at star
    }

    public void addDummy(){
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

        HashMap<String, String> hamburgerbrød = new HashMap<String, String>() {{
            put("name", "hamburgerbrød");
            put("amount", "2.0");
            put("unit", "stk");
        }};

        Category italiensk = new Category("italiensk");
        Category burger = new Category("burger");
        Category kjøtt = new Category("kjøtt");

        addRecipe(new Recipe("Pizza", 2, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), 
        new ArrayList<String>(Arrays.asList(
            "Lag pizzadeig først. Rør gjær ut i lunkent vann, og tilsett olje, mel og salt. Elt deigen godt sammen, og la den heve til dobbel størrelse.", 
            "Lag pizzasaus med å frese løk og hvitløk i olivenolje. Tilsett tomatpurè og la det frese i et par minutter.",
            "Ha i grovhakket tomat, timian, oregano, salt og pepper. Dette skal nå koke inn til en tykk og god saus. Avkjøl og kjør gjerne til en jevn saus med stavmikser.",
            "Ha deigen på en lett melet benkeplate og del den i fire like emner. Trill hvert emne til runde boller. Strø over litt mel og legg over et klede. Emnene skal nå hvile i 20 minutter.", 
            "Trykk eller kjevle ut emnene til runde, tallerkenstore pizzabunner.", 
            "Fordel tomatsaus på bunnene før du legger på spekeskinke, oliven og revet parmesan.", 
            "Stek pizzaen på 250 °C i 8-10 minutter, eller til pizzaen har en gyllen farge og sprø bunn.", 
            "Dryss over rucculasalat før servering."
            ))));
        addRecipe(new Recipe("Hamburger", 1, "Hambur er godt", "30 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, hamburgerbrød, tomat)), new ArrayList<Category>(Arrays.asList(kjøtt, burger)), new ArrayList<String>(Arrays.asList(
            "Ha kjøttdeig, salt, pepper og vann i en bolle og rør deigen sammen. Ikke rør for lenge, deigen skal ikke bli seig.", 
            "Ha litt vann på en fjøl og form deigen til runde kaker som klemmes ut til store flate burgere.", 
            "Stek burgere i en stekepanne med margarin eller olje. Bruk sterk varme og stek på den ene siden til det pipler ut kjøttsaft. Snu burgerne og stek videre på den andre siden til det igjen pipler ut kjøttsaft. Da er burgerne medium stekt.",
            "Gjør klar tilbehøret. Varm hamburgerbrød som anvist på pakken. Vask salat. Kutt tomat, rødløk og syltet agurk i skiver.",
            "Fyll brødene med salatblad, burgere, tomat, rødløk og syltet agurk. Ha gjerne på en dressing du liker godt, eller en hjemmelaget ketchup. Server gjerne med potetchips",
            "Vil du ha et godt tips til burgertilbehør? Syltet rødløk er superdigg på burgeren!"
            ))));

        collectCategories();
    }

    public void addRecipe(Recipe recipe) {
        duplicateRecipeName(recipes, recipe);   // checks for recipes with duplicate names
        recipes.add(recipe);                    // adds recipe to cookbook
        recipeAmount++;                         // updates amount of recipes in cookbook
    }

    public void removeRecipe(Recipe recipe) {   // removes recipe from cookbook and updates recipe amount
        recipeExists(recipes, recipe);          //? er denne nødvendig med tanke på hvoradn grensesnittet er satt opp?  if recipe exists
        recipes.remove(recipe);                 // removes recipe from cookbook
        recipeAmount--;                         // updates amount of recipes in cookbook
    }

    public int getAmount() {
        return recipeAmount;    // returns amount of recipes in cookbook
    }

    public ArrayList<Recipe> getRecipes() {
        return new ArrayList<Recipe>(recipes);  // returns all recipes in cookbook     
    }

    public ArrayList<Category> getCategories() {
        collectCategories();
        return new ArrayList<Category>(categories); // returns categories in cookbook
    }

    public ArrayList<Recipe> filterByCategories(ArrayList<Recipe> recipes, ArrayList<Category> categories){    // metode som returnerer alle recipes som inneholder alle kategoriene
        ArrayList<Recipe> sortedRecipes = new ArrayList<>();                        // create an output arraylist
        for (Recipe recipe : recipes) {                                             // loops through all recipes in cookbook
            boolean containsAllCategories = true;                                   // sets temporary variable to true
            for (Category category : categories) {                                  // loops through all categories
                if(!recipe.getCategories().contains(category)){                     // checks if recipe does not contain this category
                    containsAllCategories = false;                                  // if: set false
                }
            }
            if(containsAllCategories){
                sortedRecipes.add(recipe);                                          // if still true, add recipe to output array
            }
        }
        System.out.println(sortedRecipes);
        return sortedRecipes;
    }

    private void collectCategories(){
        ArrayList<Category> collectedCategories = new ArrayList<>();    // create an output arraylist
        for (Recipe recipe : recipes) {                                 // lopps through all recipes
            for (Category category : recipe.getCategories()) {          // loops through all categories in recipe
                if(!collectedCategories.contains(category)){            // if not category already collected
                    collectedCategories.add(category);                  // collect category
                    //System.out.println("collected " + category);
                }
            }
        }
        categories = collectedCategories;
    }

    public ArrayList<Recipe> searchRecipes(String searchString, ArrayList<Recipe> filterTarget) {   // searches for recipes in cookbook with name containing searchString:
        ArrayList<Recipe> searchResults = new ArrayList<>();                                        // initializes an output arraylist
        for (Recipe recipe : filterTarget) {                                                        // loops through all recipes
            if (recipe.getName().toLowerCase().contains(searchString.toLowerCase())) {              // if recipe name contains searchString
                searchResults.add(recipe);                                                          // add recipe to output arraylist
            }
        }
        return searchResults;                                                                       // return output arraylist
    }

    public void setFridgeCheck(boolean fridgeCheck) {
        this.fridgeCheck = fridgeCheck;
    }

    public ArrayList<Recipe> filter(ArrayList<Recipe> recipes, String searchText, ArrayList<Category> categories, Fridge fridge){
        ArrayList<Recipe> filteredRecipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            filteredRecipes.add(recipe);
        }

        if(fridgeCheck){ // KJØLESKAP
            filteredRecipes = fridge.filter(recipes);
        }

        if(categories.size() > 0){ // KATEGORIER
            filteredRecipes = filterByCategories(filteredRecipes, categories);
        }

        if(true){ // SØKE
            if(searchText != ""){
                filteredRecipes = searchRecipes(searchText, filteredRecipes);
            }
        }
        return filteredRecipes;                         //returnerer filtrasjonsproduktet
    }
}