package pvc;

import pvc.Exceptions.HEADFileCorruptedError;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static pvc.PathsAndTokens.*;

public class Checker {

    public static boolean isRepositoryExist() {
        return new File(System.getProperty("user.dir") + pvcMainFolderName).exists();
    }

    public static boolean isFolderExist(String path) {
        return new File(path).exists();
    }

    public static boolean isAddFileEmpty() {
        return new File(System.getProperty("user.dir") + pvcMainFolderName + "\\" + pvcAddFile).length() == 0;
    }

    public static String currentBranch() throws HEADFileCorruptedError {
        File headFile = new File(System.getProperty("user.dir") + pvcMainFolderName + "\\" + pvcHEADFileName);
        try {
            Scanner scanner = new Scanner(headFile);
            String[] headData = scanner.nextLine().split(" ");
            return headData[0];
        }
        catch (FileNotFoundException e) {
            throw new HEADFileCorruptedError();
        }
    }

    public static int currentCommitID() throws HEADFileCorruptedError {
        File headFile = new File(System.getProperty("user.dir") + pvcMainFolderName + "\\" + pvcHEADFileName);
        try {
            Scanner scanner = new Scanner(headFile);
            String[] headData = scanner.nextLine().split(" ");
            return Integer.parseInt(headData[2]);
        }
        catch (FileNotFoundException e) {
            throw new HEADFileCorruptedError();
        }
    }

}
