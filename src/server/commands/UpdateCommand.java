package server.commands;

import graph.Graph;
import server.ResponseDTO;

public class UpdateCommand implements Command {
    private final Graph graph;
    private final String source;
    private final String destination;
    private final int travelTime;

    public UpdateCommand(Graph graph, String source, String destination, int travelTime) {
        this.graph = graph;
        this.source = source;
        this.destination = destination;
        this.travelTime = travelTime;
    }

    @Override
    public ResponseDTO execute() {
        try {
            graph.updateEdgeWeight(source, destination, travelTime);
            return new ResponseDTO("success", "Travel time updated successfully.");
        } catch (IllegalArgumentException e) {
            return new ResponseDTO("error", e.getMessage());
        }
    }
}
