package com.example.routeoramaserver.models;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable {
    private static final long serialVersionUID = 6529685098267757692L;
    private double x, y;
    private String country;
    private String city;

    public Location(double x, double y, String country, String city) {
        this.x = x;
        this.y = y;
        this.country = country;
        this.city = city;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.x, x) == 0 && Double.compare(location.y, y) == 0 && Objects.equals(country, location.country) && Objects.equals(city, location.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, country, city);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
