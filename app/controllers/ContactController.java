package controllers;

import akka.actor.ActorRef;
import common.UpdateQueryMsg;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ActorService;
import services.ContactService;

import java.util.ArrayList;

/**
 * Created by ismet on 19/11/15.
 */
public class ContactController extends Controller {

    public static Result getContacts(String query) {
        UpdateQueryMsg updateQueryMsg = new UpdateQueryMsg(query, ctx().request().remoteAddress());

        ActorService.eventRef.tell(updateQueryMsg, ActorRef.noSender());

        if (query.equals("")) {
            return ok(Json.toJson(new ArrayList()));
        }
        return ok(Json.toJson(ContactService.findByName(query)));
    }
}
