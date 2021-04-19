package PVCSource;

import java.io.File;
import java.io.IOException;

class RepositoryInitializator implements Handler<String> {

    public void handle(String path) {

        boolean isSuccesfullyCreated = new File(path + "\\.pvc").mkdir();

        if (isSuccesfullyCreated)
            setHiddenAttribute(new File(path + "\\.pvc"));
        else
            System.out.println("Something went wrong");

    }

    private void setHiddenAttribute(File file) {
        try {
            Process createHiddenFile = Runtime.getRuntime().exec("attrib +H " + file.getPath());
            createHiddenFile.waitFor();
        }
        catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong");
        }
    }

}
