package com.example.routeoramaserver.dao.users;

import com.example.routeoramaserver.models.User;

import java.sql.SQLException;

public interface IUserDAO {
    User login(String username, String password) throws SQLException;
    void logout() throws SQLException;
    boolean register(User user) throws SQLException;
}
