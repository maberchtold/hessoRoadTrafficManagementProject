package client;

import server.ResponseDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientRequestHandler {
    private final String serverAddress;
    private final int serverPort;

    public ClientRequestHandler(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
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
        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

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
        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to server. Type your commands:");

            while (true) {
                System.out.print("> ");
                String command = scanner.nextLine();

                // Prepare and send the RequestDTO
                String[] parts = command.split(" ");
                String action = parts[0].toUpperCase();

                RequestDTO request;
                if ("UPDATE".equals(action) && parts.length == 4) {
                    String source = parts[1];
                    String destination = parts[2];
                    int travelTime = Integer.parseInt(parts[3]);
                    request = new RequestDTO(action, source, destination, travelTime);
                } else if ("PATH".equals(action) && parts.length == 3) {
                    String source = parts[1];
                    String destination = parts[2];
                    request = new RequestDTO(action, source, destination, 0);
                } else if ("EXIT".equalsIgnoreCase(action)) {
                    request = new RequestDTO(action, "", "", 0);
                    out.writeObject(request);
                    out.flush();
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid command. Use UPDATE <source> <destination> <travelTime> or PATH <source> <destination>");
                    continue;
                }

                out.writeObject(request);
                out.flush();

                // Read and display the response
                ResponseDTO response = (ResponseDTO) in.readObject();
                System.out.println("Response from server: " + response.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
