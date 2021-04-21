package pvc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pvc.Exceptions.*;

public class RepositoryInitializer implements Handler<String> {

    private final String pvcMainFolderName = "\\.pvc";
    private final String pvcCommitsFolder = "\\commits";
    private final String pvcBranchesFolder = "\\branches";
    private final String pvcConfigFileName = "config";
    private final String pvcHEADFileName = "HEAD";

    public void handle(String path) throws PVCException {

        boolean isSuccessfullyCreated = new File(path + this.pvcMainFolderName).mkdir();

        if (isSuccessfullyCreated) {
            setHiddenAttribute(new File(path + this.pvcMainFolderName));
            createCommitFolderSystem(path + this.pvcMainFolderName);
            createBranchesFolder(path + this.pvcMainFolderName);
            createConfigFile(path);
            createHEADFile(path);
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

    private void createCommitFolderSystem(String path) throws CreatingFolderSystemError {
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

    private void createBranchesFolder(String path) throws CreatingFolderSystemError {
        if (new File(path + this.pvcBranchesFolder).mkdir())
            return;
        else
            throw new CreatingFolderSystemError();
    }

    private void createConfigFile(String path) throws ProcessExecutionError {
        File configFile = new File(path + pvcMainFolderName, pvcConfigFileName);

        try {
            configFile.createNewFile();
        }
        catch (IOException ioe) {
            throw new ProcessExecutionError();
        }
    }

    private void createHEADFile(String path) throws ProcessExecutionError {
        File HEADFile = new File(path + pvcMainFolderName, pvcHEADFileName);

        try {
            HEADFile.createNewFile();

            FileWriter writer = new FileWriter(path + pvcMainFolderName + "\\" + pvcHEADFileName);
            writer.write("main null");
            writer.close();
        }
        catch (IOException ioe) {
            throw new ProcessExecutionError();
        }
    }

}
