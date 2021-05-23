package com.example.routeoramaserver.dao.places.posts;

import com.example.routeoramaserver.models.Comment;
import com.example.routeoramaserver.models.CommentContainer;
import com.example.routeoramaserver.models.Post;
import com.example.routeoramaserver.models.PostContainer;

import java.sql.SQLException;
import java.util.List;

public interface IPostDAO {
    Post NewPost(Post post, List<String> tags) throws SQLException;
    boolean DeletePost(int postID) throws SQLException;
    Post GetPost(int postID) throws SQLException;
    Post GetPost(String title) throws SQLException;
    PostContainer LoadPostsFromChannel(int placeID, int postID) throws SQLException;
    void LikeThePost(int postId, int userId) throws SQLException;
    void UnlikeThePost(int postId, int userId) throws SQLException;
    boolean IsAlreadyLiked(int postId, int userId) throws SQLException;
    PostContainer GetPostsForNewsFeed(int userId) throws SQLException;
    PostContainer LoadMorePostsForNewsFeed(int userId, int postId) throws SQLException;
    void Comment(Comment comment);
    void DeleteComment(Comment comment);
    CommentContainer GetCommentForPost(int postId);
    CommentContainer LoadMoreComments(int postId, Comment lastComment);
}
