package actors;

import akka.actor.UntypedActor;
import play.Logger;
import play.libs.EventSource;
import play.libs.F;
import common.MessageType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ismet özöztürk on 21/11/15.
 */
public class EventActor extends UntypedActor {

    Map<String, EventSource> eventSourceMap = new HashMap<>();

    @Override
    public void onReceive(Object message) throws Exception {

        // Handle connections
        if (message instanceof F.Tuple) {
            String remoteAddress = (String) ((F.Tuple) message)._2;

            if (((F.Tuple) message)._1 instanceof EventSource) {

                final EventSource eventSource = (EventSource) ((F.Tuple) message)._1;

                if (eventSourceMap.containsKey(remoteAddress)) {
                    // Browser is disconnected
                    eventSourceMap.remove(remoteAddress);
                    Logger.info("Browser disconnected (" + eventSourceMap.size() + " browsers currently connected)");
                } else {
                    // Register disconnected callback
                    eventSource.onDisconnected(() -> self().tell(eventSource, null));
                    // New browser connected
                    eventSourceMap.put(remoteAddress, eventSource);
                    Logger.info("New browser connected (" + eventSourceMap.size() + " browsers currently connected)");
                }
            }

            if (((F.Tuple) message)._1 instanceof MessageType) {
                switch ((MessageType) ((F.Tuple) message)._1) {
                    case DB_FINISH:
                        if (eventSourceMap.containsKey(remoteAddress))
                            eventSourceMap.get(remoteAddress).send(EventSource.Event.event("db_finish"));
                        break;
                    default:
                        Logger.warn("Undefined msg");
                }
            }
        }

    }
}