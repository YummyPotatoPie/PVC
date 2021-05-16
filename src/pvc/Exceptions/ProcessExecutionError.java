package pvc.Exceptions;

/**
 * Exception that arise if something went wrong while execution
 */
public class ProcessExecutionError extends PVCException {

    @Override
    public String getMessage() {
        return "Error during the execution of the process";
    }

}
