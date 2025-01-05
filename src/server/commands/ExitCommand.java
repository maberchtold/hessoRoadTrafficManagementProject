package server.commands;

import server.ResponseDTO;

public class ExitCommand implements Command {
    @Override
    public ResponseDTO execute() {
        return new ResponseDTO("success", "Connection closing...");
    }
}
