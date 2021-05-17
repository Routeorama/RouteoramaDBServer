package com.example.routeoramaserver.callbacks.place.post;

import com.example.routeoramaserver.dao.places.posts.IPostDAO;
import com.example.routeoramaserver.dao.places.posts.PostDAOManager;
import com.example.routeoramaserver.models.Post;
import com.example.routeoramaserver.models.PostContainer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

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
    public Post NewPost(Post post, List<String> tags) throws RemoteException {
        try {
            return postDAO.NewPost(post, tags);
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

    @Override
    public void LikeThePost(int postId, int userId) throws RemoteException {
        try {
            postDAO.LikeThePost(postId, userId);
        } catch (SQLException e) {
            throw new RuntimeException("Error liking the post");
        }
    }

    @Override
    public void UnlikeThePost(int postId, int userId) throws RemoteException {
        try {
            postDAO.UnlikeThePost(postId, userId);
        } catch (SQLException e) {
            throw new RuntimeException("Error liking the post");
        }
    }

    @Override
    public boolean IsAlreadyLiked(int postId, int userId) throws RemoteException {
        try {
            return postDAO.IsAlreadyLiked(postId,userId);
        } catch (SQLException e) {
            throw new RuntimeException("Error isAlreadyLiked post");
        }
    }

    @Override
    public PostContainer GetPostsForNewsFeed(int userId) throws RemoteException {
        try {
            return postDAO.GetPostsForNewsFeed(userId);
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting posts for feed.");
        }
    }

    @Override
    public PostContainer LoadMorePostsForNewsFeed(int userId, int postId) throws RemoteException {
        try {
            return postDAO.LoadMorePostsForNewsFeed(userId, postId);
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting posts for feed.");
        }
    }
}
