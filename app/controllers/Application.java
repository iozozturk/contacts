package controllers;

import models.Contact;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import play.Play;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Result;
import services.ContactService;
import utils.XMLUtils;
import views.html.index;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Application extends Controller {

    public static final String UPLOAD_DIR = Play.application().configuration().getString("store.file.dir");

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static play.mvc.Result upload() throws IOException, ParserConfigurationException, SAXException {

        MultipartFormData body = request().body().asMultipartFormData();
        MultipartFormData.FilePart filePart = body.getFile("xmlFile");

        if (filePart != null) {

            File file = utils.FileUtils.moveFilePartToDir(UPLOAD_DIR, filePart);

            NodeList nodeList = XMLUtils.parseXMLFile(file);

            List<Contact> contacts = ContactService.parseXMLContacts(nodeList);
            long count = contacts.stream().map(ContactService::saveContacts).count();


            return ok("File uploaded and " + count + " contacts saved.");

        } else {
            flash("error", "Missing file");
            return badRequest();
        }
    }

}
