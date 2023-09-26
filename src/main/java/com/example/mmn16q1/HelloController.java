package com.example.mmn16q1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class HelloController {
    final int TIME_FOR_LAP = 60;
    final int WRONG_ANSWER = -5, GOOD_ANSWER = 10;

    @FXML
    private RadioButton answer1;

    @FXML
    private RadioButton answer2;

    @FXML
    private RadioButton answer3;

    @FXML
    private RadioButton answer4;

    @FXML
    private ToggleGroup answerBtn;

    @FXML
    private Button btn;

    @FXML
    private Text qText;

    @FXML
    private Text score;

    @FXML
    private Text timer;

    private Question question;
    private int scoreNum;
    private int timerSeconds;
    private boolean isStarted, runOutOfTime;
    Timeline timeline;

    public void initialize(){

        // Initialize variables
        scoreNum = 0;
        isStarted = false;
        runOutOfTime = false;

        startScreen();

        // Set up timer
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timerSeconds--;
            timer.setText(timerSeconds + "");
        }));
        timeline.setOnFinished(event -> {
            runOutOfTime = true;
            btn.fire();
        });
    }

    /**
     * Handle button click during game.
     *
     * If game not started, begins game.
     * If started, checks selected answer and updates score.
     * Gets next question and resets timer.
     */
    @FXML
    void btnPressed(ActionEvent event) {
        // Game logic
        if(!isStarted){
            isStarted = true;
            btn.setText("Choose");

            answer1.setVisible(true);
            answer2.setVisible(true);
            answer3.setVisible(true);
            answer4.setVisible(true);

            scoreNum = 0;
            score.setText(scoreNum + "");

        } else {
            //there is no btn that selected
            if(answerBtn.getSelectedToggle() == null){
                //if the time is run out and no btn selected it's like a wrong answer
                if(runOutOfTime){
                    updateScore(WRONG_ANSWER);
                    runOutOfTime = false;
                } else {
                    return;
                }
            } else {
                RadioButton selected = (RadioButton)(answerBtn.getSelectedToggle());
                //update the score
                if(question.isItCorrectAnswer(selected.getText())){
                    updateScore(GOOD_ANSWER);
                }else{
                    updateScore(WRONG_ANSWER);
                }
                answerBtn.getSelectedToggle().setSelected(false);
            }
        }

        // Get and set next question
        new ClientThread(this, "127.0.0.1", question).start();

        // Restart timer
        setTimer();
    }

    /**
     * Set the current question to display.
     *
     * @param question Question model to display
     */
    public void setQuestion(Question question){
        // Set question text
        this.question = question;
        String[] answers = this.question.getAllAnswersShuffled();

        // Shuffle and set answer options
        Platform.runLater(() -> {
            qText.setText(question.getQuestion());
            qText.setFont(Font.font(12));
            answer1.setText(answers[0]);
            answer2.setText(answers[1]);
            answer3.setText(answers[2]);
            answer4.setText(answers[3]);
        });
    }

    /**
     * Stop game and display end screen.
     */
    public void finishGame(){
        // Hide answers
        answer1.setVisible(false);
        answer2.setVisible(false);
        answer3.setVisible(false);
        answer4.setVisible(false);

        // Replace the choose button with reset button
        Platform.runLater(() -> {
            btn.setText("Restart");
            isStarted = false;
        });

        // Set final score text
        qText.setText("Congratulation!! \nYou Finish The Game With " + scoreNum + " Points");

        // Stop timer
        timeline.stop();
    }

    /**
     * Show initial start screen.
     */
    public void startScreen(){
        // Hide answers
        answer1.setVisible(false);
        answer2.setVisible(false);
        answer3.setVisible(false);
        answer4.setVisible(false);

        // Set start text
        qText.setText("Welcome To My Trivia Game\n" +
                "You Will Need To Answer 20 Questions\n" +
                "For Every Question You Get " + TIME_FOR_LAP + " Seconds To Answer\n" +
                "If The Time Runs Out The Answer Is The Last One You Chose\n" +
                "If You Didn't Chose At All It Will Consider Like Wrong Answer\n" +
                "Good Luck :)");
        qText.setFont(Font.font(14));
    }

    /**
     * Reset and start question timer.
     */
    public void setTimer(){
        // Reset time and text
        timerSeconds = TIME_FOR_LAP;
        timeline.stop();
        timer.setText(timerSeconds + "");

        // Start timeline
        timeline.setCycleCount(TIME_FOR_LAP);
        timeline.play();
    }

    /**
     * Update displayed score by specified amount.
     *
     * @param additionalScore Points to add to total score
     */
    public void updateScore(int additionalScore){
        scoreNum += additionalScore;
        score.setText(scoreNum + "");
    }

}
