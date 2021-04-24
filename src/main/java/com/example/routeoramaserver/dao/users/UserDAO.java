package com.example.routeoramaserver.dao.users;

import com.example.routeoramaserver.db.DB;
import com.example.routeoramaserver.models.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class UserDAO implements IUserDAO {
    private MongoCollection<Document> users;

    public User login(String username, String password) {
        try (MongoClient client = MongoClients.create(DB.URI)) {
            users = client.getDatabase(DB.Name).getCollection(DB.UserCollection);
            for (Document user : users.find()) {
                String newUsername = (String) user.get("username");
                String newPassword = (String) user.get("password");

                if (newUsername.equals(username) && newPassword.equals(password)) {
                    String dob = (String) user.get("dob");
                    String dateCreated = (String) user.get("dateCreated");
                    return new User(newUsername, newPassword, dob, dateCreated);
                }
            }
        }
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public boolean register(User user) {
        try (MongoClient client = MongoClients.create(DB.URI)) {
            users = client.getDatabase(DB.Name).getCollection(DB.UserCollection);

            for (Document existingUser : users.find()) {
                if (existingUser.get("username").equals(user.getUsername())) {
                    return false;
                }
            }
            Document newUser = new Document();
            newUser.append("username", user.getUsername());
            newUser.append("password", user.getPassword());
            newUser.append("dob", user.getDob());
            newUser.append("dateCreated", user.getDateCreated());
            newUser.append("email", user.getEmail());
            newUser.append("role", user.getRole());
            users.insertOne(newUser);
            return true;
        }
    }
}
