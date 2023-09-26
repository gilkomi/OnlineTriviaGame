# OnlineTriviaGame

## Description
This is a client-server project for a trivia game using TCP communication. The game allows users to connect to the server, receive questions on various topics and answer them within a limited time.

The server is multi-threaded to handle multiple clients simultaneously. It reads questions and answers from a text file, randomly selects a question and sends it to the client along with 4 possible answers using TCP sockets.

The client displays the question and answers in a JavaFX graphical interface and gives the user a limited time to choose an answer using a timer thread.

For each correct answer the user gets 10 points. The game ends after 20 questions.

## Main Features
* Java server listening on a fixed port that can handle many clients simultaneously using threads
* TCP communication between client and server
* Reading questions and answers from a text file
* Sending random questions to clients over TCP, without repeating the same question twice to the same client
* JavaFX client with graphical interface for displaying questions and selecting answers
* Timer thread for limited time per question
* Score calculation and display

## Format of the Questions File
The trivia questions that the game server sends to clients are stored in a plaintext file named `questions.txt`. Each trivia question in the file consists of exactly 5 consecutive lines:

1. The first line contains the text of the trivia question.
2. The second line contains the correct answer to the question.
3. The third, fourth and fifth lines contain incorrect answers to the question, also known as distractors.

There are no empty lines separating between questions in the file. Each question begins immediately after the previous one ends.

This format allows the game server to efficiently parse the question file and extract the question text, correct answer and distractors for each question. The strict structure with no unnecessary whitespace makes the file easy to process programmatically.

#### example 
>What is the capital of France? <br>
Paris <br>
Marseille <br>
Lyon <br>
Toulouse <br>
What is the largest planet in the solar system? <br>
Jupiter <br>
Saturn <br>
Mars <br>
Venus <br>

## Client Pictures 
#### Welcome Screen
![e68ff3b3-de62-425b-a881-e978435d2c25](https://github.com/gilkomi/OnlineTriviaGame/assets/144124159/47bc7a9d-61aa-4656-b8f2-909a6c0bebb2)

#### Question Screen
![ae94e9d8-4f76-469a-b6af-f79cbcd3c70e](https://github.com/gilkomi/OnlineTriviaGame/assets/144124159/42efa219-0823-4499-9fe4-699b095c66dc)
![6f9dd626-113d-4df9-800b-3511c054ccb2](https://github.com/gilkomi/OnlineTriviaGame/assets/144124159/3dc904de-434f-44fa-af4d-a638ab19e63b)

#### Game Finish Screen
![7d5c7724-f146-4814-a0d5-980b650f6ec0](https://github.com/gilkomi/OnlineTriviaGame/assets/144124159/261bcab5-d8a6-4e29-8da4-040d289a3ec5)


