package controllers;

import akka.actor.ActorRef;
import play.Logger;
import play.libs.EventSource;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import services.ActorService;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(main.render("Contacts"));
    }

    public static Result registerEventSource() {
        String remoteAddress = request().remoteAddress();
        Logger.info(remoteAddress + " - SSE connected");

        EventSource eventSource = new EventSource() {
            @Override
            public void onConnected() {
                EventSource currentSocket = this;
                F.Tuple<EventSource, String> message = new F.Tuple<>(currentSocket, remoteAddress);
                ActorService.eventRef.tell(message, ActorRef.noSender());
            }
        };
        return ok(eventSource);
    }

}
