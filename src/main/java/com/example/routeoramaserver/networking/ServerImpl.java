package com.example.routeoramaserver.networking;

import com.example.routeoramaserver.callbacks.place.IPlaceServerCallback;
import com.example.routeoramaserver.callbacks.place.PlaceServerImpl;
import com.example.routeoramaserver.callbacks.place.post.IPostServerCallback;
import com.example.routeoramaserver.callbacks.place.post.PostServerImpl;
import com.example.routeoramaserver.callbacks.user.IUserServerCallback;
import com.example.routeoramaserver.callbacks.user.UserServerImpl;
import com.example.routeoramaserver.networking.callbacks.ServerCallback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ServerImpl implements ServerCallback {
    private IUserServerCallback userServerCallback;
    private final Lock lock = new ReentrantLock();

    private IPlaceServerCallback placeServerCallback;
    private final Lock lock1 = new ReentrantLock();

    private IPostServerCallback postServerCallback;
    private final Lock lock2 = new ReentrantLock();

    public ServerImpl() {
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IUserServerCallback getUserServer() throws RemoteException {
        if (userServerCallback == null) {
            synchronized (lock) {
                if (userServerCallback == null)
                    try {
                        userServerCallback = new UserServerImpl();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return userServerCallback;
    }

    @Override
    public IPlaceServerCallback getPlaceServer() throws RemoteException {
        if (placeServerCallback == null) {
            synchronized (lock1) {
                if (placeServerCallback == null)
                    try {
                        placeServerCallback = new PlaceServerImpl();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return placeServerCallback;
    }

    @Override
    public IPostServerCallback getPostServer() throws RemoteException {
        if (postServerCallback == null) {
            synchronized (lock2) {
                if (postServerCallback == null)
                    try {
                        postServerCallback = new PostServerImpl();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return postServerCallback;
    }
}
