package com.example.mmn16q1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Question implements Serializable {
    private String question;
    private String correctAnswer;
    private String[] inCorrectAnswer;

    public Question(String question, String correctAnswer, String inCorrectAnswer1, String inCorrectAnswer2, String inCorrectAnswer3){
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.inCorrectAnswer = new String[]{inCorrectAnswer1, inCorrectAnswer2, inCorrectAnswer3};
    }

    public Question getObject(){
        return new Question(question, correctAnswer, inCorrectAnswer[0], inCorrectAnswer[1], inCorrectAnswer[2]);
    }
    public String getQuestion() {
        return question;
    }

    public boolean isItCorrectAnswer(String answer) {
        return correctAnswer.equals(answer);
    }

    public String[] getAllAnswersShuffled() {
        Random ran = new Random();
        ArrayList<String> alAnswers = new ArrayList<>();
        String[] answers = new String[4];

        alAnswers.add(correctAnswer);
        alAnswers.add(inCorrectAnswer[0]);
        alAnswers.add(inCorrectAnswer[1]);
        alAnswers.add(inCorrectAnswer[2]);

        for (int i = 0; i < answers.length; i++) {
            int index = ran.nextInt(alAnswers.size());
            answers[i] = alAnswers.remove(index);
        }

        return answers;
    }
}
