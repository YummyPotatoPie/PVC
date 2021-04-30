package pvc.Exceptions;

public class NothingToCommitError extends PVCException {

    @Override
    public String getMessage() {
        return "Nothing to commit";
    }

}
