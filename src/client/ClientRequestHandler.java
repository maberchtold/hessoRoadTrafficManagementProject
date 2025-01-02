package client;

import server.ResponseDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientRequestHandler {
    private final String serverAddress;
    private final int serverPort;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientRequestHandler(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     * Initialize the connection to the server.
     */
    public void connect() {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to the server.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the connection to the server.
     */
    public void disconnect() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            System.out.println("Disconnected from the server.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a request to the server and receive a response.
     *
     * @param command    The command to send (e.g., "UPDATE", "PATH", "EXIT").
     * @param source     The source node for the request (optional for "EXIT").
     * @param destination The destination node for the request (optional for "EXIT").
     * @param travelTime The travel time to update (used only for "UPDATE").
     * @return The response from the server as a ResponseDTO.
     */
    public ResponseDTO sendRequest(String command, String source, String destination, int travelTime) {
        try {
            // Create and send the request
            RequestDTO request = new RequestDTO(command, source, destination, travelTime);
            out.writeObject(request);
            out.flush();

            // Receive and return the response
            return (ResponseDTO) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO("error", "Failed to communicate with the server.");
        }
    }

    /**
     * Interactive command-line interface for client requests.
     */
    public void start() {
        connect(); // Ensure the connection is established for CLI use
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Connected to server. Type your commands:");

            while (true) {
                System.out.print("> ");
                String command = scanner.nextLine();

                // Parse the command
                String[] parts = command.split(" ");
                String action = parts[0].toUpperCase();

                ResponseDTO response;

                if ("UPDATE".equals(action) && parts.length == 4) {
                    String source = parts[1];
                    String destination = parts[2];
                    int travelTime = Integer.parseInt(parts[3]);
                    response = sendRequest(action, source, destination, travelTime);
                } else if ("PATH".equals(action) && parts.length == 3) {
                    String source = parts[1];
                    String destination = parts[2];
                    response = sendRequest(action, source, destination, 0);
                } else if ("EXIT".equalsIgnoreCase(action)) {
                    response = sendRequest(action, "", "", 0);
                    System.out.println(response.getMessage());
                    break; // Exit the loop
                } else {
                    System.out.println("Invalid command. Use UPDATE <source> <destination> <travelTime> or PATH <source> <destination>");
                    continue;
                }

                // Display the server's response
                System.out.println("Server: " + response.getMessage());
            }
        } finally {
            disconnect(); // Ensure the connection is closed when exiting
        }
    }
}
