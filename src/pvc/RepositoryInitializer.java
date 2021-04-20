package pvc;

import java.io.File;
import java.io.IOException;
import pvc.Exceptions.*;

public class RepositoryInitializator implements Handler<String> {

    private final String pvcMainFolderName = "\\.pvc";
    private final String pvcCommitsFolder = "\\commits";
    private final String pvcBranchesFolder = "\\branches";

    public void handle(String path) throws PVCException {

        boolean isSuccesfullyCreated = new File(path + this.pvcMainFolderName).mkdir();

        if (isSuccesfullyCreated) {
            setHiddenAttribute(new File(path + this.pvcMainFolderName));
            createFolderSystem(path + this.pvcMainFolderName);
        }
        else
            throw new InitializationError();
    }

    private void setHiddenAttribute(File file) throws PVCException {
        try {
            Process createHiddenFile = Runtime.getRuntime().exec("attrib +H " + file.getPath());
            createHiddenFile.waitFor();
        }
        catch (IOException | InterruptedException e) {
            throw new ProcessExecutionError();
        }
    }

    private void createFolderSystem(String path) throws CreatingFolderSystemError {
        new File(path + this.pvcCommitsFolder).mkdir();

        for (int i = 17; i < 256; i++) {
            if (new File(path + this.pvcCommitsFolder + "\\" + Integer.toHexString(i)).mkdir())
                continue;
            else {
                File pvcMainFolder = new File(path);
                pvcMainFolder.delete();

                throw new CreatingFolderSystemError();
            }
        }
    }

}
