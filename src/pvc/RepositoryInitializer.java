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
    private final String defaultBranchName = "main";
    private final String nullString = "null";
    private final String zeroCommitString = "0";

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
        new File(path + this.pvcCommitsFolder).mkdir();

        for (int i = 17; i < 256; i++) {
            if (new File(path + this.pvcCommitsFolder + "\\" + Integer.toHexString(i)).mkdir()) continue;
            else {
                emergencyDeletion(new File(path + pvcMainFolderName));
                throw new CreatingFolderSystemError();
            }
        }
    }

    private void createBranchesFolder(String path) throws CreatingFolderSystemError, ProcessExecutionError {
        if (new File(path + this.pvcBranchesFolder).mkdir()) {
            File defaultBranchFile = new File(path + this.pvcBranchesFolder);

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
        File configFile = new File(path + this.pvcMainFolderName, this.pvcConfigFileName);

        try {
            if (configFile.createNewFile()) {
                FileWriter writer = new FileWriter(path + this.pvcMainFolderName + "\\" +this.pvcConfigFileName);
                writer.write(System.getProperty("user.name") + " " + this.nullString);
                writer.close();
            }
        }
        catch (IOException ioe) {
            emergencyDeletion(new File(path + this.pvcMainFolderName));
            throw new ProcessExecutionError();
        }
    }

    private void createHEADFile(String path) throws ProcessExecutionError {
        File HEADFile = new File(path + this.pvcMainFolderName, this.pvcHEADFileName);

        try {
            if (HEADFile.createNewFile()) {
                FileWriter writer = new FileWriter(path + this.pvcMainFolderName + "\\" + this.pvcHEADFileName);
                writer.write(this.defaultBranchName + " " + this.nullString + " " + this.zeroCommitString);
                writer.close();
            }
        }
        catch (IOException ioe) {
            emergencyDeletion(new File(path + this.pvcMainFolderName));
            throw new ProcessExecutionError();
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
