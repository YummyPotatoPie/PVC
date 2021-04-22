package pvc;

import pvc.Exceptions.PVCException;
import pvc.Exceptions.ProcessExecutionError;
import pvc.Exceptions.RepositoryDoesNotExist;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Committer implements Handler<String> {

    public void handle(String message) throws PVCException  {

        if (!Checker.isRepositoryExist())
            throw new RepositoryDoesNotExist();

        String commitHash = getCommitHash();
        String path = System.getProperty("user.dir") + RepositoryInitializer.pvcMainFolderName +
                RepositoryInitializer.pvcCommitsFolder;

        if (Checker.isFolderExist(commitHash.substring(0, 2))) {
            createCommitFile(path + "\\" + commitHash.substring(0, 2) + "\\" + commitHash);
        }
        else {
            createCommitFolder(path + "\\" + commitHash.substring(0, 2));
            createCommitFile(path + "\\" + commitHash.substring(0, 2) + "\\" + commitHash);
        }
    }

    private String getCommitHash() throws ProcessExecutionError {
        try {
            MessageDigest commitHash = MessageDigest.getInstance("SHA-256");
            String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            commitHash.update(currentTime.getBytes());

            return String.format("%64x", new BigInteger(1, commitHash.digest()));
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

}
