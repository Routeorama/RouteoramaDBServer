package com.example.routeoramaserver.callbacks.place.post;

import com.example.routeoramaserver.models.Post;
import com.example.routeoramaserver.models.PostContainer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface IPostServerCallback extends Remote {
    Post NewPost(Post post) throws RemoteException;
    boolean DeletePost(int postID) throws RemoteException;
    Post GetPost(int postID) throws RemoteException;
    PostContainer LoadPostsFromChannel(int placeID, int postID) throws RemoteException;
}
