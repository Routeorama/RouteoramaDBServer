package com.example.routeoramaserver.callbacks.place.post;

import com.example.routeoramaserver.models.Post;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPostServerCallback extends Remote {
    Post NewPost(Post post) throws RemoteException;
    boolean DeletePost(int postID) throws RemoteException;
    Post GetPost(int postID) throws RemoteException;
}
