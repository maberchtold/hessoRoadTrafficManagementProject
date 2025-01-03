package server;

import graph.Graph;

import java.net.Socket;

public class DefaultRequestProcessorFactory extends RequestProcessorFactory {
    @Override
    public RequestProcessor createRequestProcessor(Socket clientSocket, Graph graph) {
        return new RequestProcessor(clientSocket, graph);
    }
}
