package com.example.routeoramaserver.dao.places;

import com.example.routeoramaserver.models.Place;

import java.sql.SQLException;

public interface IPlaceDAO {
    Place NewPlace(Place place) throws SQLException;
    Place GetPlace(String place) throws SQLException;
}
