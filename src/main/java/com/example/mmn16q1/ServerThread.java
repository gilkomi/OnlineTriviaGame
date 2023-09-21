package com.example.mmn16q1;

import com.example.mmn16q1.Question;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ServerThread extends Thread {
    private Socket socket = null;
    private Question question;

    public ServerThread(Socket socket, Question questions){
        this.question = questions;
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();

        try {
            handleReadAndWrite();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleReadAndWrite() throws IOException, ClassNotFoundException {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(question);

        outputStream.close();
        objectOutputStream.close();
        socket.close();
    }
}
