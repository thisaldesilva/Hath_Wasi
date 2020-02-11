package com.example.fypui;

import java.lang.reflect.Array;

public class ScoreBoard {
    private static ScoreBoard ourInstance;
    private static int numberOfScores;
    private GameScore scores[];

    public int getNumberOfScores() {
        return numberOfScores;
    }

    public void setNumberOfScores(int numberOfScores) {
        this.numberOfScores = numberOfScores;
    }

    private ScoreBoard() {
        this.scores = new GameScore[10];
        this.numberOfScores = 0;
    }

    public static ScoreBoard getInstance() {

        if(ourInstance == null){
            ourInstance = new ScoreBoard();
        }
        return ourInstance;
    }

    public static boolean gameFinish(){
        if(numberOfScores >= 10){
            return true;
        }
        else {
            return false;
        }
    }

    public GameScore[] getScores() {
        return scores;
    }

    public void setScore(GameScore score) {
        if(this.numberOfScores < 10) {
            this.scores[this.numberOfScores++] = score;
        }
    }



}
