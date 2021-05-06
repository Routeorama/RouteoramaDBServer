package com.example.routeoramaserver.callbacks.place;

import com.example.routeoramaserver.models.Place;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPlaceServerCallback extends Remote {
    Place NewPlace(Place place) throws RemoteException;
    Place GetPlace(String place) throws RemoteException;
    List<Place> getPlacesInBounds(List<Double> bounds) throws RemoteException;
}
