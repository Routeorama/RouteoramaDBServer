package com.example.routeoramaserver.models;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Serializable {
    private static final long serialVersionUID = 6529685098267757692L;
    private double lat, lng;
    private String country, city;

    public Location() {
        super();
    }

    public Location(double x, double y, String country, String city) {
        this.lat = x;
        this.lng = y;
        this.country = country;
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
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
        return Double.compare(location.lat, lat) == 0 && Double.compare(location.lng, lng) == 0 && Objects.equals(country, location.country) && Objects.equals(city, location.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lng, country, city);
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
