package pvc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pvc.Exceptions.*;

public class RepositoryInitializer implements Handler<String> {

    public final static String pvcMainFolderName = "\\.pvc";
    public final static String pvcCommitsFolder = "\\commits";
    public final static String pvcBranchesFolder = "\\branches";
    public final static String pvcConfigFileName = "config";
    public final static String pvcHEADFileName = "HEAD";
    public final static String pvcAddFile = "add";
    public final static String defaultBranchName = "main";
    public final static String nullString = "null";
    public final static String zeroCommitString = "0";

    public void handle(String path) throws PVCException {

        boolean isSuccessfullyCreated = new File(path + pvcMainFolderName).mkdir();

        if (isSuccessfullyCreated) {
            setHiddenAttribute(new File(path + pvcMainFolderName));
            createCommitFolderSystem(path + pvcMainFolderName);
            createBranchesFolder(path + pvcMainFolderName);
            createConfigFile(path);
            createHEADFile(path);
            createAddFile(path);
        }
        else
            throw new InitializationError();
    }

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

    private void createCommitFolderSystem(String path) throws CreatingFolderSystemError {
        new File(path + pvcCommitsFolder).mkdir();

        for (int i = 16; i < 256; i++) {
            if (new File(path + pvcCommitsFolder + "\\" + Integer.toHexString(i)).mkdir()) continue;
            else {
                emergencyDeletion(new File(path + pvcMainFolderName));
                throw new CreatingFolderSystemError();
            }
        }
    }

    private void createBranchesFolder(String path) throws CreatingFolderSystemError, ProcessExecutionError {
        if (new File(path + pvcBranchesFolder).mkdir()) {
            File defaultBranchFile = new File(path + pvcBranchesFolder);

            try {
                defaultBranchFile.createNewFile();
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

    private void createAddFile(String path) throws ProcessExecutionError {
        File addFile = new File(path + pvcMainFolderName, pvcAddFile);

        try {
            addFile.createNewFile();
        }
        catch (IOException ioe) {
            emergencyDeletion(new File(path + pvcMainFolderName));
        }
    }

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
