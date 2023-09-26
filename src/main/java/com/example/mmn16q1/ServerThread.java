package com.example.mmn16q1;

import java.io.*;
import java.net.Socket;

/**
 * Handles communication with a single client.
 */
public class ServerThread extends Thread {
    private Socket socket = null;
    private Question question;

    /**
     * Create thread for client socket.
     *
     * @param socket Client socket
     * @param question ext question
     */
    public ServerThread(Socket socket, Question question){
        this.question = question;
        this.socket = socket;
    }

    /**
     * Handle sending question to client.
     */
    @Override
    public void run() {
        super.run();

        try {
            handleReadAndWrite();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send question object over socket.
     */
    public void handleReadAndWrite() throws IOException, ClassNotFoundException {
        // Get output stream
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        // Write question
        objectOutputStream.writeObject(question);

        // Close streams and socket
        outputStream.close();
        objectOutputStream.close();
        socket.close();
    }
}
