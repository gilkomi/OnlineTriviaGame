package com.example.mmn16q1;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{
    private HelloController controller;
    private String ip;
    private Question question;

    public ClientThread(HelloController controller, String ip, Question question){
        this.controller = controller;
        this.ip = ip;
        this.question = question;
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
        Socket socket = new Socket(ip, 3333);

        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        question = (Question) objectInputStream.readObject();
        if(question == null){
            controller.finishGame();
        } else {
            controller.setQuestion(question.getObject());
        }

        inputStream.close();
        objectInputStream.close();
        socket.close();
    }
}
