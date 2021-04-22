package com.example.routeoramaserver.models.dao.users;

import com.example.routeoramaserver.models.User;

public interface IUserDAO {
    User login(String username, String password);

    void logout();

    boolean register(User user);

}
