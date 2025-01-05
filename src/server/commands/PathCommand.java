package server.commands;

import algorithms.Dijkstra;
import graph.Graph;
import server.ResponseDTO;

public class PathCommand implements Command {
    private final Graph graph;
    private final String source;
    private final String destination;

    public PathCommand(Graph graph, String source, String destination) {
        this.graph = graph;
        this.source = source;
        this.destination = destination;
    }

    @Override
    public ResponseDTO execute() {
        Dijkstra.PathResult result = Dijkstra.shortestPath(graph, source, destination);
        if (result.getPath().isEmpty()) {
            return new ResponseDTO("error", "No path found between " + source + " and " + destination + ".");
        } else {
            return new ResponseDTO("success", result.toString());
        }
    }
}
