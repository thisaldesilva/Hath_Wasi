package com.example.fypui;
import java.util.Arrays;


import java.lang.reflect.Array;

public class ScoreBoard {
    private static ScoreBoard ourInstance;
    private static int numberOfScores;
    private static GameScore scores[];

    public static int getNumberOfScores() {
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

        if (ourInstance == null) {
            ourInstance = new ScoreBoard();
        }
        return ourInstance;
    }

    public static boolean gameFinish() {
        if (numberOfScores >= 10) {
            return true;
        } else {
            return false;
        }
    }

    public GameScore[] getScores() {
        return scores;
    }

    public void setScore(GameScore score) {
        if (this.numberOfScores < 10) {
            this.scores[this.numberOfScores++] = score;
        }
    }

    public static int getWinner() {
        int[] totals = new int[]{0,0,0};

        for (int i = 0; i < ScoreBoard.getNumberOfScores(); i++){
            totals[0] += scores[i].getHuman();
            totals[1] += scores[i].getComputerPlayer1();
            totals[2] += scores[i].getComputerPlayer2();
        }

        // Initialize maximum element
        int max = totals[0];
        int index = 0;

        // Traverse array elements from second and
        // compare every element with current max
        for (int i = 1; i < totals.length; i++)
            if (totals[i] > max) {
                max = totals[i];
                index = i;
            }

        return index;

    }


}
