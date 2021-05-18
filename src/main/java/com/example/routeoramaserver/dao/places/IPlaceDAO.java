package com.example.routeoramaserver.dao.places;

import com.example.routeoramaserver.models.Place;

import java.sql.SQLException;
import java.util.List;

public interface IPlaceDAO {
    Place NewPlace(Place place) throws SQLException;
    Place GetPlace(String place) throws SQLException;
    List<Place> getPlacesInBounds(List<Double> bounds) throws SQLException;
    void FollowThePlace(int placeId, int userId) throws SQLException;
    void UnfollowThePlace(int placeId, int userId) throws SQLException;
    boolean IsAlreadyFollowed(int placeId, int userId) throws SQLException;
    List<String> GetMostFollowedPlaces() throws SQLException;
    List<String> GetMostLikedPlaces() throws SQLException;
}
