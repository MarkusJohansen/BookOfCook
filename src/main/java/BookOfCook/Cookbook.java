
package BookOfCook;

import java.util.*;

public class Cookbook extends Validator {
    private int recipeAmount, displayedAmount;                                              // amount of recipes in cookbook
    private ArrayList<Recipe> recipes;                                                      // recipes in cookbook
    private ArrayList<Category> categories;                                                 // categories in cookbook
    private boolean fridgeCheck;                                                            

    public Cookbook() {                                                              // constructor
        this.recipeAmount = 0;                                                       // amount of recipes in cookbook is always 0 at start
        this.displayedAmount = 0;                                                    // amount of recipes displayed in cookbook is always 0 at start
        this.recipes = new ArrayList<Recipe>(); 
        this.categories = new ArrayList<Category>();
    }

    public void addDummyRecipes(){                                                 // add dummy recipes to cookbook
        HashMap<String, String> ost = new HashMap<String, String>() {{              // hashmap for ost
            put("name", "ost");
            put("amount", "1.0");
            put("unit", "kg");
        }};
        HashMap<String, String> melk = new HashMap<String, String>() {{             // hashmap for melk
            put("name", "melk");
            put("amount", "2.0");
            put("unit", "L");
        }};

        HashMap<String, String> tomat = new HashMap<String, String>() {{            // hashmap for tomat
            put("name", "tomat");
            put("amount", "5.0");
            put("unit", "stk");
        }};

        HashMap<String, String> hamburgerbrød = new HashMap<String, String>() {{   // hashmap for hamburgerbrød
            put("name", "hamburgerbrød");
            put("amount", "2.0");
            put("unit", "stk");
        }};

        Category italiensk = new Category("italiensk");                             // category for italiensk
        Category burger = new Category("burger");                                   // category for burger
        Category kjøtt = new Category("kjøtt");

        addRecipe(new Recipe("Pizza", 2, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), 
        new ArrayList<String>(Arrays.asList(
            "Lag pizzadeig først. Rør gjær ut i lunkent vann og tilsett olje mel og salt. Elt deigen godt sammen og la den heve til dobbel størrelse.", 
            "Lag pizzasaus med å frese løk og hvitløk i olivenolje. Tilsett tomatpurè og la det frese i et par minutter.",
            "Ha i grovhakket tomat timian oregano salt og pepper. Dette skal nå koke inn til en tykk og god saus. Avkjøl og kjør gjerne til en jevn saus med stavmikser.",
            "Ha deigen på en lett melet benkeplate og del den i fire like emner. Trill hvert emne til runde boller. Strø over litt mel og legg over et klede. Emnene skal nå hvile i 20 minutter.", 
            "Trykk eller kjevle ut emnene til runde tallerkenstore pizzabunner.", 
            "Fordel tomatsaus på bunnene før du legger på spekeskinke oliven og revet parmesan.", 
            "Stek pizzaen på 250 °C i 8-10 minutter eller til pizzaen har en gyllen farge og sprø bunn.", 
            "Dryss over rucculasalat før servering."
            ))), "");
        addRecipe(new Recipe("Hamburger", 1, "Hambur er godt", "30 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, hamburgerbrød, tomat)), new ArrayList<Category>(Arrays.asList(kjøtt, burger)), new ArrayList<String>(Arrays.asList(
            "Ha kjøttdeig salt pepper og vann i en bolle og rør deigen sammen. Ikke rør for lenge deigen skal ikke bli seig.", 
            "Ha litt vann på en fjøl og form deigen til runde kaker som klemmes ut til store flate burgere.", 
            "Stek burgere i en stekepanne med margarin eller olje. Bruk sterk varme og stek på den ene siden til det pipler ut kjøttsaft. Snu burgerne og stek videre på den andre siden til det igjen pipler ut kjøttsaft. Da er burgerne medium stekt.",
            "Gjør klar tilbehøret. Varm hamburgerbrød som anvist på pakken. Vask salat. Kutt tomat rødløk og syltet agurk i skiver.",
            "Fyll brødene med salatblad burgere tomat rødløk og syltet agurk. Ha gjerne på en dressing du liker godt eller en hjemmelaget ketchup. Server gjerne med potetchips",
            "Vil du ha et godt tips til burgertilbehør? Syltet rødløk er superdigg på burgeren!"
            ))), "");

        collectCategories();                                                    
    }

    public void setFridgeCheck(boolean fridgeCheck) {                                                             
        this.fridgeCheck = fridgeCheck;                                                                       
    }

    public void addRecipe(Recipe recipe, String calories) {                 // add recipe to cookbook
        duplicateRecipeName(recipes, recipe);                               // checks for recipes with duplicate names
        if (calories == null || calories.equals("")) {                       // checks if calories is empty
            recipe.setCalories(0);                                           // sets calories to 0
        } else {                                                             // if calories is not empty
            recipe.setCalories(Double.parseDouble(calories));                // sets calories to double value of calories
        }
        recipes.add(recipe);                                                // adds recipe to cookbook
        recipeAmount++;                                                     // updates amount of recipes in cookbook
    }

    public void removeRecipe(Recipe recipe) {                               // removes recipe from cookbook and updates recipe amount
        recipeExists(recipes, recipe);                                      // checks if recipe exists
        recipes.remove(recipe);                                             // removes recipe from cookbook
        recipeAmount--;                                                     // updates amount of recipes in cookbook
    }

    public int getAmount() {                                                // returns amount of recipes in cookbook
        return recipeAmount;                    
    }

    public int getDisplayedAmount() {                                       // returns amount of recipes displayed in cookbook
        return displayedAmount;
    }

    public ArrayList<Recipe> getRecipes() {                                 // returns recipes in cookbook
        return new ArrayList<Recipe>(recipes);                               
    }                                                                       

    public ArrayList<Category> getCategories() {
        collectCategories();                                                
        return new ArrayList<Category>(categories);                         
    }

    private ArrayList<Recipe> filterByCategories(ArrayList<Recipe> recipes, ArrayList<Category> categories){     // metode som returnerer alle recipes som inneholder alle kategoriene
        ArrayList<Recipe> sortedRecipes = new ArrayList<>();                                                    // create an output arraylist
        for (Recipe recipe : recipes) {                                                                         // loops through all recipes in cookbook
            boolean containsAllCategories = true;                                                               // sets temporary variable to true
            for (Category category : categories) {                                                              // loops through all categories
                if(!recipe.getCategories().contains(category)){                                                 // checks if recipe does not contain this category
                    containsAllCategories = false;                                                              // if: set false
                }
            }
            if(containsAllCategories){                                                                          // recipe contains all categories
                sortedRecipes.add(recipe);                                                                      // add recipe to output array
            }
        }
        return sortedRecipes;
    }

    private void collectCategories(){                                                                           
        ArrayList<Category> collectedCategories = new ArrayList<>();                                            
        for (Recipe recipe : recipes) {                                                                         // loops through all recipes
            for (Category category : recipe.getCategories()) {                                                  // loops through all categories in recipe
                if(!collectedCategories.contains(category)){                                                    // if not category already collected
                    collectedCategories.add(category);                                                          // collect category
                }
            }
        }
        categories = collectedCategories;
    }

    public ArrayList<Category> addNewCategories(ArrayList<String> categNames){                           // creates new categories from a list of names
        ArrayList<Category> outputArray = new ArrayList<Category>();                                            // create an output arraylist
        for(String category : categNames){                                                                      // loops through all names in category names
            if(checkIfCategoryExist(category, getCategories())){                                                // checks if category already exists in cookbook 
                for(Category c : getCategories()){                                                              // loops through all categories in cookbook
                    if(category.toUpperCase().equals(c.getName().toUpperCase())){                               // if category name is equal to category name in cookbook
                        outputArray.add(c);                                                                     // add category to output array
                    }
                }
            }else{
                outputArray.add(new Category(category));                                                        // add new category to output array
            }
        }
        return outputArray;                                                                                     // return output array
    }

    private ArrayList<Recipe> searchRecipes(String searchString, ArrayList<Recipe> filterTarget) {               // searches for recipes in cookbook with name containing searchString:
        ArrayList<Recipe> searchResults = new ArrayList<>();                                                    // initializes an output arraylist
        for (Recipe recipe : filterTarget) {                                                                    // loops through all recipes
            if (recipe.getName().toLowerCase().contains(searchString.toLowerCase())) {                          // if recipe name contains searchString
                searchResults.add(recipe);                                                                      // add recipe to output arraylist
            }
        }
        return searchResults;                                                                                   // return output arraylist
    }

    public ArrayList<Recipe> filter(String searchText, ArrayList<Category> categories, Fridge fridge){
        ArrayList<Recipe> filteredRecipes = new ArrayList<>();
        for (Recipe recipe : getRecipes()) {                                                               
            filteredRecipes.add(recipe);                        
        }
        if(fridgeCheck){                                                                                                            
            filteredRecipes = fridge.filter(recipes);                                                                               
        }
        if(categories.size() > 0){ // KATEGORIER                                                                                    // dersom noen kategorier sendes inn i funksjonen, skal den filtrere på disse
            filteredRecipes = filterByCategories(filteredRecipes, categories);                                                      // filtered recipes settes til resultatet en arraylist med recipes fra filteredRecipes som matcher categories
        }
        if(searchText != ""){                                                                                                       //dersom søkestrengen ikke er tom, skal den filtrere på søkestrengen
            filteredRecipes = searchRecipes(searchText, filteredRecipes);                                                           // filtered recipes settes til resultatet en arraylist med recipes fra filteredRecipes som matcher søkestrengen
        }
        displayedAmount = filteredRecipes.size();                                                                                     // recipeAmount settes til antall recipes i filteredRecipes
        return filteredRecipes;                                                                                                     //returnerer filtrasjonsprodukte, etter tre runder med filtrering
    }
}