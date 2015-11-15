package utils;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by ismet on 15/11/15.
 */
public class XMLUtils {

    public static void parseXMLFile(File file) throws ParserConfigurationException, IOException, SAXException {
        //todo impl exception controls
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList contactList = doc.getElementsByTagName("contact");


        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
    }
}
