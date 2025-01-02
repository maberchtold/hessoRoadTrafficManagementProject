package server;

import client.RequestDTO;
import graph.Graph;

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

            // Read the RequestDTO from the client
            RequestDTO request = (RequestDTO) in.readObject();
            String command = request.getCommand();
            String source = request.getSource();
            String destination = request.getDestination();
            int travelTime = request.getTravelTime();

            ResponseDTO response;

            // Process the command
            if ("UPDATE".equals(command)) {
                graph.addEdge(source, destination, travelTime);
                response = new ResponseDTO("success", "Travel time updated successfully.");
            } else if ("PATH".equals(command)) {
                // Simulate shortest path calculation (replace with actual logic)
                String shortestPath = "Shortest path from " + source + " to " + destination + " is ...";
                response = new ResponseDTO("success", shortestPath);
            } else {
                response = new ResponseDTO("error", "Invalid command.");
            }

            // Send the ResponseDTO back to the client
            out.writeObject(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
