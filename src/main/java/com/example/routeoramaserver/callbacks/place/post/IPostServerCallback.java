package com.example.routeoramaserver.callbacks.place.post;

import com.example.routeoramaserver.models.Comment;
import com.example.routeoramaserver.models.CommentContainer;
import com.example.routeoramaserver.models.Post;
import com.example.routeoramaserver.models.PostContainer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPostServerCallback extends Remote {
    Post NewPost(Post post, List<String> tags) throws RemoteException;
    boolean DeletePost(int postID) throws RemoteException;
    Post GetPost(int postID) throws RemoteException;
    PostContainer LoadPostsFromChannel(int placeID, int postID) throws RemoteException;
    void LikeThePost(int postId, int userId) throws RemoteException;
    void UnlikeThePost(int postId, int userId) throws RemoteException;
    boolean IsAlreadyLiked(int postId, int userId) throws RemoteException;
    PostContainer GetPostsForNewsFeed(int userId) throws RemoteException;
    PostContainer LoadMorePostsForNewsFeed(int userId, int postId) throws RemoteException;
    void Comment(Comment comment) throws RemoteException;
    void DeleteComment(Comment comment) throws RemoteException;
    CommentContainer GetCommentForPost(int postId) throws RemoteException;
    CommentContainer LoadMoreComments(Comment lastComment) throws RemoteException;
    int GetCommentCount(int postId) throws RemoteException;
}
