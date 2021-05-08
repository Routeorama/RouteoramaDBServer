package com.example.routeoramaserver.dao.places.posts;

import com.example.routeoramaserver.models.Post;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface IPostDAO {
    Post NewPost(Post post) throws SQLException;
    boolean DeletePost(int postID) throws SQLException;
    Post GetPost(int postID) throws SQLException;
    Post GetPost(String title) throws SQLException;
    HashMap<Boolean, List<Post>> LoadPostsFromChannel(int placeID, int postID) throws SQLException;
}
