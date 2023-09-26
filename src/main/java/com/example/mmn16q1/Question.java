package com.example.mmn16q1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Model for a trivia question.
 */
public class Question implements Serializable {
    private String question;
    private String correctAnswer;
    private String[] inCorrectAnswer;

    /**
     * Create question with answers.
     *
     * @param question Text of question
     * @param correctAnswer Correct answer
     * @param inCorrectAnswer1 First wrong answer
     * @param inCorrectAnswer2 Second wrong answer
     * @param inCorrectAnswer3 Third wrong answer
     */
    public Question(String question, String correctAnswer, String inCorrectAnswer1, String inCorrectAnswer2, String inCorrectAnswer3){
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.inCorrectAnswer = new String[]{inCorrectAnswer1, inCorrectAnswer2, inCorrectAnswer3};
    }

    /**
     * Get deep copy of this question.
     *
     * @return Duplicate Question instance
     */
    public Question getObject(){
        return new Question(question, correctAnswer, inCorrectAnswer[0], inCorrectAnswer[1], inCorrectAnswer[2]);
    }

    /**
     * Get the question text.
     *
     * @return Question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Check if given answer is correct.
     *
     * @param answer Answer text to check
     * @return True if matches correct answer
     */
    public boolean isItCorrectAnswer(String answer) {
        return correctAnswer.equals(answer);
    }

    /**
     * Get array of answer options in random order.
     *
     * @return Array with correct answer and incorrect options shuffled
     */
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
