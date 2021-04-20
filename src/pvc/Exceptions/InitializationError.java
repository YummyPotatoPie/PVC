package pvc.Exceptions;

public class InitializationError extends PVCException {

    @Override
    public String getMessage() {
        return "Unable to initialize repository";
    }

}
