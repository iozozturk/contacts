package utils;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import play.Logger;
import play.api.Play;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by ismet on 15/11/15.
 */
public class XMLUtils {

    public static NodeList parseXMLFile(File file) throws ParserConfigurationException, IOException, SAXException {
        //todo impl exception controls
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        return doc.getElementsByTagName("contact");
    }

    public static boolean validateDocument(File document) throws IOException, SAXException {
        URL schemaFile = Play.getFile("samples/contacts.xsd", Play.current()).toURI().toURL();
        Source xmlFile = new StreamSource(document);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
            Logger.debug(xmlFile.getSystemId() + " is valid");
        } catch (SAXException e) {
            Logger.debug(xmlFile.getSystemId() + " is NOT valid");
            Logger.debug("Reason: " + e.getLocalizedMessage());
            return false;
        }
        return true;
    }
}
