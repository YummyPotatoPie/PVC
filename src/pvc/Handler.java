package pvc;

import pvc.Exceptions.PVCException;

interface Handler<T> {

    void handle(T request) throws PVCException;

}
