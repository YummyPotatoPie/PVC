package pvc.Exceptions;

public class AddedFileDoesNotExist extends PVCException {

    @Override
    public String getMessage() {
        return "File does not exist";
    }

}
