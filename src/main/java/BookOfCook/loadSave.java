package BookOfCook;

import java.io.File;

public interface LoadSave {                         //interface som implementeres av fileHandler.java
    public void save(File file, Cookbook book);     //metode for å lagre cookbooket (skrive fil)
    public Cookbook load(File file);                //metode for å laste inn cookbook (lese fra fil)
}
