package com.example.routeoramaserver.callbacks.user;

import com.example.routeoramaserver.dao.users.IUserDAO;
import com.example.routeoramaserver.dao.users.UserDAOManager;
import com.example.routeoramaserver.models.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class UserServerImpl implements IUserServerCallback {

    private IUserDAO userDAOManager;

    public UserServerImpl(){
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        userDAOManager = new UserDAOManager();
    }

    @Override
    public User Login(String username, String password) throws RemoteException {
        try {
            return userDAOManager.Login(username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error while logging in");
        }
    }

    @Override
    public void Logout() throws RemoteException {
        //TODO implement logout?
    }

    @Override
    public boolean Register(User user) throws RemoteException {
        try {
            return userDAOManager.Register(user);
        } catch (SQLException e) {
            throw new RuntimeException("Error while registering");
        }
    }
}
