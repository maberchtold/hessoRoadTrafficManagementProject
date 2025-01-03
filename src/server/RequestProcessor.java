package server;

import client.RequestDTO;
import graph.Graph;
import algorithms.Dijkstra;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestProcessor implements Runnable {
    private final Socket clientSocket;
    private final Graph graph;

    public RequestProcessor(Socket clientSocket, Graph graph) {
        this.clientSocket = clientSocket;
        this.graph = graph;  // The shared graph object that stores the road network
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            boolean running = true;
            while (running) {
                RequestDTO request = (RequestDTO) in.readObject();
                String command = request.getCommand();
                String source = request.getSource();
                String destination = request.getDestination();
                int travelTime = request.getTravelTime();

                ResponseDTO response;

                if ("UPDATE".equals(command)) {
                    try {
                        graph.updateEdgeWeight(source, destination, travelTime);
                        response = new ResponseDTO("success", "Travel time updated successfully.");
                    } catch (IllegalArgumentException e) {
                        response = new ResponseDTO("error", e.getMessage());
                    }
                } else if ("PATH".equals(command)) {
                    Dijkstra.PathResult pathResult = Dijkstra.shortestPath(graph, source, destination);
                    if (pathResult.getPath().isEmpty()) {
                        response = new ResponseDTO("error", "No path found between " + source + " and " + destination + ".");
                    } else {
                        response = new ResponseDTO("success", pathResult.toString());
                    }
                } else if ("EXIT".equalsIgnoreCase(command)) {
                    response = new ResponseDTO("success", "Connection closing...");
                    running = false;
                } else {
                    response = new ResponseDTO("error", "Invalid command.");
                }

                out.writeObject(response);
                out.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
