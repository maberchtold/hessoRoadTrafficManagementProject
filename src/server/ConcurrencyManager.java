package server;

import graph.Graph;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyManager {
    private final int port;
    private final ExecutorService clientPool;
    private final Graph graph; // Shared graph object

    public ConcurrencyManager(int port) {
        this.port = port;
        this.clientPool = Executors.newFixedThreadPool(10);
        this.graph = new Graph(); // Initialize the graph here
        graph.addNode("Brig");
        graph.addNode("Visp");
        graph.addNode("Stalden");
        graph.addNode("Eyholz");
        graph.addNode("Raron");
        graph.addNode("Gamsen");
        graph.addEdge("Brig", "Stalden", 17);
        graph.addEdge("Stalden", "Brig", 17);
        graph.addEdge("Brig", "Visp", 10);
        graph.addEdge("Visp", "Brig", 10);
        graph.addEdge("Brig", "Eyholz", 6);
        graph.addEdge("Eyholz", "Brig", 6);
        graph.addEdge("Brig", "Gamsen", 4);
        graph.addEdge("Gamsen", "Brig", 4);
        graph.addEdge("Eyholz", "Visp", 5);
        graph.addEdge("Visp", "Eyholz", 5);
        graph.addEdge("Eyholz", "Gamsen", 4);
        graph.addEdge("Gamsen", "Eyholz", 4);
        graph.addEdge("Gamsen", "Visp", 8);
        graph.addEdge("Visp", "Gamsen", 8);
        graph.addEdge("Visp", "Stalden", 10);
        graph.addEdge("Stalden", "Visp", 10);
        graph.addEdge("Visp", "Raron", 5);
        graph.addEdge("Raron", "Visp", 5);


    }

    public Graph getGraph() {
        return graph;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            ServerUI serverUI = new ServerUI(graph);
            serverUI.showUI();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                // Pass both the client socket and the graph to RequestProcessor
                clientPool.execute(new RequestProcessor(clientSocket, graph));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
