package com.example.routeoramaserver.models;

import java.io.Serializable;
import java.util.Objects;


public class Place implements Serializable {
    private static final long serialVersionUID = 6529685098267757691L;
    private int id;
    private String name;
    private String description;
    private String nameOfCreator;
    private Location location;

    public Place(int id, String name, String description, String nameOfCreator, Location location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.nameOfCreator = nameOfCreator;
        this.location = location;
    }

    public Place(String name, String description, String nameOfCreator, Location location) {
        this.name = name;
        this.description = description;
        this.nameOfCreator = nameOfCreator;
        this.location = location;
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

    public String getNameOfCreator() {
        return nameOfCreator;
    }

    public void setNameOfCreator(String nameOfCreator) {
        this.nameOfCreator = nameOfCreator;
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
        return id == place.id && Objects.equals(name, place.name) && Objects.equals(description, place.description) && Objects.equals(nameOfCreator, place.nameOfCreator) && Objects.equals(location, place.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, nameOfCreator, location);
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", nameOfCreator='" + nameOfCreator + '\'' +
                ", location=" + location +
                '}';
    }
}
