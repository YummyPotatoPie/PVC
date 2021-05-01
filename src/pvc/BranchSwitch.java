package pvc;

import pvc.Exceptions.BranchDoesNotExistError;
import pvc.Exceptions.PVCException;

public class BranchSwitch implements Handler<String> {

    public void handle(String branchName) throws PVCException {
        if (!Utilites.isBranchExist(branchName))
            throw new BranchDoesNotExistError();

        String currentBranch = Utilites.currentBranch();
        String[] currentBranchData = Utilites.readLastCommitData(currentBranch).split(" ");

        Utilites.HEADRewrite(branchName, currentBranchData[0], currentBranchData[1]);
    }

}
