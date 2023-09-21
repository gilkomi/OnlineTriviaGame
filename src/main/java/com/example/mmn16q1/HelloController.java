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
        scoreNum = 0;
        isStarted = false;
        runOutOfTime = false;

        startScreen();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timerSeconds--;
            timer.setText(timerSeconds + "");
        }));
        timeline.setOnFinished(event -> {
            runOutOfTime = true;
            btn.fire();
        });
    }

    @FXML
    void btnPressed(ActionEvent event) {
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
        new ClientThread(this, "127.0.0.1", question).start();
        setTimer();
    }

    public void setQuestion(Question question){
        this.question = question;
        String[] answers = this.question.getAllAnswersShuffled();

        Platform.runLater(() -> {
            qText.setText(question.getQuestion());
            qText.setFont(Font.font(12));
            answer1.setText(answers[0]);
            answer2.setText(answers[1]);
            answer3.setText(answers[2]);
            answer4.setText(answers[3]);
        });
    }

    public void finishGame(){
        answer1.setVisible(false);
        answer2.setVisible(false);
        answer3.setVisible(false);
        answer4.setVisible(false);

        Platform.runLater(() -> {
            btn.setText("Restart");
            isStarted = false;
        });

        qText.setText("Congratulation!! \nYou Finish The Game With " + scoreNum + " Points");

        timeline.stop();
    }
    public void startScreen(){
        answer1.setVisible(false);
        answer2.setVisible(false);
        answer3.setVisible(false);
        answer4.setVisible(false);

        qText.setText("Wellcom To My Trivia Game\n" +
                "You Will Need To Answer 20 Questions\n" +
                "For Every Question You Get " + TIME_FOR_LAP + " Seconds To Answer\n" +
                "If The Time Runs Out The Answer Is The Last One You Chose\n" +
                "If You Didn't Chose At All It Will Consider Like Wrong Answer\n" +
                "Good Luck :)");
        qText.setFont(Font.font(14));
    }

    public void setTimer(){
        timerSeconds = TIME_FOR_LAP;
        timeline.stop();
        timer.setText(timerSeconds + "");

        timeline.setCycleCount(TIME_FOR_LAP);
        timeline.play();
    }

    public void updateScore(int additionalScore){
        scoreNum += additionalScore;
        score.setText(scoreNum + "");
    }

}
