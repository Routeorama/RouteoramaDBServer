package com.example.routeoramaserver.callbacks.place.post;

import com.example.routeoramaserver.models.Post;
import com.example.routeoramaserver.models.PostContainer;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPostServerCallback extends Remote {
    Post NewPost(Post post) throws RemoteException;
    boolean DeletePost(int postID) throws RemoteException;
    Post GetPost(int postID) throws RemoteException;
    PostContainer LoadPostsFromChannel(int placeID, int postID) throws RemoteException;
    void LikeThePost(int postId, int userId) throws RemoteException;
    void UnlikeThePost(int postId, int userId) throws RemoteException;
    void IsAlreadyLiked(int postId, int userId) throws RemoteException;
}
