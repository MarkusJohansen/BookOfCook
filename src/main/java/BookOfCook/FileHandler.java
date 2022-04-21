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

        //read csv file using filereader and buffered reader objects
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                continue;       
                //read next line in document
                
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        
        return foo;
    }
}

//!fjerne navn i cookbook konstruktør?