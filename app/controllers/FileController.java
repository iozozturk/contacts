package controllers;

import actors.EventActor;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import play.Play;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import services.ActorService;
import services.ContactService;
import utils.XMLUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by ismet on 19/11/15.
 */
public class FileController {
    public static final String UPLOAD_DIR = Play.application().configuration().getString("store.file.dir");

    public static Result upload(boolean validate) throws IOException, ParserConfigurationException, SAXException {

        Http.MultipartFormData body = Controller.request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("file");

        if (filePart != null) {

            File file = utils.FileUtils.moveFilePartToDir(UPLOAD_DIR, filePart);

            if (validate && !XMLUtils.validateDocument(file)) {
                return Results.badRequest("XML document is not valid against defined schema");
            }

            NodeList nodeList = XMLUtils.parseXMLFile(file);

            //parse xml to model and save to db asynchronously
            F.Promise.promise(() -> {
                ContactService.parseXMLContacts(nodeList).forEach(ContactService::saveContacts);
                Thread.sleep(3000); //Simulating a huge file taking time
                return null;
            }).map((n) -> {
                ActorService.eventRef.tell(EventActor.DB_FINISH, null);
                return null;
            });

            return Results.ok("File uploaded and processing contacts");

        } else {
            return Results.badRequest("Missing file");
        }
    }
}
