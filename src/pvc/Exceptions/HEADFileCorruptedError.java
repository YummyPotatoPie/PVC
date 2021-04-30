package pvc.Exceptions;

public class HEADFileCorruptedError extends PVCException {

    @Override
    public String getMessage() {
        return "HEAD file corrupted or deleted";
    }

}
