package com.example.routeoramaserver.rmi.users;

import com.example.routeoramaserver.callbacks.user.UserServerCallback;
import com.example.routeoramaserver.models.User;
import com.example.routeoramaserver.dao.users.IUserDAO;
import com.example.routeoramaserver.dao.users.UserDAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServer implements UserServerCallback {

    private final IUserDAO userDAO;

    public UserServer() {
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        userDAO = new UserDAO();
    }

    @Override
    public User Login(String username, String password) throws RemoteException {
        return userDAO.login(username, password);
    }

    @Override
    public void Logout() throws RemoteException {

    }

    @Override
    public boolean Register(User user) throws RemoteException {
        return userDAO.register(user);
    }
}
