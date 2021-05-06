package com.example.routeoramaserver.dao.places;

import com.example.routeoramaserver.db.DatabaseConnection;
import com.example.routeoramaserver.models.Location;
import com.example.routeoramaserver.models.Place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceDAOManager implements IPlaceDAO {

    private DatabaseConnection databaseConnection;

    public PlaceDAOManager()
    {
        try {
            databaseConnection = DatabaseConnection.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Place NewPlace(Place place) {
        Connection connection = null;
        PreparedStatement statement = null;
        Place newPlace = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("INSERT INTO \"Place\" (\"name\", \"description\", \"userid\")" +
                    " values (?,?,?)");
            statement.setString(1, place.getName());
            statement.setString(2, place.getDescription());
            statement.setInt(3, place.getUserId());
            int m = statement.executeUpdate();
            if (m==1) {
                System.out.println("Inserted new place successfully");
                newPlace = GetPlace(place.getName());
                System.out.println(newPlace);
                Location location = insertLocation(place.getLocation(), newPlace.getId());
                if(location != null) {
                    newPlace.setLocation(location);
                    return newPlace;
                }
                else return null;
            }
            else
                System.out.println("Insertion of new place failed");

        }
        catch (SQLException ex) {
            System.out.println("Place with specified credentials already exists " + ex.getMessage());
        }
        finally {
            if (statement != null) try { statement.close(); } catch (Exception e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return newPlace;
    }

    @Override
    public Place GetPlace(String placeName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Place newPlace = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT * FROM \"Place\" WHERE \"name\" = ?");
            statement.setString(1, placeName);
            resultSet = statement.executeQuery();

            if(resultSet.next()){
                int placeId = resultSet.getInt("placeid");
                String placeName1 = resultSet.getString("name");
                String description = resultSet.getString("description");
                int followCount = resultSet.getInt("followCount");
                int userid = resultSet.getInt("userid");
                System.out.println("New place fetched");
                newPlace = new Place(placeId, placeName1, description, followCount, userid);

                if(newPlace != null) {
                    newPlace.setLocation(getLocationForThePlace(placeId));
                }
            }
        }
        catch (SQLException ex) { System.out.println("Get place with specified credentials already exists" + ex.getMessage()); }
        finally {
            if (resultSet != null) try { resultSet.close(); } catch (Exception e) { e.printStackTrace(); }
            if (statement != null) try { statement.close(); } catch (Exception e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return newPlace;
    }

    public Location insertLocation(Location location, int placeId) {
        Connection connection = null;
        PreparedStatement statement = null;
        Location newLocation = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("INSERT INTO \"Location\" (\"lat\", \"lng\", \"country\", \"city\", \"placeid\")" +
                    " values (?,?,?,?,?)");
            statement.setDouble(1, location.getLat());
            statement.setDouble(2, location.getLng());
            statement.setString(3, location.getCountry());
            statement.setString(4, location.getCity());
            statement.setInt(5, placeId);
            int m = statement.executeUpdate();
            if (m==1) {
                System.out.println("Inserted new location successfully");
                return location;
            }
            else
                System.out.println("Insertion of the new location failed");

        }
        catch (SQLException ex) {
            System.out.println("Location with specified credentials already exists" + ex.getMessage());
        }
        finally {
            if (statement != null) try { statement.close(); } catch (Exception e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return newLocation;
    }

    private Location getLocationForThePlace(int placeId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Location newLocation = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT * FROM \"Location\" WHERE \"placeid\" = ?");
            statement.setInt(1, placeId);
            resultSet = statement.executeQuery();

            if(resultSet.next()){
                int lat = resultSet.getInt("lat");
                int lng = resultSet.getInt("lng");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                System.out.println("New location fetched");
                newLocation = new Location(lat, lng, country, city);
                return newLocation;
            }
        }
        catch (SQLException ex) { System.out.println("Get Location with specified credentials already exists" + ex.getMessage()); }
        finally {
            if (resultSet != null) try { resultSet.close(); } catch (Exception e) { e.printStackTrace(); }
            if (statement != null) try { statement.close(); } catch (Exception e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return newLocation;
    }

    @Override
    public List<Place> getPlacesInBounds(List<Double> bounds) {
        double latSW = bounds.get(1), lngSW = bounds.get(0), latNE = bounds.get(3), lngNE = bounds.get(2);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Place> sendBack = new ArrayList<>();

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT * FROM \"Place\"" +
                    "FULL OUTER JOIN \"Location\" ON \"Place\".placeid = \"Location\".placeid WHERE \"Location\".lat BETWEEN ? AND ?\n" +
                    "AND \"Location\".lng BETWEEN ? AND ?");
            statement.setDouble(1, latSW);
            statement.setDouble(2, latNE);
            statement.setDouble(3, lngSW);
            statement.setDouble(4, lngNE);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                int placeid = resultSet.getInt("placeid");
                String placeName1 = resultSet.getString("name");
                String description = resultSet.getString("description");
                int followCount = resultSet.getInt("followCount");
                int userid = resultSet.getInt("userid");
                System.out.println("New place fetched");
                Place newPlace = new Place(placeid, placeName1, description, followCount, userid);

                double lat = resultSet.getDouble("lat");
                double lng = resultSet.getDouble("lng");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                System.out.println("New location fetched");

                if(newPlace != null) {
                    newPlace.setLocation(new Location(lat, lng, country, city));
                    sendBack.add(newPlace);
                }
            }

            return sendBack;
        }
        catch (SQLException ex) { System.out.println("Get location with specified credentials failed" + ex.getMessage()); }
        finally {
            if (resultSet != null) try { resultSet.close(); } catch (Exception e) { e.printStackTrace(); }
            if (statement != null) try { statement.close(); } catch (Exception e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return sendBack;
    }
}
