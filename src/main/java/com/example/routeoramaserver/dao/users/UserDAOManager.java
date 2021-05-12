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
                username = resultSet.getString("username");
                String password1 = resultSet.getString("password");
                String displayName = resultSet.getString("displayname");
                java.sql.Date dob = resultSet.getDate("dob");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                Date date = resultSet.getDate("datecreated");

                user = new User(userId, username, password1, dob, Role.valueOf(role), displayName, date, email);
                //TODO encrypt the password
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
    public void logout() {
        //TODO logout
    }

    @Override
    public boolean Register(User user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("INSERT INTO \"user\" (\"username\", \"password\", \"displayname\", \"dob\", \"email\", \"role\", \"datecreated\")" +
                    " values (?,?,?,?,?,?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUsername());
            statement.setDate(4, user.getDob());
            statement.setString(5, user.getEmail());
            statement.setString(6, String.valueOf(user.getRole()));

            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            user.setDateCreated(date);

            statement.setDate(7, (java.sql.Date) user.getDateCreated());

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
}
