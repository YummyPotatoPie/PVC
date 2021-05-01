package pvc;

import pvc.Exceptions.ProcessExecutionError;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static pvc.PathsAndTokens.*;

public class HEADInformator implements Handler<String> {

    public void handle(String property) throws ProcessExecutionError {
        File HEADFile = new File(System.getProperty("user.dir") + pvcMainFolderName + "\\" + pvcHEADFileName);

        try {
            Scanner scanner = new Scanner(HEADFile);
            String[] HEADData = scanner.nextLine().split(" ");
            scanner.close();

            if (property == null) {
                System.out.println("On branch " + HEADData[0]);
                System.out.println("Last commit " + HEADData[1]);
                System.out.println("Last commit id " + HEADData[2]);
                return;
            }

            switch (property) {
                case "branch":
                    System.out.println("On branch " + HEADData[0]);
                    break;
                case "commit":
                    System.out.println("Last commit " + HEADData[1]);
                    break;
                case "id":
                    System.out.println("Last commit id " + HEADData[2]);
                    break;
            }

        }
        catch (IOException ioe) {
            throw new ProcessExecutionError();
        }

    }

}
