/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geoloc.models;

import com.geoloc.beans.Cyclist;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.bson.Document;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import org.bson.types.ObjectId;

/**
 *
 * @author Lucas
 */
@Stateless
public class CyclistModel {

    @EJB
    MongoClientProvider mongoClientProvider;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Cyclist> collection;

    @PostConstruct
    public void init() {
        mongoClient = mongoClientProvider.getMongoClient();

        CodecRegistry codecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        mongoDatabase = mongoClient.getDatabase("GeolocCyclist").withCodecRegistry(codecRegistry);
        collection = mongoDatabase.getCollection("Cyclist", Cyclist.class);
    }

    public void create(Cyclist cyclist) {
        collection.insertOne(cyclist);
    }

    public List<Cyclist> read() {
        List<Cyclist> list = new ArrayList<>();
        collection.find().iterator().forEachRemaining(list::add);
        return list;
    }

    public void delete(Cyclist cyclist) {
        collection.deleteOne(eq("_id", cyclist.getId()));
    }
    
    public void update(Cyclist cyclist){ 
        Bson filter = eq("_id", cyclist.getId());
        collection.findOneAndReplace(filter, cyclist);
    }
    
    public Cyclist find(ObjectId id){
        Cyclist c = (Cyclist) collection.find(eq("_id", id)).first();
        return c;
    }
    
}
