package pvc;

import pvc.Exceptions.AddedFileDoesNotExist;
import pvc.Exceptions.ProcessExecutionError;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileAdder implements Handler<String> {

    public void handle(String filePath) throws ProcessExecutionError, AddedFileDoesNotExist {
        if (Checker.isRepositoryExist()) {
            File addedFile = new File(filePath);

            if (addedFile.exists()) {
                if (!isNotRepetitive(filePath)) System.exit(1);

                try {
                    FileWriter writer = new FileWriter(System.getProperty("user.dir") +
                            RepositoryInitializer.pvcMainFolderName + "\\" + RepositoryInitializer.pvcAddFile, true);

                    writer.write(filePath + "\n");
                    writer.close();
                }
                catch (IOException ioe) {
                    throw new ProcessExecutionError();
                }
            }
            else {
                throw new AddedFileDoesNotExist();
            }
        }
    }

    private boolean isNotRepetitive(String filePath) {
        Scanner scanner = new Scanner(System.getProperty("user.dir" +
                RepositoryInitializer.pvcMainFolderName) + "\\" + RepositoryInitializer.pvcAddFile);

        while (scanner.hasNextLine()) {
            if (scanner.nextLine() == filePath) {
                scanner.close();
                return false;
            }
        }
        scanner.close();
        return true;
    }

}
