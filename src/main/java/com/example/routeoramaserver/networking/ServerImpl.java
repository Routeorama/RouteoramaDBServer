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

//            try (MongoClient client = MongoClients.create(DB.URI)) {
//                MongoCollection<Document> cookies = client.getDatabase("RouteoramaTest").getCollection("Users");
//                addingDefaultUsers(cookies);
//            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // method used to add default users
    /*private void addingDefaultUsers(MongoCollection<Document> cookies) {
        HashMap<User, Document> mapOfHardUsers = new HashMap<>();

        // 1. hardcoded user
        User hardUser = new User("George", "george", "21.01.1999", "20:04:2021 13:45:30");
        Document document = new Document();
        document.append("username", hardUser.getUsername());
        document.append("password", hardUser.getPassword());
        document.append("dob", hardUser.getDob());
        document.append("dateCreated", hardUser.getDateCreated());

        // 2. hardcoded user
        User hardUser2 = new User("Leon", "leon", "15.03.1987", "20:04:2021 13:45:30");
        Document document2 = new Document();
        document2.append("username", hardUser2.getUsername());
        document2.append("password", hardUser2.getPassword());
        document2.append("dob", hardUser2.getDob());
        document2.append("dateCreated", hardUser2.getDateCreated());

        // putting user and their document in a hash map
        mapOfHardUsers.put(hardUser, document);
        mapOfHardUsers.put(hardUser2, document2);

        // dropping collection (cookies) and inserting default users
        cookies.drop();
        cookies.insertOne(document);
        cookies.insertOne(document2);
    }*/

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
