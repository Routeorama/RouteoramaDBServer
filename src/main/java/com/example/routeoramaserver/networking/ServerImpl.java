package com.example.routeoramaserver.networking;

import com.example.routeoramaserver.callbacks.place.PlaceServerCallback;
import com.example.routeoramaserver.callbacks.user.UserServerCallback;
import com.example.routeoramaserver.rmi.places.PlaceServer;
import com.example.routeoramaserver.rmi.users.UserServer;
import com.example.routeoramaserver.models.User;
import com.example.routeoramaserver.networking.callbacks.ServerCallback;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ServerImpl implements ServerCallback {
    private UserServerCallback userServerCallback;
    private final Lock lock = new ReentrantLock();

    private PlaceServerCallback placeServerCallback;
    private final Lock lock1 = new ReentrantLock();

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
    private void addingDefaultUsers(MongoCollection<Document> cookies) {
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
    }

    @Override
    public UserServerCallback getUserServer() throws RemoteException {
        if (userServerCallback == null) {
            synchronized (lock) {
                if (userServerCallback == null)
                    try {
                        userServerCallback = new UserServer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return userServerCallback;
    }

    @Override
    public PlaceServerCallback getPlaceServer() throws RemoteException {
        if (placeServerCallback == null) {
            synchronized (lock1) {
                if (placeServerCallback == null)
                    try {
                        placeServerCallback = new PlaceServer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return placeServerCallback;
    }
}
