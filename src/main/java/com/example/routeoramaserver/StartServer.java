package com.example.routeoramaserver;

import com.example.routeoramaserver.models.Location;
import com.example.routeoramaserver.models.Place;
import com.example.routeoramaserver.networking.ServerImpl;
import com.example.routeoramaserver.networking.callbacks.ServerCallback;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, UnknownHostException {
        System.setProperty("java.security.policy", "src\\main\\rmi.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Registry registry = LocateRegistry.createRegistry(1099);

        ServerCallback connection = new ServerImpl();
        registry.bind("RouteoramaServer", connection);
        System.out.println("Server started on: " + InetAddress.getLocalHost().getHostAddress());
    }
}
