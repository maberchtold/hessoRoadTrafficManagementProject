package server.commands;

import server.ResponseDTO;

public interface Command {
    ResponseDTO execute();
}
