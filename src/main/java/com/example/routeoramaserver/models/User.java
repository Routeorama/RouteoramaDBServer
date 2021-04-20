package com.example.routeoramaserver.models;

import java.util.Date;
import java.util.Objects;

public class User {

    // mock-up user obj with auto-generated methods used as a placeholder
    private String username;
    private String password;
    private String dob;
    private String dateCreated;


    public User(String username, String password, String dob, String dateCreated) {
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.dateCreated = dateCreated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && password.equals(user.password) && dob.equals(user.dob) && dateCreated.equals(user.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, dob, dateCreated);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dob='" + dob + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
