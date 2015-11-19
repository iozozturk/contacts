package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ContactService;

/**
 * Created by ismet on 19/11/15.
 */
public class ContactController extends Controller {

    public static Result getContacts(String query){
        return ok(Json.toJson(ContactService.findByName(query)));
    }
}
