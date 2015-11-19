package controllers;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import play.Play;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Result;
import services.ContactService;
import utils.XMLUtils;
import views.html.main;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Application extends Controller {

    public static Result index() {
        return ok(main.render("Contacts"));
    }

}
