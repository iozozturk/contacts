package services;

import models.Contact;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by ismet on 15/11/15.
 */
public class ContactService {

    public static void saveContacts(Contact contact) {
        Datastore datastore = DBService.getMorphiaClient();

        Query<Contact> findContact = datastore.createQuery(Contact.class)
                .field("name").equal(contact.getName())
                .field("lastName").equal(contact.getLastName());

        UpdateOperations<Contact> updatePhones = datastore.createUpdateOperations(Contact.class).addAll("phones", contact.getPhones(), false);
        datastore.update(findContact, updatePhones, true);
    }

    public static List<Contact> parseXMLContacts(NodeList nodeList) {
        Stream<Node> nodeStream = IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item);

        Function<Node, Contact> parseNode = node -> {
            Element element = (Element) node;
            Contact contact = new Contact();
            List<String> phones = new ArrayList<>();
            contact.setName(element.getElementsByTagName("name").item(0).getTextContent());
            contact.setLastName(element.getElementsByTagName("lastName").item(0).getTextContent());
            phones.add(element.getElementsByTagName("phone").item(0).getTextContent());
            contact.setPhones(phones);
            return contact;
        };

        return nodeStream.map(parseNode).collect(Collectors.toList());
    }

    public static List<Contact> findByName(String name) {
        Datastore datastore = DBService.getMorphiaClient();
        Query<Contact> contacts = datastore.find(Contact.class).field("name").startsWithIgnoreCase(name);
        return contacts.asList();
    }
}
