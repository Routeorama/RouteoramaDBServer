package test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerCallback extends Remote {
    String getTestingStuff() throws RemoteException;
}
