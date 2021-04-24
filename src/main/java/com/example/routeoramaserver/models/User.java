package com.example.routeoramaserver.models;

import com.example.routeoramaserver.enumClasses.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class User implements Serializable {

    // mock-up user obj with auto-generated methods used as a placeholder
    private static final long serialVersionUID = 6529685098267757690L;
    private String username;
    private String password;
    private String dob;
    private String dateCreated;
    private String email;
    private Role role;

    public User(String username, String password, String dob, String dateCreated) {
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.dateCreated = dateCreated;
    }

    public User(String username, String password, String dob, String email, Role role) {
        this.username = username;
        this.password = password;
        this.dob = dob;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd:MM:yyyy hh:mm:ss");
        this.dateCreated = now.format(dateTimeFormatter);
        this.email = email;
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(dob, user.dob) && Objects.equals(dateCreated, user.dateCreated) && Objects.equals(email, user.email) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, dob, dateCreated, email, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dob='" + dob + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
