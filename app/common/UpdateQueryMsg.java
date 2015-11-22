package common;

/**
 * Created by ismet on 22/11/15.
 */
public class UpdateQueryMsg implements ActorMessage {
    private String remoteAddress;

    @Override
    public String getRemoteAddress() {
        return remoteAddress;
    }
}
