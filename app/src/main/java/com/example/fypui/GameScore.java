package com.example.fypui;

public class GameScore {

    int human;
    int computerPlayer1;
    int getComputerPlayer2;

    public GameScore(int human, int cpu1, int cpu2){
        this.human = human;
        this.computerPlayer1 = cpu1;
        this.getComputerPlayer2 = cpu2;
    }




    public int getHuman() {
        return human;
    }

    public void setHuman(int human) {
        this.human = human;
    }

    public int getComputerPlayer1() {
        return computerPlayer1;
    }

    public void setComputerPlayer1(int computerPlayer1) {
        this.computerPlayer1 = computerPlayer1;
    }

    public int getComputerPlayer2() {
        return getComputerPlayer2;
    }

    public void setComputerPlayer2(int getComputerPlayer2) {
        this.getComputerPlayer2 = getComputerPlayer2;
    }

}
