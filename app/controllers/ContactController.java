package controllers;

import actions.Exceptions;
import actions.LoggingFilter;
import akka.actor.ActorRef;
import common.UpdateQueryMsg;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.ActorService;
import services.ContactService;

import java.util.ArrayList;

/**
 * Created by ismet on 19/11/15.
 */
@With(LoggingFilter.class)
@Exceptions.Handled
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
