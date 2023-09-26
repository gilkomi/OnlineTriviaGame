package com.example.mmn16q1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Server that provides trivia questions to clients.
 */
public class Server {
    /**
     * File path of question data.
     */
    final String PATH = "questions.txt";
    final int AMOUNT_OF_QUESTIONS_PER_USER = 20;
    ArrayList<Question> questions;

    //Shuffled question lists for each client.
    HashMap<String , ArrayList<Question>> usersQuestionsLists;

    /**
     * Initialize server and load questions.
     */
    public Server(){
        ServerSocket sc = null;
        Socket s = null;
        makeQuestionsListFromFile();
        usersQuestionsLists = new HashMap<>();

        try {
            sc = new ServerSocket(3333);
            while (true){
                s = sc.accept();
                String  userIP = s.getInetAddress().getHostAddress();

                //if the questions list isn't empty it return to the client the first question
                if(usersQuestionsLists.get(userIP) != null && !usersQuestionsLists.get(userIP).isEmpty()){
                    new ServerThread(s, usersQuestionsLists.get(userIP).remove(0)).start();
                }
                //if the list is empty it replace it with a full list
                else {
                    ArrayList<Question> shuffleQuestions = shuffleQuestions();
                    usersQuestionsLists.put(userIP, shuffleQuestions);
                    new ServerThread(s, null).start();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Shuffle full question set into new list.
     *
     * @return Shuffled list of questions
     */
    public ArrayList<Question> shuffleQuestions(){
        Random ran = new Random();
        ArrayList<Question> tempQuestions = new ArrayList<>(questions);
        ArrayList<Question> shuffleQuestions = new ArrayList<>();

        for (int i = 0; i < AMOUNT_OF_QUESTIONS_PER_USER && !tempQuestions.isEmpty(); i++) {
            int randomIndex = ran.nextInt(tempQuestions.size());
            shuffleQuestions.add(tempQuestions.remove(randomIndex));
        }

        return shuffleQuestions;
    }

    /**
     * Load questions from file into memory.
     */
    public void makeQuestionsListFromFile(){
        questions = new ArrayList<>();

        File file = new File(PATH);
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
            BufferedReader bfReader = new BufferedReader(fileReader);

            while (bfReader.ready()){
                String[] input = new String[5];
                for (int i = 0; i < input.length; i++) {
                    input[i] = bfReader.readLine();
                }

                questions.add(new Question(input[0], input[1], input[2], input[3], input[4]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Main entry point to start server.
     */
    public static void main(String[] args) {
        new Server();
    }
}
