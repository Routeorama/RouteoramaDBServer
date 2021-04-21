package com.example.routeoramaserver.networking;

import com.example.routeoramaserver.callbacks.login.LoginServerCallback;
import com.example.routeoramaserver.login.LoginFromDB;
import com.example.routeoramaserver.models.User;
import com.example.routeoramaserver.networking.callbacks.ServerCallback;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ServerImpl implements IServer, ServerCallback {
    private LoginServerCallback loginFromDB;
    private final Lock lock = new ReentrantLock();
    private static final String uri = "mongodb+srv://RouteoramaDBAdmin:routeorama123@routeorama.tujmk.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

    public ServerImpl() {
        try {
            UnicastRemoteObject.exportObject(this, 0);

            try (MongoClient client = MongoClients.create(uri)) {
                MongoCollection<Document> cookies = client.getDatabase("RouteoramaTest").getCollection("Users");
                addingDefaultUsers(cookies);
            }
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
    public LoginServerCallback getLoginServer() throws RemoteException {
        if (loginFromDB == null) {
            synchronized (lock) {
                if (loginFromDB == null)
                    try {
                        loginFromDB = new LoginFromDB(uri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return loginFromDB;
    }
}
