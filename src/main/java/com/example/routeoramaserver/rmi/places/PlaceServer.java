package com.example.routeoramaserver.rmi.places;

import com.example.routeoramaserver.callbacks.place.PlaceServerCallback;
import com.example.routeoramaserver.dao.places.IPlaceDAO;
import com.example.routeoramaserver.dao.places.PlaceDAO;
import com.example.routeoramaserver.models.Place;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PlaceServer implements PlaceServerCallback {

    private IPlaceDAO placeDAO;

    public PlaceServer(){
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        placeDAO = new PlaceDAO();
    }
    @Override
    public Place NewPlace(Place place) throws RemoteException {
        return placeDAO.NewPlace(place);
    }
}
