package muprogram;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Server extends JFrame {

    JTextArea chatArea;
    JTextField messageField;
    JButton sendButton;

    ServerSocket serverSocket;
    Socket socket;

    BufferedReader in;
    PrintWriter out;

    public Server() {

        setTitle("Chat Server");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        messageField = new JTextField();
        sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());

        messageField.addActionListener(e -> sendMessage());

        setVisible(true);

        startServer();
        JLabel dev =
                new JLabel(
                        "Aryan Mali (24EARAD023)",
                        SwingConstants.CENTER
                );

        add(
                dev,
                BorderLayout.NORTH
        );
    }

    void startServer() {

        new Thread(() -> {

            try {

                serverSocket = new ServerSocket(5000);

                chatArea.append("Server started...\n");
                chatArea.append("Waiting for client...\n");

                socket = serverSocket.accept();

                chatArea.append("Client connected!\n");

                in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

                out = new PrintWriter(
                        socket.getOutputStream(), true);

                String message;

                while ((message = in.readLine()) != null) {

                    chatArea.append("Client: " + message + "\n");
                }

            } catch (Exception e) {

                chatArea.append("Connection closed.\n");
            }

        }).start();
    }

    void sendMessage() {

        String message = messageField.getText();

        if (!message.isEmpty() && out != null) {

            out.println(message);

            chatArea.append("You: " + message + "\n");

            messageField.setText("");
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Server();
        });
    }
}