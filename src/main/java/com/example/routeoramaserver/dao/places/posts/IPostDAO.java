package com.example.routeoramaserver.dao.places.posts;

import com.example.routeoramaserver.models.Post;

import java.sql.SQLException;

public interface IPostDAO {
    Post NewPost(Post post) throws SQLException;
    boolean DeletePost(int postID) throws SQLException;
    Post GetPost(int postID) throws SQLException;
}
