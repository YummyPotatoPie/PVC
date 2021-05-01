package pvc;

import pvc.Exceptions.HEADFileCorruptedError;
import pvc.Exceptions.PVCException;
import pvc.Exceptions.ProcessExecutionError;
import pvc.Exceptions.RepositoryDoesNotExist;

import java.io.*;
import java.util.Scanner;

import static pvc.PathsAndTokens.*;

public class Brancher implements Handler<String> {

    public void handle(String branchName) throws PVCException {

        if (!Utilites.isRepositoryExist())
            throw new RepositoryDoesNotExist();

        createBranchFile(branchName);
        rewriteCommits(branchName);

    }

    private void createBranchFile(String branchName) throws ProcessExecutionError {
        File branch = new File(System.getProperty("user.dir") + pvcMainFolderName + pvcBranchesFolder + "\\" + branchName);

        try {
            branch.createNewFile();
        }
        catch (IOException ioe) {
            throw new ProcessExecutionError();
        }
    }

    private void rewriteCommits(String branchName) throws ProcessExecutionError, HEADFileCorruptedError {
        File currentBranch = new File(System.getProperty("user.dir") + pvcMainFolderName +
                pvcBranchesFolder + "\\" + Utilites.currentBranch());

        File newBranch = new File(System.getProperty("user.dir") + pvcMainFolderName +
                pvcBranchesFolder + "\\" + branchName);

        try {
            Scanner reader = new Scanner(currentBranch);
            FileWriter writer = new FileWriter(newBranch);
            String commitData = "";

            while (reader.hasNextLine()) {
                commitData = reader.nextLine();
                writer.append(commitData).append("\n");
            }

            String[] data = commitData.split(" ");
            Utilites.HEADRewrite(branchName, data[0], data[1]);
            reader.close();
            writer.close();
        }
        catch (IOException e) {
            throw new ProcessExecutionError();
        }

    }

}
