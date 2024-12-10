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

    public void start() {
        try (Socket socket = new Socket(serverAddress, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to server. Type your commands:");

            while (true) {
                System.out.print("> ");
                String command = scanner.nextLine();

                // Break command into components
                String[] parts = command.split(" ");
                String action = parts[0].toUpperCase();  // e.g., UPDATE or PATH

                // Prepare the RequestDTO based on the command
                RequestDTO request = null;
                if ("UPDATE".equals(action) && parts.length == 4) {
                    String source = parts[1];
                    String destination = parts[2];
                    int travelTime = Integer.parseInt(parts[3]);
                    request = new RequestDTO(action, source, destination, travelTime);
                } else if ("PATH".equals(action) && parts.length == 3) {
                    String source = parts[1];
                    String destination = parts[2];
                    request = new RequestDTO(action, source, destination, 0);  // Travel time not needed for PATH
                } else if ("EXIT".equalsIgnoreCase(action)) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid command. Use UPDATE <source> <destination> <travelTime> or PATH <source> <destination>");
                    continue;
                }

                // Send RequestDTO to the server
                out.writeObject(request);

                // Receive ResponseDTO from the server
                ResponseDTO response = (ResponseDTO) in.readObject();
                System.out.println("Response from server: " + response.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
