package pvc;

import pvc.Exceptions.NothingToCommitError;
import pvc.Exceptions.PVCException;
import pvc.Exceptions.ProcessExecutionError;
import pvc.Exceptions.RepositoryDoesNotExist;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static pvc.PathsAndTokens.*;

public class Committer implements Handler<String> {

    public void handle(String message) throws PVCException {

        if (!Utilites.isRepositoryExist())
            throw new RepositoryDoesNotExist();

        if (Utilites.isAddFileEmpty())
            throw new NothingToCommitError();

        String commitHash = getCommitHash();
        String path = System.getProperty("user.dir") + pvcMainFolderName + pvcCommitsFolder;

        if (Utilites.isFolderExist(commitHash.substring(0, 2)))
            createCommitFile(path + "\\" + commitHash.substring(0, 2) + "\\" + commitHash);
        else {
            createCommitFolder(path + "\\" + commitHash.substring(0, 2));
            createCommitFile(path + "\\" + commitHash.substring(0, 2) + "\\" + commitHash);
        }

        addedFilesToCommit(commitHash.substring(0, 2), commitHash, message);
        addFileFlush();
        addCommitDataToBranchFile(Utilites.currentBranch(), commitHash, Integer.toString(Utilites.currentCommitID() + 1));
        Utilites.HEADRewrite(Utilites.currentBranch(), commitHash, Integer.toString(Utilites.currentCommitID() + 1));

    }

    private String getCommitHash() throws ProcessExecutionError {
        try {
            MessageDigest commitHash = MessageDigest.getInstance("SHA-256");
            String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            commitHash.update(currentTime.getBytes());

            String result = String.format("%64x", new BigInteger(1, commitHash.digest()));
            return result.charAt(0) == ' ' ? result.substring(1) : result;
        }
        catch (NoSuchAlgorithmException e) {
            throw new ProcessExecutionError();
        }
    }

    private void createCommitFolder(String path) throws ProcessExecutionError {
        File commitFolder = new File(path);

        if (!commitFolder.mkdir())
            throw new ProcessExecutionError();
    }

    private void createCommitFile(String path) throws ProcessExecutionError {
        File commitFile = new File(path);

        try {
            if (!commitFile.createNewFile())
                throw new ProcessExecutionError();
        }
        catch (IOException ioe) {
            throw new ProcessExecutionError();
        }
    }

    private void addedFilesToCommit(String commitFolderPath, String commitFileHash, String message) throws ProcessExecutionError {
        String path = System.getProperty("user.dir") + pvcMainFolderName;
        File commitInput = new File(path + pvcCommitsFolder + "\\" + commitFolderPath + "\\" + commitFileHash);
        File addFile = new File(path + "\\" + pvcAddFile);

        try {
            Scanner scanner = new Scanner(addFile);
            FileWriter outputStream = new FileWriter(commitInput);
            outputStream.append(message).append(PathsAndTokens.message).append("\n");

            while (scanner.hasNextLine()) {
                String filename = scanner.nextLine();
                File addedFilePath = new File(System.getProperty("user.dir") + "\\" + filename);
                Scanner addedFileScanner = new Scanner(addedFilePath);

                outputStream.append(filename).append(fileStart);

                while (addedFileScanner.hasNextLine())
                    outputStream.append(addedFileScanner.nextLine()).append("\n");

                outputStream.append("\n").append(filename).append(fileEnd);
                addedFileScanner.close();
            }
            outputStream.close();
        }
        catch (IOException | NoSuchElementException e) {
            throw new ProcessExecutionError();
        }

    }

    private void addFileFlush() throws ProcessExecutionError {
        String path = System.getProperty("user.dir") + pvcMainFolderName + "\\" + pvcAddFile;
        File file = new File(path);
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        }
        catch (FileNotFoundException e) {
            throw new ProcessExecutionError();
        }
    }

    private void addCommitDataToBranchFile(String branchName, String commitHash, String commitID) throws ProcessExecutionError {
        File branchFile = new File(System.getProperty("user.dir") + pvcMainFolderName + pvcBranchesFolder + "\\" + branchName);

        try {
            FileWriter writer = new FileWriter(branchFile, true);
            writer.append(commitHash).append(" ").append(commitID).append("\n");
            writer.close();
        } catch (IOException e) {
            throw new ProcessExecutionError();
        }
    }

}
