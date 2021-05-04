package com.example.routeoramaserver.callbacks.place;

import com.example.routeoramaserver.models.Place;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlaceServerCallback extends Remote {
    Place NewPlace(Place place) throws RemoteException;
    Place GetPlace(String place) throws RemoteException;
}
