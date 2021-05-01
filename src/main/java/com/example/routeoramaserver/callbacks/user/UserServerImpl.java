package com.example.routeoramaserver.callbacks.user;

import com.example.routeoramaserver.dao.users.UserDAOManager;
import com.example.routeoramaserver.models.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class UserServerImpl implements IUserServerCallback {

    private UserDAOManager userDAOManager;

    public UserServerImpl() throws SQLException {
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        userDAOManager = new UserDAOManager();
    }

    @Override
    public User Login(String username, String password) throws RemoteException {
        return userDAOManager.login(username, password);
    }

    @Override
    public void Logout() throws RemoteException {

    }

    @Override
    public boolean Register(User user) throws RemoteException {
        return userDAOManager.register(user);
    }
}
