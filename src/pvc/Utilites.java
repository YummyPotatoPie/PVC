package pvc;

import pvc.Exceptions.HEADFileCorruptedError;
import pvc.Exceptions.ProcessExecutionError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static pvc.PathsAndTokens.*;

public class Utilites {

    public static boolean isRepositoryExist() {
        return new File(System.getProperty("user.dir") + pvcMainFolderName).exists();
    }

    public static boolean isFolderExist(String path) {
        return new File(path).exists();
    }

    public static boolean isAddFileEmpty() {
        return new File(System.getProperty("user.dir") + pvcMainFolderName + "\\" + pvcAddFile).length() == 0;
    }

    public static String[] getConfigData() throws ProcessExecutionError {
        try {
            File configFile = new File(System.getProperty("user.dir") + pvcMainFolderName + "\\" + pvcConfigFileName);
            Scanner scanner = new Scanner(configFile);

            String[] data = scanner.nextLine().split(" ");
            scanner.close();
            return data;

        }
        catch (IOException ioe) {
            throw new ProcessExecutionError();
        }
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

    public static int currentCommitHash() throws HEADFileCorruptedError {
        File headFile = new File(System.getProperty("user.dir") + pvcMainFolderName + "\\" + pvcHEADFileName);
        try {
            Scanner scanner = new Scanner(headFile);
            String[] headData = scanner.nextLine().split(" ");
            return Integer.parseInt(headData[1]);
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

    public static void HEADRewrite(String branchName, String commitName, String commitID) throws ProcessExecutionError {
        File branchFile = new File(System.getProperty("user.dir") + pvcMainFolderName + "\\" + pvcHEADFileName);

        try {
            FileWriter writer = new FileWriter(branchFile);
            writer.write(branchName + " " + commitName + " " + commitID);
            writer.close();
        } catch (IOException e) {
            throw new ProcessExecutionError();
        }
    }

    public static boolean isBranchExist(String branchName) {
        return new File(System.getProperty("user.dir") + pvcMainFolderName +
                pvcBranchesFolder + "\\" + branchName).exists();
    }

    public static String readLastCommitData(String branchName) throws ProcessExecutionError {
        File branchFile = new File(System.getProperty("user.dir") + pvcMainFolderName +
                pvcBranchesFolder + "\\" + branchName);

        try {
            Scanner scanner = new Scanner(branchFile);

            String currentData = "";

            while (scanner.hasNextLine())
                currentData = scanner.nextLine();

            return currentData;
        }
        catch (IOException ioe) {
            throw new ProcessExecutionError();
        }
    }

    public static String toBinaryString(String str) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < str.length(); i++)
            result.append(Integer.toBinaryString(str.charAt(i)));

        return result.toString();
    }

}
