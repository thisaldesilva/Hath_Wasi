package com.example.fypui;

import java.lang.reflect.Array;

public class ScoreBoard {
    private static ScoreBoard ourInstance;

    private GameScore scores[];

    public int getNumberOfScores() {
        return numberOfScores;
    }

    public void setNumberOfScores(int numberOfScores) {
        this.numberOfScores = numberOfScores;
    }

    private int numberOfScores;

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

    public GameScore[] getScores() {
        return scores;
    }

    public void setScore(GameScore score) {
        this.scores[this.numberOfScores++] = score;
    }



}
