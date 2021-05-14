package com.example.routeoramaserver.dao.places.posts;

import com.example.routeoramaserver.models.Post;
import com.example.routeoramaserver.models.PostContainer;

import java.sql.SQLException;

public interface IPostDAO {
    Post NewPost(Post post) throws SQLException;
    boolean DeletePost(int postID) throws SQLException;
    Post GetPost(int postID) throws SQLException;
    Post GetPost(String title) throws SQLException;
    PostContainer LoadPostsFromChannel(int placeID, int postID) throws SQLException;
    void LikeThePost(int postId, int userId) throws SQLException;
    void UnlikeThePost(int postId, int userId) throws SQLException;
    boolean IsAlreadyLiked(int postId, int userId) throws SQLException;
}
