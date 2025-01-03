package server;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import graph.Graph;
import graph.Edge;
import graph.Node;

public class ServerUI extends JFrame {

    private GraphPanel graphPanel;

    public ServerUI(Graph graph) {
        setTitle("Road System Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        graphPanel = new GraphPanel();
        add(graphPanel);

        // Populate the graph panel with existing nodes and edges
        for (Node node : graph.getNodes().values()) {
            switch (node.getName()) {
                case "Brig":
                    graphPanel.addNode("B", 100, 200);
                    break;
                case "Visp":
                    graphPanel.addNode("V", 500, 200);
                    break;
                case "Stalden":
                    graphPanel.addNode("S", 420, 90);
                    break;
                case "Eyholz":
                    graphPanel.addNode("E", 280, 240);
                    break;
                case "Raron":
                    graphPanel.addNode("R", 650, 300);
                    break;
                case "Gamsen":
                    graphPanel.addNode("G", 200, 300);
                    break;
            }

        }
        for (Map.Entry<String, Node> entry : graph.getNodes().entrySet()) {
            Node sourceNode = entry.getValue();
            for (Edge edge : sourceNode.getEdges()) {
                graphPanel.addEdge(sourceNode.getName().substring(0, 1), edge.getDestination().getName().substring(0, 1));
            }
        }

    }

    public void showUI() {
        setVisible(true);
    }

    private static class GraphPanel extends JPanel {
        private final List<NodeUI> nodes;
        private final List<EdgeUI> edges;

        public GraphPanel() {
            nodes = new ArrayList<>();
            edges = new ArrayList<>();
        }

        public void addNode(String name, int x, int y) {
            nodes.add(new NodeUI(name, x, y));
            repaint();
        }

        public void addEdge(String source, String destination) {
            NodeUI sourceNode = findNodeByName(source);
            NodeUI destinationNode = findNodeByName(destination);

            if (sourceNode != null && destinationNode != null) {
                edges.add(new EdgeUI(sourceNode, destinationNode));
                repaint();
            }
        }

        private NodeUI findNodeByName(String name) {
            return nodes.stream().filter(node -> node.name.equals(name)).findFirst().orElse(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw edges
            g2d.setColor(Color.BLACK);
            for (EdgeUI edge : edges) {
                g2d.drawLine(edge.source.x, edge.source.y, edge.destination.x, edge.destination.y);
            }

            // Draw nodes
            for (NodeUI node : nodes) {
                g2d.setColor(Color.BLUE);
                g2d.fillOval(node.x - 10, node.y - 10, 20, 20);
                g2d.setColor(Color.WHITE);
                g2d.drawString(node.name, node.x - 5, node.y + 5);
            }
        }

        private static class NodeUI {
            String name;
            int x, y;

            public NodeUI(String name, int x, int y) {
                this.name = name;
                this.x = x;
                this.y = y;
            }
        }

        private static class EdgeUI {
            NodeUI source;
            NodeUI destination;

            public EdgeUI(NodeUI source, NodeUI destination) {
                this.source = source;
                this.destination = destination;
            }
        }
    }
}
