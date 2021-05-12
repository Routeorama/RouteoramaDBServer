package com.example.routeoramaserver.callbacks.place.post;

import com.example.routeoramaserver.dao.places.posts.IPostDAO;
import com.example.routeoramaserver.dao.places.posts.PostDAOManager;
import com.example.routeoramaserver.models.Post;
import com.example.routeoramaserver.models.PostContainer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class PostServerImpl implements IPostServerCallback{
    private IPostDAO postDAO;

    public PostServerImpl(){
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        postDAO = new PostDAOManager();
    }

    @Override
    public Post NewPost(Post post) throws RemoteException {
        try {
            return postDAO.NewPost(post);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating post");
        }
    }

    @Override
    public boolean DeletePost(int postID) throws RemoteException {
        try {
            return postDAO.DeletePost(postID);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting post");
        }
    }

    @Override
    public Post GetPost(int postID) throws RemoteException {
        try{
            return postDAO.GetPost(postID);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching post");
        }
    }

    @Override
    public PostContainer LoadPostsFromChannel(int placeID, int postID) throws RemoteException {
        try{
            return postDAO.LoadPostsFromChannel(placeID, postID);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching posts");
        }
    }
}
