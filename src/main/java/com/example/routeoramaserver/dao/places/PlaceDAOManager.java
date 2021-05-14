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

    public PlaceDAOManager() {
        try {
            databaseConnection = DatabaseConnection.getInstance();
        } catch (SQLException e) {
            System.out.println("Could not connect to database" + e.getMessage());
        }
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

            if (resultSet.next()) {
                int lat = resultSet.getInt("lat");
                int lng = resultSet.getInt("lng");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                System.out.println("New location fetched");
                newLocation = new Location(lat, lng, country, city);
                return newLocation;
            }
        } catch (SQLException e) {
            System.out.println("Could not fetch the specified location" + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newLocation;
    }

    private Location insertLocation(Location location, int placeId) {
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
            if (m == 1) {
                System.out.println("Inserted new location successfully");
                return location;
            } else
                System.out.println("Insertion of the new location failed");

        } catch (SQLException e) {
            System.out.println("Location with specified credentials already exists" + e.getMessage());
        } finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newLocation;
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
            if (m == 1) {
                System.out.println("Inserted new place successfully");
                newPlace = GetPlace(place.getName());
                Location location = insertLocation(place.getLocation(), newPlace.getId());
                if (location != null) {
                    newPlace.setLocation(location);
                    return newPlace;
                } else return null;
            } else
                System.out.println("Insertion of the new place failed");

        } catch (SQLException e) {
            System.out.println("Place already exists " + e.getMessage());
        } finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

            if (resultSet.next()) {
                int placeId = resultSet.getInt("placeid");
                String placeName1 = resultSet.getString("name");
                String description = resultSet.getString("description");
                int followCount = resultSet.getInt("followCount");
                int userid = resultSet.getInt("userid");
                System.out.println("New place fetched");
                newPlace = new Place(placeId, placeName1, description, followCount, userid);

                if (newPlace != null) {
                    newPlace.setLocation(getLocationForThePlace(placeId));
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not fetch the specified place" + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newPlace;
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

            while (resultSet.next()) {
                int placeid = resultSet.getInt("placeid");
                String placeName1 = resultSet.getString("name");
                String description = resultSet.getString("description");
                int followCount = resultSet.getInt("followCount");
                int userid = resultSet.getInt("userid");
                Place newPlace = new Place(placeid, placeName1, description, followCount, userid);

                double lat = resultSet.getDouble("lat");
                double lng = resultSet.getDouble("lng");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");


                if (newPlace != null) {
                    newPlace.setLocation(new Location(lat, lng, country, city));
                    sendBack.add(newPlace);
                }
            }
            System.out.println("Places within bounds fetched.");
            return sendBack;
        } catch (SQLException e) {
            System.out.println("Could not fetch the places within bounds" + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sendBack;
    }

    @Override
    public boolean FollowThePlace(int placeId, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;

        if (!IsAlreadyFollowed(placeId, userId)) {
            try {
                connection = databaseConnection.getConnection();
                connection.setSchema("Routeourama");
                statement = connection.prepareStatement("INSERT INTO \"Follow\" VALUES (?,?)");
                statement.setInt(1, userId);
                statement.setInt(2, placeId);

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    System.out.println("Creating follow request failed");
                    return false;
                }

                System.out.println("Follow request successfully executed");
            } catch (SQLException e) {
                System.out.println("Creating follow request failed" + e.getMessage());
            } finally {
                if (statement != null) try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (connection != null) try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        else {
            try {
                connection = databaseConnection.getConnection();
                connection.setSchema("Routeourama");
                statement = connection.prepareStatement("DELETE FROM \"Follow\" WHERE \"userid\" = ? AND \"placeid\" = ?");
                statement.setInt(1, userId);
                statement.setInt(2, placeId);

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    System.out.println("Unfollow request failed");
                    return true;
                }

                System.out.println("Unfollow request successfully executed");
            } catch (SQLException e) {
                System.out.println("Deleting follow request failed" + e.getMessage());
            } finally {
                if (statement != null) try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (connection != null) try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }

    @Override
    public boolean IsAlreadyFollowed(int placeId, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT * FROM \"Follow\" WHERE \"userid\" = ? AND \"placeid\" = ?");
            statement.setInt(1, userId);
            statement.setInt(2, placeId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userid = resultSet.getInt("userid");
                int placeid = resultSet.getInt("placeid");

                if (placeId == placeid && userId == userid) {
                    System.out.println("User is following the place already");
                    return true;
                }
            }
            System.out.println("User is not following the place");
        } catch (SQLException e) {
            System.out.println("Could not fetch the follow for user" + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
