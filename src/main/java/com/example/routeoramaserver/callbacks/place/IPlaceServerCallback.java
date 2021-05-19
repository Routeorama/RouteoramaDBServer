package com.example.routeoramaserver.callbacks.place;

import com.example.routeoramaserver.models.Place;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPlaceServerCallback extends Remote {
    Place NewPlace(Place place) throws RemoteException;
    Place GetPlace(String place) throws RemoteException;
    List<Place> GetPlacesInBounds(List<Double> bounds) throws RemoteException;
    void FollowThePlace(int placeId, int userId) throws RemoteException;
    void UnfollowThePlace(int placeId, int userId) throws RemoteException;
    boolean IsAlreadyFollowed(int placeId, int userId) throws RemoteException;
    List<String> GetMostFollowedPlaces() throws RemoteException;
    List<String> GetMostLikedPlaces() throws RemoteException;
    List<Place> SearchForPlaces(String filter) throws RemoteException;
}
