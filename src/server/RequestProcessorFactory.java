package server;

import graph.Graph;

import java.net.Socket;

public abstract class RequestProcessorFactory {
    public abstract RequestProcessor createRequestProcessor(Socket clientSocket, Graph graph);
}
