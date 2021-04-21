package com.example.routeoramaserver.login;

import com.example.routeoramaserver.callbacks.login.LoginServerCallback;
import com.example.routeoramaserver.models.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;

public class LoginFromDB implements LoginServerCallback {

    private String uri;

    public LoginFromDB(String uri) {
        try {
            UnicastRemoteObject.exportObject(this, 0);
            this.uri = uri;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User Login(String username, String password) throws RemoteException {
        try (MongoClient client = MongoClients.create(uri)) {
            MongoCollection<Document> cookies = client.getDatabase("RouteoramaTest").getCollection("Users");

            FindIterable<Document> documentFindIterable = cookies.find();
            Iterator it = documentFindIterable.iterator();
            while (it.hasNext()) {
                Document user = (Document)it.next();
                String newUsername = (String) user.get("username");
                String newPassword = (String) user.get("password");

                if(newUsername.equals(username) && newPassword.equals(password))
                {
                    return new User(newUsername, newPassword, (String) user.get("dob"), (String) user.get("dateCreated"));
                }
            }
        }
        return null;
    }

    @Override
    public void Logout() throws RemoteException {

    }
}
