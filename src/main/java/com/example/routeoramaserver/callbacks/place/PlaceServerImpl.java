package com.example.routeoramaserver.callbacks.place;

import com.example.routeoramaserver.dao.places.IPlaceDAO;
import com.example.routeoramaserver.dao.places.PlaceDAOManager;
import com.example.routeoramaserver.models.Place;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class PlaceServerImpl implements IPlaceServerCallback {

    private IPlaceDAO placeDAO;

    public PlaceServerImpl(){
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        placeDAO = new PlaceDAOManager();
    }

    @Override
    public Place NewPlace(Place place) throws RemoteException{
        try {
            return placeDAO.NewPlace(place);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating a new place");
        }
    }

    @Override
    public Place GetPlace(String placeName) throws RemoteException {
        try {
            return placeDAO.GetPlace(placeName);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating a new place");
        }
    }

    @Override
    public List<Place> GetPlacesInBounds(List<Double> bounds) throws RemoteException {
        try {
            return placeDAO.getPlacesInBounds(bounds);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching bounds for a place");
        }
    }

    @Override
    public void FollowThePlace(int placeId, int userId) throws RemoteException {
        try {
            placeDAO.FollowThePlace(placeId,userId);
        } catch (SQLException e) {
            throw new RuntimeException("Error following the place");
        }
    }

    @Override
    public void UnfollowThePlace(int placeId, int userId) throws RemoteException {
        try {
            placeDAO.UnfollowThePlace(placeId,userId);
        } catch (SQLException e) {
            throw new RuntimeException("Error following the place");
        }
    }

    @Override
    public boolean IsAlreadyFollowed(int placeId, int userId) throws RemoteException {
        try {
            return placeDAO.IsAlreadyFollowed(placeId,userId);
        } catch (SQLException e) {
            throw new RuntimeException("Error isAlreadyFollowed place");
        }
    }

    @Override
    public List<String> GetMostFollowedPlaces() throws RemoteException {
        try {
            return placeDAO.GetMostFollowedPlaces();
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting most followed places");
        }
    }

    @Override
    public List<String> GetMostLikedPlaces() throws RemoteException {
        try {
            return placeDAO.GetMostLikedPlaces();
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting most liked places");
        }
    }

    @Override
    public List<Place> SearchForPlaces(String filter) throws RemoteException {
        try {
            return placeDAO.SearchForPlaces(filter);
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting places for search bar");
        }
    }
}
