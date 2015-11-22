package common;

/**
 * Created by ismet on 22/11/15.
 */
public class DBCompleteMsg implements ActorMessage {

    private String remoteAddress;

    public DBCompleteMsg(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    public String getRemoteAddress() {
        return remoteAddress;
    }
}
