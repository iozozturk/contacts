package common;

import play.libs.EventSource;

/**
 * Created by ismet on 22/11/15.
 */
public class EventSourceMsg implements ActorMessage {
    private String remoteAddress;
    private EventSource eventSource;

    public EventSourceMsg(EventSource eventSource, String remoteAddress){
        this.remoteAddress = remoteAddress;
        this.eventSource = eventSource;
    }

    public EventSource getEventSource() {
        return eventSource;
    }

    @Override
    public String getRemoteAddress() {
        return remoteAddress;
    }
}
