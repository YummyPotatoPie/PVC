package pvc.Exceptions;

public class BranchDoesNotExistError extends PVCException {

    @Override
    public String getMessage() {
        return "Branch does not exist";
    }

}
