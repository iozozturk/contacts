package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

/**
 * Created by ismet on 15/11/15.
 */
@Entity
public class Contact {

    @Id
    private ObjectId id;

    private String name;
    private String lastName;
    private List<String> phones;
}
