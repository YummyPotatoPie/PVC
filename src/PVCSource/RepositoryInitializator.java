package PVCSource;

import java.io.File;
import java.io.IOException;

class RepositoryInitializator implements Handler<String> {

    private final String pvcMainFolderName = "\\.pvc";
    private final String pvcCommitsFolder = "\\commits";

    public void handle(String path) {

        boolean isSuccesfullyCreated = new File(path + this.pvcMainFolderName).mkdir();

        if (isSuccesfullyCreated) {
            setHiddenAttribute(new File(path + this.pvcMainFolderName));
            createFolderSystem(path + this.pvcMainFolderName);
        }
        else {
            System.out.println("Something went wrong");
            System.exit(1);
        }

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

    private void createFolderSystem(String path) {
        new File(path + this.pvcCommitsFolder).mkdir();

        for (int i = 17; i < 256; i++) {
            if (new File(path + this.pvcCommitsFolder + "\\" + Integer.toHexString(i)).mkdir())
                continue;
            else {
                System.out.println("Something went wrong");

                File pvcMainFolder = new File(path);
                pvcMainFolder.delete();

                System.exit(1);
            }
        }
    }

}
