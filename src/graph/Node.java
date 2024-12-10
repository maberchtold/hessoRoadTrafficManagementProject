package graph;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String name;
    private final List<Edge> edges;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public String getName() {
        return name;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
