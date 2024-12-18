package server;


public class ServerApp {
    public static void main(String[] args) {
        ConcurrencyManager concurrencyManager = new ConcurrencyManager(12345);
        concurrencyManager.start();
        ServerUI serverUI = new ServerUI(concurrencyManager.getGraph());
        serverUI.showUI();


    }
}
