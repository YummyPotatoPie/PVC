package pvc.Exceptions;

public class ConfigFileCorruptedError extends PVCException {

    @Override
    public String getMessage() {
        return "Config file corrupted";
    }

}
