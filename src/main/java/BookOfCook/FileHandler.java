package BookOfCook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
                bufferedWriter.write("-------------------------------");
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
        System.out.println("Loading...");
        Cookbook foo = new Cookbook();
        try {                                                                //read csv file using filereader and buffered reader objects
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader); 

            //read 5 lines at a time
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] data = line.split(",");
                Recipe recipe = new Recipe(data[0], Integer.parseInt(data[1]), data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                foo.addRecipe(recipe);
                line = bufferedReader.readLine();
                System.out.println(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        
        return foo;
    }
}

    // public void load(File file) {
    //     if (file.exists()) {
    //         try {
    //             Scanner scanner = new Scanner(file);
    //             while (scanner.hasNextLine()) {
    //                 String line = scanner.nextLine();

    //                 //line 1
    //                 String[] parts = line.split(",");
    //                 String name = parts[0];
    //                 int servings = Integer.parseInt(parts[1]);
    //                 Recipe recipe = new Recipe(name, servings); //! NY KONSTRUKTØR
    //                 recipe.setDescription(parts[2]);
    //                 recipe.setCalories(Double.parseDouble(parts[3]));
    //                 recipe.setPrepTime(parts[4]);

    //                 //line 2 categories
    //                 line = scanner.nextLine().replace("[", "").replace("]", ""); //removes the array brackets
    //                 parts = line.split(",");
    //                 for (String part : parts) {
    //                     recipe.addCategory(new Category(part)); //!lager nye categorier
    //                 }
    //                 //line 3 ingredients
    //                 line = scanner.nextLine().replace("[", "").replace("]", ""); //removes the array brackets
    //                 String[] ingredients = line.split("}"); //splits the ingredients into an array  
    //                 for (String ingredient : ingredients) {
    //                     ingredient = ingredient.replace("{", "");  //removes curly brackets

    //                     //if the ingredientstring starts with comma, then start from the second character in string
    //                     if(ingredient.startsWith(",")){
    //                         ingredient = ingredient.substring(0);
    //                     }

    //                     String[] ingredientParts = ingredient.split(",");   //splits the ingredient into an array of ingredient componetns
                        
    //                     //slice ingredName from '=' to the end
    //                     Double amount = Double.parseDouble(ingredientParts[0].substring(ingredientParts[0].indexOf("=")+1));
    //                     String unit = ingredientParts[1].substring(ingredientParts[0].indexOf("="));
    //                     String ingredName = ingredientParts[3].substring(ingredientParts[0].indexOf("="));

    //                     //add ingredient to recipe
    //                     recipe.addIngredient(ingredName, amount, unit);
    //                     System.out.println("succesfully added ingredient: " + ingredName + ' ' + amount + ' ' + unit);
    //                 }
    //                 //line 4 instructions
    //                 line = scanner.nextLine().replace("[", "").replace("]", ""); //removes the array brackets
    //                 String[] instructions = line.split("}"); //splits the instructions into an array
    //                 for(String step : instructions){
    //                     recipe.addStep(step);
    //                 }
    //                 //add recipe to cookbook
    //                 recipes.add(recipe);
    //             }

    //         scanner.close();

    //         } catch (FileNotFoundException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }
