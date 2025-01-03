package server.commands;

import server.ResponseDTO;

public class InvalidCommand implements Command {
    private final String errorMessage;

    public InvalidCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public ResponseDTO execute() {
        return new ResponseDTO("error", errorMessage);
    }
}
