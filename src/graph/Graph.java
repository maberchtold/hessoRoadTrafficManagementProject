package graph;

import java.util.HashMap;
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

    public void updateEdgeWeight(String source, String destination, int weight) {
        Node sourceNode = nodes.get(source);
        Node destinationNode = nodes.get(destination);
        if (sourceNode != null && destinationNode != null) {

        }
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }
}
