package com.example.routeoramaserver.networking;

import com.example.routeoramaserver.Login.ILoginFromDB;
import com.example.routeoramaserver.Login.LoginFromDB;
import com.example.routeoramaserver.LoginServerCallback;
import com.example.routeoramaserver.models.User;
import com.example.routeoramaserver.networking.callbacks.ServerCallback;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import org.bson.Document;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        if(loginFromDB == null) {
         synchronized (lock) {
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
