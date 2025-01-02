package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<String, Node> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public void addNode(String name) {
        nodes.putIfAbsent(name, new Node(name));
    }

    public void addEdge(String source, String destination, int weight) {
        Node sourceNode = nodes.get(source);
        Node destinationNode = nodes.get(destination);
        if (sourceNode != null && destinationNode != null) {
            sourceNode.addEdge(new Edge(destinationNode, weight));
        }
    }

    public List<Edge> getEdges() {
        List<Edge> edgeList = new ArrayList<>();
        for (Node node : nodes.values()) {
            edgeList.addAll(node.getEdges());
        }
        return edgeList;
    }

    public void updateEdgeWeight(String source, String destination, int weight) {
        Node sourceNode = nodes.get(source);
        if (sourceNode != null) {
            for (Edge edge : sourceNode.getEdges()) {
                if (edge.getDestination().getName().equals(destination)) {
                    edge.setWeight(weight); // Update the edge weight
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Edge from " + source + " to " + destination + " does not exist.");
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }
}
