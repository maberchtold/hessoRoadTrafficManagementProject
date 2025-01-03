package server.commands;

import graph.Graph;

public class CommandFactory {
    private final Graph graph;

    public CommandFactory(Graph graph) {
        this.graph = graph;
    }

    public Command createCommand(String commandType, String source, String destination, int travelTime) {
        switch (commandType.toUpperCase()) {
            case "UPDATE":
                return new UpdateCommand(graph, source, destination, travelTime);
            case "PATH":
                return new PathCommand(graph, source, destination);
            case "EXIT":
                return new ExitCommand();
            default:
                return new InvalidCommand("Invalid command: " + commandType);
        }
    }
}
