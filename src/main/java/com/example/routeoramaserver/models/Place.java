package com.example.routeoramaserver.models;

import java.io.Serializable;
import java.util.Objects;


public class Place implements Serializable {

    private static final long serialVersionUID = 6529685098267757691L;
    private int id;
    private String name;
    private String description;
    private int userId;
    private int followCount;
    private Location location;

    public Place() {
        super();
    }

    public Place(int id, String name, String description, int followCount, int userId, Location location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.followCount = followCount;
        this.location = location;
    }

    public Place(String name, String description, int userId, Location location) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.location = location;
    }

    public Place(int placeId, String placeName, String description, int followCount, int userid) {
        this.id = placeId;
        this.name = placeName;
        this.description = description;
        this.followCount = followCount;
        this.userId = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return id == place.id && userId == place.userId && followCount == place.followCount && name.equals(place.name) && description.equals(place.description) && location.equals(place.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, userId, followCount, location);
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", followCount=" + followCount +
                ", location=" + location +
                '}';
    }
}
