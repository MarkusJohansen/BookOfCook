package BookOfCook;

import java.io.File;

public interface loadSave {

    public void save(File file, Cookbook book);

    public Cookbook load(File file);
}
