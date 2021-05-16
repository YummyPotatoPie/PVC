package pvc;

import pvc.Exceptions.*;

import static pvc.PathsAndTokens.pvcBranchesFolder;
import static pvc.PathsAndTokens.pvcMainFolderName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class which handle "merge" command
 */
public class Merger implements Handler<String> {

    public void handle(String branchName) throws PVCException {

        if (!Utilites.isRepositoryExist())
            throw new RepositoryDoesNotExist();

        if (!Utilites.isBranchExist(branchName))
            throw new BranchDoesNotExistError();

        if (!isCompatibleCommitHistory(branchName))
            throw new CommitHistoryIsNotCompatibleError();

        mergeBranches(branchName);

    }

    /**
     * Method for checking if commit histories are compatible
     * @param branchName Name of branch
     * @return True if compatible, else false
     */
    private boolean isCompatibleCommitHistory(String branchName) throws ProcessExecutionError, HEADFileCorruptedError {
        File currentBranch = new File(System.getProperty("user.dir") + pvcMainFolderName +
                pvcBranchesFolder + "\\" + Utilites.currentBranch());

        File argBranch = new File(System.getProperty("user.dir") + pvcMainFolderName +
                pvcBranchesFolder + "\\" + branchName);

        try {
            Scanner currentBranchReader = new Scanner(currentBranch);
            Scanner argBranchReader = new Scanner(argBranch);

            while (currentBranchReader.hasNextLine() && argBranchReader.hasNextLine()) {
                if (!currentBranchReader.nextLine().equals(argBranchReader.nextLine())) {
                    return false;
                }
            }

            if (!currentBranchReader.hasNextLine()) {
                currentBranchReader.close();
                argBranchReader.close();
                return true;
            }
            else {
                currentBranchReader.close();
                argBranchReader.close();
                return false;
            }

        }
        catch (IOException ioe) {
            throw new ProcessExecutionError();
        }

    }

    /**
     * Method which merge current branch with another branch
     * @param branchName Branch name as String
     */
    private void mergeBranches(String branchName) throws ProcessExecutionError, HEADFileCorruptedError {
        try {
            File currentBranch = new File(System.getProperty("user.dir") + pvcMainFolderName +
                    pvcBranchesFolder + "\\" + Utilites.currentBranch());

            File argBranch = new File(System.getProperty("user.dir") + pvcMainFolderName +
                    pvcBranchesFolder + "\\" + branchName);

            Scanner argBranchReader = new Scanner(argBranch);
            Scanner currentBranchReader = new Scanner(currentBranch);
            FileWriter currentBranchWriter = new FileWriter(currentBranch);

            while (argBranchReader.hasNextLine()) {
                String commitData = argBranchReader.nextLine();
                if (!currentBranchReader.hasNextLine() || !currentBranchReader.nextLine().equals(commitData)) {
                    currentBranchWriter.append(commitData).append("\n");
                }
            }

            argBranchReader.close();
            currentBranchReader.close();
            currentBranchWriter.close();

        }
        catch (IOException ioe) {
            throw new ProcessExecutionError();
        }
    }

}
