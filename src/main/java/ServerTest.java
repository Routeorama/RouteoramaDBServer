import test.Connection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerTest {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, UnknownHostException {
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("RouteoramaServer", new Connection());
        System.out.println("Server started on: " + InetAddress.getLocalHost().getHostAddress());
    }
}
