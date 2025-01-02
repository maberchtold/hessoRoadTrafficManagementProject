package client;

import server.ResponseDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUI {
    private final ClientRequestHandler clientRequestHandler;

    public ClientUI(String serverAddress, int serverPort) {
        this.clientRequestHandler = new ClientRequestHandler(serverAddress, serverPort);
        createAndShowUI();
    }

    private void createAndShowUI() {
        JFrame frame = new JFrame("Client UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        // Input fields
        JTextField sourceField = new JTextField();
        JTextField destinationField = new JTextField();
        JTextField travelTimeField = new JTextField();

        // Labels
        panel.add(new JLabel("Source:"));
        panel.add(sourceField);
        panel.add(new JLabel("Destination:"));
        panel.add(destinationField);
        panel.add(new JLabel("Travel Time:"));
        panel.add(travelTimeField);

        // Response area
        JTextArea responseArea = new JTextArea();
        responseArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(responseArea);
        scrollPane.setPreferredSize(new Dimension(300, 100));

        // Buttons
        JButton updateButton = new JButton("Update");
        JButton pathButton = new JButton("Path");
        JButton exitButton = new JButton("Exit");

        // Add buttons to panel
        panel.add(updateButton);
        panel.add(pathButton);
        panel.add(exitButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Button event handling
        updateButton.addActionListener(e -> {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            int travelTime;
            try {
                travelTime = Integer.parseInt(travelTimeField.getText());
                ResponseDTO response = clientRequestHandler.sendRequest("UPDATE", source, destination, travelTime);
                responseArea.append("Server: " + response.getMessage() + "\n");
            } catch (NumberFormatException ex) {
                responseArea.append("Invalid travel time.\n");
            }
        });

        pathButton.addActionListener(e -> {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            ResponseDTO response = clientRequestHandler.sendRequest("PATH", source, destination, 0);
            responseArea.append("Server: " + response.getMessage() + "\n");
        });

        exitButton.addActionListener(e -> {
            clientRequestHandler.sendRequest("EXIT", "", "", 0);
            responseArea.append("Exiting...\n");
            frame.dispose();
        });

        // Display the frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientUI("localhost", 12345));
    }
}
