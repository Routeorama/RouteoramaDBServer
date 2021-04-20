package com.example.routeoramaserver.networking;

import com.example.routeoramaserver.login.LoginFromDB;
import com.example.routeoramaserver.callbacks.login.LoginServerCallback;
import com.example.routeoramaserver.networking.callbacks.ServerCallback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ServerImpl implements IServer, ServerCallback {


    private LoginServerCallback loginFromDB;
    private final Lock lock = new ReentrantLock();

    public ServerImpl() {
        try {
            UnicastRemoteObject.exportObject(this, 0);
            /*String uri = "mongodb+srv://RouteoramaDBAdmin:routeorama123@routeorama.tujmk.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
            try (MongoClient client = MongoClients.create(uri)) {
                MongoCollection<Document> cookies = client.getDatabase("RouteoramaTest").getCollection("Users");
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd:MM:yyyy hh:mm:ss");
                User hardUser = new User("George", "george", "21.01.1999", now.format(dateTimeFormatter));
                Document document = new Document();
                document.append("username", hardUser.getUsername());
                document.append("password", hardUser.getPassword());
                document.append("dob", hardUser.getDob());
                document.append("dateCreated", hardUser.getDateCreated());
                cookies.insertOne(document);
            }*/
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /*public String getTestingStuff() throws RemoteException {
        String uri = "mongodb+srv://RouteoramaDBAdmin:routeorama123@routeorama.tujmk.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
        try (MongoClient client = MongoClients.create(uri)) {
            MongoCollection<Document> cookies = client.getDatabase("RouteoramaTest").getCollection("Test");
            cookies.insertOne(new Document("string", "this is smth you should look at!"));
            return cookies.find().first().toString();
        }
    }*/

    @Override
    public LoginServerCallback getLoginServer() throws RemoteException {
        if (loginFromDB == null) {
            synchronized (lock) {
                if (loginFromDB == null)
                    try {
                        loginFromDB = new LoginFromDB();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return loginFromDB;
    }
}
