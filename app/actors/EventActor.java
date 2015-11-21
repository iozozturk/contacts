package actors;

import akka.actor.UntypedActor;
import play.Logger;
import play.libs.EventSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ismet özöztürk on 21/11/15.
 */
public class EventActor extends UntypedActor {

    List<EventSource> connections = new ArrayList<>();
    Map<String, EventSource> eventSourceMap = new HashMap<>();

    public static final String DB_FINISH = "DB Operations Finished";

    @Override
    public void onReceive(Object message) throws Exception {
        // Handle connections
        if (message instanceof EventSource) {
            final EventSource eventSource = (EventSource) message;

            if (connections.contains(eventSource)) {
                // Browser is disconnected
                connections.remove(eventSource);
                Logger.info("Browser disconnected (" + connections.size() + " browsers currently connected)");
            } else {
                // Register disconnected callback
                eventSource.onDisconnected(() -> self().tell(eventSource, null));
                // New browser connected
                connections.add(eventSource);
                Logger.info("New browser connected (" + connections.size() + " browsers currently connected)");
            }
        }
        // Tick, send time to all connected browsers
        if (DB_FINISH.equals(message)) {
            for (EventSource es : connections) {
                es.send(EventSource.Event.event("db_finish"));
            }
        }
    }


}
