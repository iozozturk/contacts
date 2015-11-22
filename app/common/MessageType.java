package common;

/**
 * Created by ismet özöztürk on 22/11/15.
 */
public enum MessageType {
    DB_FINISH("db_finish"), REFRESH_QUERIES("refresh_queries");

    private final String message;

    MessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
