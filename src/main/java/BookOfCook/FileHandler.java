package BookOfCook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileHandler extends Validator implements LoadSave{
    
    public void save(File file, Cookbook book) {                                                                                            //metode for å lagre cookbooken (skrive fil)
        System.out.println("Saving...");                                                                                                    //skriver ut "Saving..." for å vise handlingen i konsollet
        try {                                                                                                                               //forsøker å skrive til fil
            FileWriter fileWriter = new FileWriter(file);                                                                                   //oppretter en ny filwriter  
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);                                                                 //oppretter en ny bufferedwriter

            for (Recipe r : book.getRecipes()) {                                                                                            //for hver recipe i cookbooket (r) 
                bufferedWriter.write(r.getName() + "," + r.getServings() + "," + r.getDesc() + "," + r.getCal() + "," + r.getPrepTime());   //skriver ut navn, antall personer, beskrivelse, kalorier, og tidsforbruk
                bufferedWriter.newLine();                                                                                                   //skriver ny linje
                bufferedWriter.write(r.getCategories() + "");                                                                               //skriver ut kategorier
                bufferedWriter.newLine();                                                                                                   //skriver ny linje
                bufferedWriter.write(r.getIngredients() + "");                                                                              //skriver ut ingredienser
                bufferedWriter.newLine();                                                                                                   //skriver ny olivenolje
                bufferedWriter.write(r.getSteps() + "");                                                                                    //skriver ut stegene
                bufferedWriter.newLine();                                                                                                   //skriver ny linje for å gjøre klar til neste recipe
            }
            bufferedWriter.close();                                                                                                         //lukker bufferedwriter                                             
        } catch (IOException e) {                                                                                                           //hvis det skulle være noe feil med filen     
            System.out.println("Error writing to file");                                                                                    //skriver ut "Error writing to file"
        }
    }

    public Cookbook load(File file) {                                                                                                       //metode for å laste inn cookbooket (lese fil)
        nullOrEmpty(file);                                                                                                                  //sjekker om filen er null eller tom
        System.out.println("\nLoading...\n");                                                                                               //skriver ut "Loading..." for å vise handlingen i konsollet
        Cookbook foo = new Cookbook();                                                                                                      //oppretter en ny cookbook
        try {                                                                                                                               //forsøker å lese fra fil
            FileReader fileReader = new FileReader(file);                                                                                   //oppretter en ny filreader
            BufferedReader br = new BufferedReader(fileReader);                                                                             //oppretter en ny bufferedreader
            String line = arrayStripper(br.readLine());                                                                                     //leser linje og fjerner brackets/ gjør arrayet klar til å leses og deles inn i deler etter komma

            while (line != null) {                                                                                                          //så lenge det er linjer igjen i filen
                String line2 = arrayStripper(br.readLine());                                                                                //leser linje og fjerner brackets/ gjør arrayet klar til å leses og deles inn i deler etter komma
                String line3 = arrayStripper(br.readLine());                                                                                //leser linje og fjerner brackets/ gjør arrayet klar til å leses og deles inn i deler etter komma
                String line4 = arrayStripper(br.readLine());                                                                                //leser linje og fjerner brackets/ gjør arrayet klar til å leses og deles inn i deler etter komma

                String[] sData = line.split(",");                                                                                           //splitter første linje etter komma og setter det i en array. dette er all data som ikke er i arrays i oppskriften
                String[] categoriesData = line2.split(",");                                                                                 //splitter andre linje etter komma og setter det i en array. dette er kategorier
                String[] ingredientData = line3.split(";");                                                                                 //splitter tredje linje etter semikolon og setter det i en array. dette er ingredienser
                String[] stepsData = line4.split(",");                                                                                      //splitter fjerde linje etter komma og setter det i en array. dette er stegene
            
                ArrayList<HashMap<String, String>> ingredients = new ArrayList<HashMap<String, String>>();                                  //oppretter en ny arrayliste for ingredienser
                for (String s : ingredientData) {                                                                                           //for hver ingrediens i arrayen
                    HashMap<String, String> ingredient = new HashMap<String, String>();                                                     //oppretter en ny hashmap for hver ingrediens
                    String[] ingredientParts = s.split(",");                                                                                //splitter ingrediensen etter komma og setter det i en array. dette er ingrediensens deler
                    for (int i = 0; i < ingredientParts.length; i++) {                                                                      //for hver del i arrayen
                        String[] keyValue = ingredientParts[i].trim().split("=");                                                           //splitter delen etter = og setter det i en array. dette er key og value. trimmer også for å fikse bug hvor mellomrom oppstår
                        ingredient.put(keyValue[0], keyValue[1]);                                                                           //legger key value  pair i hashmapen
                    }
                    ingredients.add(ingredient);                                                                                            //legger hashmapen, altså ferdig ingrediens i arraylisten
                }

                ArrayList<Category> categories = new ArrayList<Category>();                                                                 //oppretter en ny arrayliste for kategorier
                for(String s : categoriesData) {                                                                                            //for hver kategori i arrayen
                    categories.add(new Category(s));                                                                                        //legger kategorien i arraylisten
                }

                ArrayList<String> steps = new ArrayList<String>();                                                                          //oppretter en ny arrayliste for stegene
                for(String step : stepsData) {                                                                                              //for hver steg i arrayen
                    steps.add(step.trim());                                                                                                 //legger steget i arraylisten   
                }
                Recipe r = new Recipe(sData[0], Integer.parseInt(sData[1]), sData[2], sData[4], ingredients, categories, steps);            //oppretter en ny recipe med data fra filen
                foo.addRecipe(r);                                                                                                           //legger recipe i cookbooket
                line = br.readLine();                                                                                                       //moves on to next recipe
            }
            br.close();                                                                                                                     //lukker bufferedreader
        } catch (IOException e) {                                                                                                           //hvis det skulle være noe feil med filen
            System.out.println("Error reading file");                                                                                       //skriver ut "Error reading file"
        }
        return foo;                                                                                                                         //returnerer cookbooket
    }
}
