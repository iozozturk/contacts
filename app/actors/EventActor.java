package actors;

import akka.actor.UntypedActor;
import common.ActorMessage;
import common.DBCompleteMsg;
import common.EventSourceMsg;
import play.Logger;
import play.libs.EventSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ismet özöztürk on 21/11/15.
 */
public class EventActor extends UntypedActor {

    Map<String, EventSource> eventSourceMap = new HashMap<>();
    Map<String, String> remoteAddrQuery = new HashMap<>();

    @Override
    public void onReceive(Object message) throws Exception {

        // Handle connections
        if (message instanceof ActorMessage) {
            String remoteAddress = ((ActorMessage) message).getRemoteAddress();

            if (message instanceof EventSourceMsg) {

                final EventSource eventSource = ((EventSourceMsg) message).getEventSource();

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

            if (message instanceof DBCompleteMsg) {
                if (eventSourceMap.containsKey(remoteAddress))
                    eventSourceMap.get(remoteAddress).send(EventSource.Event.event("db_finish"));
            }
        }

    }
}