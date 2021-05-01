package pvc.Exceptions;

public class CommitHistoryIsNotCompatibleError extends PVCException {

    @Override
    public String getMessage() {
        return "Commit history is not compatible";
    }

}
