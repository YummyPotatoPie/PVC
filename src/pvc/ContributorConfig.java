package pvc;

import pvc.Exceptions.ConfigFileCorruptedError;
import pvc.Exceptions.PVCException;
import pvc.Exceptions.ProcessExecutionError;
import pvc.Exceptions.RepositoryDoesNotExist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static pvc.PathsAndTokens.*;

public class ContributorConfig implements Handler<String[]> {

    public void handle(String[] configArgs) throws PVCException {
        if (Utilites.isRepositoryExist()) {
            try {
                String configFilePath = System.getProperty("user.dir") + pvcMainFolderName + "\\" + pvcConfigFileName;

                Scanner configFileReader = new Scanner(new File(configFilePath));
                String[] oldData = configFileReader.nextLine().split(" ");
                configFileReader.close();

                String newData = "";
                if (configArgs.length < oldData.length) {
                    int currentArg = 0;
                    for (;currentArg < configArgs.length; currentArg++)
                        newData = newData.concat(configArgs[currentArg] + " ");

                    for (; currentArg < oldData.length; currentArg++)
                        newData = newData.concat(oldData[currentArg] + " ");
                }
                else
                    newData = configArgs[0] + " " + configArgs[1] + " ";

                FileWriter configFileWriter = new FileWriter(configFilePath);
                configFileWriter.write(newData.substring(0, newData.length() - 1));
                configFileWriter.close();
            }
            catch (IOException ioe) {
                throw new ProcessExecutionError();
            }
            catch (NoSuchElementException e) {
                throw new ConfigFileCorruptedError();
            }
        }
        else
            throw new RepositoryDoesNotExist();
    }

}
