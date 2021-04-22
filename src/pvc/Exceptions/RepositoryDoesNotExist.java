package pvc.Exceptions;

public class RepositoryDoesNotExist extends PVCException {

    @Override
    public String getMessage() {
        return "Repository does not exist";
    }

}
