package com.example.routeoramaserver.callbacks.place;

import com.example.routeoramaserver.models.Place;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlaceServerCallback extends Remote {
    Place NewPlace(Place place) throws RemoteException;
}
