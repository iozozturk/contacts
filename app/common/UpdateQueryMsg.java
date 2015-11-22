package common;

/**
 * Created by ismet on 22/11/15.
 */
public class UpdateQueryMsg implements ActorMessage {
    private String remoteAddress;
    private String query;

    public UpdateQueryMsg(String query, String remoteAddress){
        this.remoteAddress = remoteAddress;
        this.query = query;
    }

    @Override
    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getQuery() {
        return query;
    }
}
