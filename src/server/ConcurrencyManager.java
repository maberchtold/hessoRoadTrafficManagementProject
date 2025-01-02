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
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
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
