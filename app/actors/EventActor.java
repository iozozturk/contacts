package actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import common.*;
import models.Contact;
import play.Logger;
import play.libs.EventSource;
import play.libs.Json;
import scala.concurrent.duration.Duration;
import services.ActorService;
import services.ContactService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by ismet özöztürk on 21/11/15.
 */
public class EventActor extends UntypedActor {

    Map<String, EventSource> eventSourceMap = new HashMap<>();
    Map<String, String> remoteQueryMap = new HashMap<>();

    //Schedule query refresh message to update user queries
    static {
        ActorService.actorSystem.scheduler().schedule(
                Duration.Zero(),
                Duration.create(1000, TimeUnit.MILLISECONDS),
                ActorService.eventRef,
                new RefreshQueryResultsMsg(),
                ActorService.actorSystem.dispatcher(),
                ActorRef.noSender()
        );
    }

    @Override
    public void onReceive(Object message) throws Exception {

        // Handle connections
        if (message instanceof ActorMessage) {
            String remoteAddress = ((ActorMessage) message).getRemoteAddress();

            if (message instanceof EventSourceMsg) {

                final EventSource eventSource = ((EventSourceMsg) message).getEventSource();

                // Register disconnected callback
                eventSource.onDisconnected(() -> {
                    if (eventSourceMap.containsKey(remoteAddress))
                        eventSourceMap.remove(remoteAddress);
                    Logger.info("Browser disconnected (" + eventSourceMap.size() + " browsers currently connected)");
                });

                // New browser connected
                eventSourceMap.put(remoteAddress, eventSource);
                Logger.info("New browser connected (" + eventSourceMap.size() + " browsers currently connected)");
            }

            if (message instanceof DBCompleteMsg) {
                if (eventSourceMap.containsKey(remoteAddress))
                    eventSourceMap.get(remoteAddress).send(EventSource.Event.event("db_finish"));
            }

            if (message instanceof UpdateQueryMsg) {
                String query = ((UpdateQueryMsg) message).getQuery();

                if (query.equals("") && remoteQueryMap.containsKey(remoteAddress))
                    remoteQueryMap.remove(remoteAddress);
                else
                    remoteQueryMap.put(remoteAddress, query);
            }
        }

        if (message instanceof RefreshQueryResultsMsg) {
//            Logger.debug("Refreshing query results");
            remoteQueryMap.keySet().stream()
                    .filter(remote -> eventSourceMap.containsKey(remote))
                    .forEach(remote -> {
                        List<Contact> result = ContactService.findByName(remoteQueryMap.get(remote));
                        eventSourceMap.get(remote).send(EventSource.Event.event(Json.toJson(result)));
                    });
        }

    }
}