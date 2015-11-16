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
    static final Morphia morphia = MorphiaClient.morphia;
    static final Datastore datastore = morphia.createDatastore(getMongoClient(), DB_NAME);

    static{
        morphia.mapPackage("models");
        datastore.ensureIndexes();
    }

    private static class MongoDBClient {
       private static final MongoClient mongoClient = new MongoClient();
    }

    private static class MorphiaClient {
        private static final Morphia morphia = new Morphia();
    }

    public static MongoClient getMongoClient(){
        return MongoDBClient.mongoClient;
    }

    public static Datastore getMorphiaClient(){
        return datastore;
    }

}
