package client;

public class ClientApp {
    public static void main(String[] args) {
        ClientRequestHandler clientRequestHandler = new ClientRequestHandler("localhost", 12345);
        clientRequestHandler.start();
    }
}
