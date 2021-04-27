package com.example.routeoramaserver.dao.places;

import com.example.routeoramaserver.db.DB;
import com.example.routeoramaserver.models.Place;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class PlaceDAO implements IPlaceDAO{

    private MongoCollection<Document> places;

    @Override
    public Place NewPlace(Place place) {
        try (MongoClient client = MongoClients.create(DB.URI)) {
            places = client.getDatabase(DB.Name).getCollection(DB.PlaceCollection);

            for (Document existingplace : places.find()) {
                if (existingplace.get("name").equals(place.getName())) {
                    return null;
                }
            }

            long count = places.countDocuments();
            Document newPlace = new Document();
            newPlace.append("id", count);
            newPlace.append("name", place.getName());
            newPlace.append("description", place.getDescription());
            newPlace.append("nameOfCreator", place.getNameOfCreator());

            newPlace.append("Location", new Document().append("x", place.getLocation().getX())
            .append("y", place.getLocation().getY()).append("country", place.getLocation().getCountry())
                    .append("city", place.getLocation().getCity()));

            places.insertOne(newPlace);
            return place;
        }
    }
}
