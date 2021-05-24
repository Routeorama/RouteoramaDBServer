package com.example.routeoramaserver.callbacks.user;

import com.example.routeoramaserver.models.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/*
 * server interface for RMI client to call methods on server (this)
 * */
public interface IUserServerCallback extends Remote {
    User Login(String username, String password) throws RemoteException;

    boolean Register(User user) throws RemoteException;

    String UpdateUser(User user) throws RemoteException;
}
