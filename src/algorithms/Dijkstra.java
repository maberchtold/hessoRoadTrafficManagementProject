package algorithms;

import graph.*;
import java.util.*;

public class Dijkstra {

    public static PathResult shortestPath(Graph graph, String startNode, String endNode) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(NodeDistance::getDistance));

        // Initialize distances
        for (String nodeName : graph.getNodes().keySet()) {
            distances.put(nodeName, Integer.MAX_VALUE);
        }
        distances.put(startNode, 0);
        priorityQueue.add(new NodeDistance(startNode, 0));

        while (!priorityQueue.isEmpty()) {
            NodeDistance current = priorityQueue.poll();
            String currentNode = current.getNodeName();

            // If we've reached the destination node, stop
            if (currentNode.equals(endNode)) {
                break;
            }

            for (Edge edge : graph.getNodes().get(currentNode).getEdges()) {
                String neighbor = edge.getDestination().getName();
                int newDist = distances.get(currentNode) + edge.getWeight();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previousNodes.put(neighbor, currentNode);
                    priorityQueue.add(new NodeDistance(neighbor, newDist));
                }
            }
        }

        // Reconstruct the shortest path
        List<String> path = reconstructPath(previousNodes, startNode, endNode);
        int distance = distances.get(endNode);
        return new PathResult(path, distance);
    }

    private static List<String> reconstructPath(Map<String, String> previousNodes, String startNode, String endNode) {
        List<String> path = new ArrayList<>();
        String currentNode = endNode;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = previousNodes.get(currentNode);
        }

        Collections.reverse(path);

        // If the path doesn't start with the start node, it means there's no valid path
        if (!path.isEmpty() && !path.get(0).equals(startNode)) {
            return new ArrayList<>(); // Empty path indicates no valid path
        }

        return path;
    }

    // Helper class for the priority queue
    private static class NodeDistance {
        private final String nodeName;
        private final int distance;

        public NodeDistance(String nodeName, int distance) {
            this.nodeName = nodeName;
            this.distance = distance;
        }

        public String getNodeName() {
            return nodeName;
        }

        public int getDistance() {
            return distance;
        }
    }

    // Result class to encapsulate the path and its distance
    public static class PathResult {
        private final List<String> path;
        private final int distance;

        public PathResult(List<String> path, int distance) {
            this.path = path;
            this.distance = distance;
        }

        public List<String> getPath() {
            return path;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public String toString() {
            if (path.isEmpty()) {
                return "No path found.";
            }
            return "Shortest distance: " + distance + "\nPath: " + String.join(" -> ", path);
        }
    }
}
