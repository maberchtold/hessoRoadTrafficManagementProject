package server;

import java.io.Serializable;

public class ResponseDTO implements Serializable {
    private String status;
    private String message;

    public ResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
