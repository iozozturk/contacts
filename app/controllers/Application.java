package controllers;

import Actions.Exceptions;
import Actions.LoggingFilter;
import akka.actor.ActorRef;
import common.EventSourceMsg;
import play.Logger;
import play.libs.EventSource;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import services.ActorService;
import views.html.main;

@With(LoggingFilter.class)
@Exceptions.Handled
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
                EventSourceMsg eventSourceMsg = new EventSourceMsg(currentSocket, remoteAddress);
                ActorService.eventRef.tell(eventSourceMsg, ActorRef.noSender());
            }
        };
        return ok(eventSource);
    }

}
