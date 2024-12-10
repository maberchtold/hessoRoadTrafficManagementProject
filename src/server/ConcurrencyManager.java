package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyManager {
    private final int port;
    private final ExecutorService clientPool;

    public ConcurrencyManager(int port) {
        this.port = port;
        this.clientPool = Executors.newFixedThreadPool(10);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientPool.execute(new RequestProcessor(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
