package com.example.mmn16q1;

import java.io.*;
import java.net.Socket;

/**
 * Handles client networking to retrieve next question.
 */
public class ClientThread extends Thread{
    private HelloController controller;
    private String ip;
    private Question question;

    /**
     * Create thread with controller and IP.
     *
     * @param controller UI controller instance
     * @param ip Server IP address
     * @param question Current question
     */
    public ClientThread(HelloController controller, String ip, Question question){
        this.controller = controller;
        this.ip = ip;
        this.question = question;
    }

    /**
     * Connect to server and get next question.
     *
     * Notifies controller on new question or end of game.
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
     * Read question from server and pass to controller.
     */
    public void handleReadAndWrite() throws IOException, ClassNotFoundException {
        // Connect to server
        Socket socket = new Socket(ip, 3333);

        // Read question
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        question = (Question) objectInputStream.readObject();

        if(question == null){
            controller.finishGame();
        } else {
            controller.setQuestion(question.getObject());
        }

        // Close streams and socket
        inputStream.close();
        objectInputStream.close();
        socket.close();
    }
}
