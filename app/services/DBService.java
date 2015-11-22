package services;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import play.Play;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ismet on 15/11/15.
 */
public class DBService {

    private static final String DB_NAME = Play.application().configuration().getString("mongodb.name");
    private static final String USER = Play.application().configuration().getString("mongodb.name");
    private static final String PASS = Play.application().configuration().getString("mongodb.pass");
    private static final String HOST = Play.application().configuration().getString("mongodb.host");
    private static final int PORT = Play.application().configuration().getInt("mongodb.port");

    static MongoCredential credential = MongoCredential.createCredential(USER, DB_NAME, PASS.toCharArray());
    static List<MongoCredential> credentials = new ArrayList<>();

    static final Morphia morphia = MorphiaClient.morphia;
    static final Datastore datastore = morphia.createDatastore(getMongoClient(), DB_NAME);

    static {
        morphia.mapPackage("models");
        datastore.ensureIndexes();
        credentials.add(credential);
    }

    private static class MongoDBClient {
        static ServerAddress address = new ServerAddress(HOST, PORT);
        private static final MongoClient mongoClient = new MongoClient(address, credentials);
    }

    private static class MorphiaClient {
        private static final Morphia morphia = new Morphia();
    }

    public static MongoClient getMongoClient() {
        return MongoDBClient.mongoClient;
    }

    public static Datastore getMorphiaClient() {
        return datastore;
    }

}
