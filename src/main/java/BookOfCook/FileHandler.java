package BookOfCook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileHandler implements LoadSave {

    public void save(File file, Cookbook book) {
        System.out.println("Saving...");

        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Recipe r : book.getRecipes()) {
                bufferedWriter.write(r.getName() + "," + r.getServings() + "," + r.getDescription() + "," + r.getCalories() + "," + r.getPrepTime());
                bufferedWriter.newLine();
                bufferedWriter.write(r.getCategories() + "");
                bufferedWriter.newLine();
                bufferedWriter.write(r.getIngredients() + "");
                bufferedWriter.newLine();
                bufferedWriter.write(r.getSteps() + "");
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }

    //Coookbook load() konstruerer et cookbook objekt fra Dataen i filen, og returnerer denne cookbooken.
    //dette cookbook objektet skal erstatte den eksisterende cookbooken som leses i grensesnittet.
    public Cookbook load(File file) {
        System.out.println("\nLoading...\n");
        Cookbook foo = new Cookbook();
        try {                                                                //read csv file using filereader and buffered reader Strings
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader); 

            //read 5 lines at a time
            String line = "";
            while (line != null) {
                String[] simpleData = bufferedReader.readLine().split(",");
                String[] categoriesData = bufferedReader.readLine().replace("[", "").replace("]", "").split(",");
                String[] ingredientData = bufferedReader.readLine().replace("[", "").replace("]", "").replace("{", "").replace("}", ";").split(";"); // name=blabla, unit=blabla, amount=blabla; name=blabla, unit=blabla, amount=blabla;
                String[] stepsData = bufferedReader.readLine().replace("[", "").replace("]", "").split(",");

                //construct hashmap for ingredients
                ArrayList<HashMap<String, String>> ingredients = new ArrayList<HashMap<String, String>>();
                for (String s : ingredientData) {  
                    HashMap<String, String> ingredient = new HashMap<String, String>();
                    String[] ingredientParts = s.split(",");

                    for (int i = 0; i < ingredientParts.length; i++) {
                        String[] keyValue = ingredientParts[i].split("=");
                        ingredient.put(keyValue[0], keyValue[1]);
                    }
                    ingredients.add(ingredient);
                }

                //!construct categories for recipe (lager nye kategorier)
                ArrayList<Category> categories = new ArrayList<Category>();
                for(String s : categoriesData) {
                    categories.add(new Category(s));
                }

                //converting String[] to ArrayList<String> for steps
                ArrayList<String> steps = new ArrayList<String>();
                for(String step : stepsData) {
                    steps.add(step);
                }
                Recipe r = new Recipe(simpleData[0], Integer.parseInt(simpleData[1]), simpleData[2], simpleData[4], ingredients, categories, steps);
                foo.addRecipe(r);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        return foo;
    }
}
