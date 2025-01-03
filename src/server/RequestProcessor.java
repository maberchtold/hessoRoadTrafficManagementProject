package server;

import client.RequestDTO;
import graph.Graph;
import server.commands.Command;
import server.commands.CommandFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestProcessor implements Runnable {
    private final Socket clientSocket;
    private final Graph graph;

    public RequestProcessor(Socket clientSocket, Graph graph) {
        this.clientSocket = clientSocket;
        this.graph = graph;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            CommandFactory commandFactory = new CommandFactory(graph);
            boolean running = true;

            while (running) {
                RequestDTO request = (RequestDTO) in.readObject();
                Command command = commandFactory.createCommand(
                        request.getCommand(),
                        request.getSource(),
                        request.getDestination(),
                        request.getTravelTime()
                );

                ResponseDTO response = command.execute();
                out.writeObject(response);
                out.flush();

                if (request.getCommand().equalsIgnoreCase("EXIT")) {
                    running = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
