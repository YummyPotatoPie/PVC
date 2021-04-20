package pvc.Exceptions;

public class ProcessExecutionError extends PVCException {

    @Override
    public String getMessage() {
        return "Error during the execution of the process";
    }

}
