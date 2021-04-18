package test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class Connection implements IConnection, ServerCallback {
    public Connection() {
        try{
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public String getTestingStuff() throws RemoteException {
        String uri = "mongodb+srv://RouteoramaDBAdmin:routeorama123@routeorama.tujmk.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
        try (MongoClient client = MongoClients.create(uri)){
            MongoCollection<Document> cookies = client.getDatabase("RouteoramaTest").getCollection("Test");
            cookies.insertOne(new Document("string", "this is smth you should look at!"));
            return cookies.find().first().toString();
        }
    }
}
