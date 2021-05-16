package pvc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pvc.Exceptions.*;

import static pvc.PathsAndTokens.*;

/**
 * Class which initialize repository
 */
public class RepositoryInitializer implements Handler<String> {

    public void handle(String path) throws PVCException {

        boolean isSuccessfullyCreated = new File(path + pvcMainFolderName).mkdir();

        if (isSuccessfullyCreated) {
            setHiddenAttribute(new File(path + pvcMainFolderName));
            createCommitFolder(path + pvcMainFolderName);
            createBranchesFolder(path + pvcMainFolderName);
            createConfigFile(path);
            createHEADFile(path);
            createAddFile(path);
        }
        else
            throw new InitializationError();
    }

    /**
     * Method which set hidden attribute to ".pvc" folder
     */
    private void setHiddenAttribute(File file) throws ProcessExecutionError {
        try {
            Process createHiddenFile = Runtime.getRuntime().exec("attrib +H " + file.getPath());
            createHiddenFile.waitFor();
        }
        catch (IOException | InterruptedException e) {
            emergencyDeletion(file);
            throw new ProcessExecutionError();
        }
    }

    /**
     * Method which create ".pvc/commit" folder
     */
    private void createCommitFolder(String path) throws CreatingFolderSystemError {
        if (!(new File(path + pvcCommitsFolder).mkdir())) {
            emergencyDeletion(new File(path));
            throw new CreatingFolderSystemError();
        }
    }

    /**
     * Method which create ".pvc/branches" folder
     */
    private void createBranchesFolder(String path) throws CreatingFolderSystemError, ProcessExecutionError {
        if (new File(path + pvcBranchesFolder).mkdir()) {
            File defaultBranchFile = new File(path + pvcBranchesFolder + "\\" + defaultBranchName);

            try {
                if (!defaultBranchFile.createNewFile()) {
                    emergencyDeletion(new File(path));
                    throw new ProcessExecutionError();
                }
            }
            catch (IOException ioe) {
                emergencyDeletion(new File(path));
                throw new ProcessExecutionError();
            }
        }
        else {
            emergencyDeletion(new File(path + pvcMainFolderName));
            throw new CreatingFolderSystemError();
        }
    }

    /**
     * Method which create ".pvc/config" file
     */
    private void createConfigFile(String path) throws ProcessExecutionError {
        File configFile = new File(path + pvcMainFolderName, pvcConfigFileName);

        try {
            if (configFile.createNewFile()) {
                FileWriter writer = new FileWriter(path + pvcMainFolderName + "\\" +pvcConfigFileName);
                writer.write(System.getProperty("user.name") + " " + nullString);
                writer.close();
            }
        }
        catch (IOException ioe) {
            emergencyDeletion(new File(path + pvcMainFolderName));
            throw new ProcessExecutionError();
        }
    }

    /**
     * Method which create ".pvc/HEAD" file
     */
    private void createHEADFile(String path) throws ProcessExecutionError {
        File HEADFile = new File(path + pvcMainFolderName, pvcHEADFileName);

        try {
            if (HEADFile.createNewFile()) {
                FileWriter writer = new FileWriter(path + pvcMainFolderName + "\\" + pvcHEADFileName);
                writer.write(defaultBranchName + " " + nullString + " " + zeroCommitString);
                writer.close();
            }
        }
        catch (IOException ioe) {
            emergencyDeletion(new File(path + pvcMainFolderName));
            throw new ProcessExecutionError();
        }
    }

    /**
     * Method which create ".pvc/add" file
     */
    private void createAddFile(String path) throws ProcessExecutionError {
        File addFile = new File(path + pvcMainFolderName, pvcAddFile);

        try {
            if (!addFile.createNewFile())
                throw new ProcessExecutionError();
        }
        catch (IOException ioe) {
            emergencyDeletion(new File(path + pvcMainFolderName));
            throw new ProcessExecutionError();
        }
    }

    /**
     * Deletes ".../.pvc" folder if something went wrong
     * @param file ".../.pvc" folder
     */
    private void emergencyDeletion(File file) {
        if (file.isDirectory()) {
            File[] directoryFiles = file.listFiles();

            if (directoryFiles != null)
                for (File f: directoryFiles)
                    emergencyDeletion(f);
        }

        file.delete();
    }

}
