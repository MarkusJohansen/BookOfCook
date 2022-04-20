package BookOfCook;

import java.io.File;

public interface LoadSave {

    public void save(File file, Cookbook book);

    public Cookbook load(File file);
}
