package com.example.routeoramaserver.dao.users;

import com.example.routeoramaserver.db.DatabaseConnection;
import com.example.routeoramaserver.enumClasses.Role;
import com.example.routeoramaserver.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class UserDAOManager implements IUserDAO {

    private DatabaseConnection databaseConnection;

    public UserDAOManager()
    {
        try {
            databaseConnection = DatabaseConnection.getInstance();
        } catch (SQLException e) {
            System.out.println("Could not connect to database" + e.getMessage());
        }
    }

    @Override
    public User Login(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT * FROM \"user\" WHERE \"username\" = ? AND \"password\" = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if(resultSet.next()){
                int userId = resultSet.getInt("userId");
                String username1 = resultSet.getString("username");
                String password1 = resultSet.getString("password");
                String displayName = resultSet.getString("displayname");
                java.sql.Date dob = resultSet.getDate("dob");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                Date date = resultSet.getDate("datecreated");
                byte[] photo = resultSet.getBytes("photo");
                String photoType = resultSet.getString("photoType");

                user = new User(userId, username1, password1, dob, Role.valueOf(role), displayName, date, email);
                user.setPhoto(photo);
                user.setPhotoType(photoType);
            }
        }
        catch (SQLException ex) { System.out.println("Could not validate the login" + ex.getMessage()); }
        finally {
            if (resultSet != null) try { resultSet.close(); } catch (Exception e) { e.printStackTrace(); }
            if (statement != null) try { statement.close(); } catch (Exception e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return user;
    }

    @Override
    public boolean Register(User user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("INSERT INTO \"user\" (\"username\", \"password\", \"displayname\", \"dob\", \"email\", \"role\", \"datecreated\", \"photo\", \"photoType\")" +
                    " values (?,?,?,?,?,?,?,?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUsername());
            statement.setDate(4, user.getDob());
            statement.setString(5, user.getEmail());
            statement.setString(6, String.valueOf(user.getRole()));

            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            user.setDateCreated(date);

            statement.setDate(7, (java.sql.Date) user.getDateCreated());
            statement.setBytes(8, user.getPhoto());
            statement.setString(9, user.getPhotoType());

            int m = statement.executeUpdate();
            if (m==1) {
                System.out.println("User inserted successfully");
                return true;
            }
            else
                System.out.println("User insertion failed");

        }
        catch (SQLException ex) {
            System.out.println("User with specified credentials already exists" + ex.getMessage());
        }
        finally {
            if (statement != null) try { statement.close(); } catch (Exception e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return false;
    }

    @Override
    public String UpdateUser(User user) {
        User userFromDB = getUserFromDB(user);
        if(userFromDB != null)
        {
            Connection connection = null;
            PreparedStatement statement = null;
            int resultSet;
            try {
                connection = databaseConnection.getConnection();
                connection.setSchema("Routeourama");
                statement = connection.prepareStatement("UPDATE \"user\"\n" +
                        "SET \"userid\" = ?, \"username\" = ?, \"password\" = ?, \"displayname\" = ?, \"dob\" = ?, \"email\" = ?, \"role\" = ?, \"datecreated\" = ?, \"photo\" = ?, \"photoType\" = ?\n" +
                        "WHERE \"userid\" = ?");

                if(user.getUserId() == userFromDB.getUserId())
                    statement.setInt(1, userFromDB.getUserId());
                else
                    statement.setInt(1, user.getUserId());

                if(user.getUsername().equals(userFromDB.getUsername()))
                    statement.setString(2, userFromDB.getUsername());
                else
                    statement.setString(2, user.getUsername());

                if(user.getPassword().equals(userFromDB.getPassword()))
                    statement.setString(3, userFromDB.getPassword());
                else
                    statement.setString(3, user.getPassword());

                if(user.getDisplayName().equals(userFromDB.getDisplayName()))
                    statement.setString(4, userFromDB.getDisplayName());
                else
                    statement.setString(4, user.getDisplayName());

                if(user.getDob().equals(userFromDB.getDob()))
                    statement.setDate(5, userFromDB.getDob());
                else
                    statement.setDate(5, user.getDob());

                if(user.getEmail().equals(userFromDB.getEmail()))
                    statement.setString(6, userFromDB.getEmail());
                else
                    statement.setString(6, user.getEmail());

                if(user.getRole().toString().equals(userFromDB.getRole().toString()))
                    statement.setString(7, userFromDB.getRole().toString());
                else
                    statement.setString(7, user.getRole().toString());

                statement.setDate(8, (java.sql.Date) userFromDB.getDateCreated());

                if(user.getPhoto().length == 0)
                    statement.setBytes(9, userFromDB.getPhoto());
                else
                    statement.setBytes(9, user.getPhoto());

                if(user.getPhotoType().equals(""))
                    statement.setString(10, userFromDB.getPhotoType());
                else
                    statement.setString(10, user.getPhotoType());

                if(user.getUserId() == userFromDB.getUserId())
                    statement.setInt(11, userFromDB.getUserId());
                else
                    statement.setInt(11, user.getUserId());

                resultSet = statement.executeUpdate();
                if(resultSet != 0)
                    return "Update of the profile successful";
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
            finally {
                if (statement != null) try { statement.close(); } catch (Exception e) { e.printStackTrace(); }
                if (connection != null) try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return "Update of the profile un-successful";
    }

    private User getUserFromDB(User user)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User recivedUser = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT * FROM \"user\" WHERE \"userid\" = ?");
            statement.setInt(1, user.getUserId());
            resultSet = statement.executeQuery();

            if(resultSet.next()){
                int userId = resultSet.getInt("userId");
                String username = resultSet.getString("username");
                String password1 = resultSet.getString("password");
                String displayName = resultSet.getString("displayname");
                java.sql.Date dob = resultSet.getDate("dob");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                Date date = resultSet.getDate("datecreated");
                byte[] photo = resultSet.getBytes("photo");
                String photoType = resultSet.getString("photoType");

                recivedUser = new User(userId, username, password1, dob, Role.valueOf(role), displayName, date, email);
                recivedUser.setPhoto(photo);
                recivedUser.setPhotoType(photoType);
                return recivedUser;
            }
        }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (resultSet != null) try { resultSet.close(); } catch (Exception e) { e.printStackTrace(); }
            if (statement != null) try { statement.close(); } catch (Exception e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return null;
    }
}
