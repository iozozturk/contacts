package controllers;

import play.Logger;
import play.libs.EventSource;
import play.mvc.Controller;
import play.mvc.Result;
import services.ActorService;
import views.html.main;

public class Application extends Controller {

    public static Result index() {
        return ok(main.render("Contacts"));
    }

    public static Result registerEventSource(){
        String remoteAddress = request().remoteAddress();
        Logger.info(remoteAddress + " - SSE connected");

        EventSource eventSource = new EventSource() {
            @Override
            public void onConnected() {
                EventSource currentSocket = this;
                ActorService.eventRef.tell(currentSocket, null);
            }
        };
        return ok(eventSource);
    }

}
