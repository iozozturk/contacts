package services;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import play.Play;

/**
 * Created by ismet on 15/11/15.
 */
public class DBService {

    private static final String DB_NAME =  Play.application().configuration().getString("mongodb.name");


    private static class MongoDBClient {
       private static final MongoClient mongoClient = new MongoClient();
    }

    public static MongoClient getMongoClient(){
        return MongoDBClient.mongoClient;
    }

    public static void setupMorphia(){
        final Morphia morphia = new Morphia();

        morphia.mapPackage("org.mongodb.morphia.example");

        final Datastore datastore = morphia.createDatastore(getMongoClient(), DB_NAME);
        datastore.ensureIndexes();
    }
}
