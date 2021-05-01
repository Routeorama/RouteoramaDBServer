package com.example.routeoramaserver.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    private static DatabaseConnection instance;
    private String plswork = "jdbc:postgresql://mpkvuqcd:yzVzTqine2R5KdQ68eIWN7epbsrdXYlI@dumbo.db.elephantsql.com:5432/mpkvuqcd?currentSchema=Routeourama";
    private String url = "jdbc:postgresql://dumbo.db.elephantsql.com:5432/mpkvuqcd";
    private String username = "mpkvuqcd";
    private String password = "yzVzTqine2R5KdQ68eIWN7epbsrdXYlI";


    private DatabaseConnection() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }


    public static synchronized DatabaseConnection getInstance() throws
            SQLException
    {
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, username, password);
    }
}
