package pvc;

import pvc.Exceptions.PVCException;

interface Handler<T> {

    /**
     * Main method of classes which handle commands of pvc
     * @param request Processing request
     * @throws PVCException Exceptions that may arise as a result of pvc execution
     */
    void handle(T request) throws PVCException;

}
