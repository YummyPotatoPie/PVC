package pvc;

import pvc.Exceptions.BranchDoesNotExistError;
import pvc.Exceptions.PVCException;

/**
 * The class that handles the "switch" command
 */
public class BranchSwitch implements Handler<String> {

    public void handle(String branchName) throws PVCException {
        if (!Utilites.isBranchExist(branchName))
            throw new BranchDoesNotExistError();

        String[] branchData = Utilites.readLastCommitData(branchName).split(" ");

        Utilites.HEADRewrite(branchName, branchData[0], branchData[1]);
    }

}
