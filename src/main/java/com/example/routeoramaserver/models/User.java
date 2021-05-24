package com.example.routeoramaserver.models;

import com.example.routeoramaserver.enumClasses.Role;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;
    private int userId;
    private String username;
    private String password;
    private java.sql.Date dob;
    private Date dateCreated;
    private String email;
    private Role role;
    private String displayName;
    private byte[] photo;
    private String photoType;

    public User(int userId, String username, String password, java.sql.Date dob, Date dateCreated, String email, Role role, String displayName, byte[] photo, String photoType) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.dateCreated = dateCreated;
        this.email = email;
        this.role = role;
        this.displayName = displayName;
        this.photo = photo;
        this.photoType = photoType;
    }

    public User(int userId, String username, String password, java.sql.Date dob, Role role, String displayName, Date dateCreated, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.dateCreated = dateCreated;
        this.email = email;
        this.role = role;
        this.displayName = displayName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public java.sql.Date getDob() {
        return dob;
    }

    public void setDob(java.sql.Date dob) {
        this.dob = dob;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && username.equals(user.username) && password.equals(user.password) && dob.equals(user.dob) && dateCreated.equals(user.dateCreated) && email.equals(user.email) && role == user.role && displayName.equals(user.displayName) && Arrays.equals(photo, user.photo) && Objects.equals(photoType, user.photoType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(userId, username, password, dob, dateCreated, email, role, displayName, photoType);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dob=" + dob +
                ", dateCreated=" + dateCreated +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", displayName='" + displayName + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", photoType='" + photoType + '\'' +
                '}';
    }
}
