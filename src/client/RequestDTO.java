package client;

import java.io.Serializable;

public class RequestDTO implements Serializable {
    private String command;
    private String source;
    private String destination;
    private int travelTime;

    public RequestDTO(String command, String source, String destination, int travelTime) {
        this.command = command;
        this.source = source;
        this.destination = destination;
        this.travelTime = travelTime;
    }

    public String getCommand() {
        return command;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getTravelTime() {
        return travelTime;
    }
}
