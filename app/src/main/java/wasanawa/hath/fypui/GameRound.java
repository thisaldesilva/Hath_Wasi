package wasanawa.hath.fypui;

import android.util.Log;


import java.util.ArrayList;
import java.util.Collections;

public class GameRound {

    private Card compPlayer1Card;
    private Card compPlayer2Card;
    private Card playerCard;
    private Player winner;

    public GameRound(Player human,Card humanSelectedCard, Card cpu1, Card cpu2, String trumps){




    }

    public GameRound( Player cpuPlayer1,Card cpu1, Player cpuPlayer2, Card cpu2, Player human, Card humanSelectedCard, String playType,String trumps) throws GameExceptions {

        this.compPlayer1Card = cpu1;
        this.compPlayer2Card = cpu2;
        this.playerCard = humanSelectedCard;

        Log.println( Log.ERROR, "TAG", "inside the constructor game round" );

        //String playType = cpu1.getCategory();
        //validate the human players card
        if(humanSelectedCard.getCategory() != playType) {

            Log.println( Log.ERROR, "TAG", "Human selected card is not the play type." );
            //check if the human has any cards of the play type
            //if so thrown an error
            if(human.CheckForCardType(playType)){
                Log.println( Log.ERROR, "TAG", "throwing" );
                 throw new GameExceptions("Card type" + playType + "Exist");

            }

        }

        int cardNo1 = 0, cardNo2 = 0, cardNo3 = 0;

        if (cpu1.getCategory() == trumps || cpu2.getCategory() == trumps || humanSelectedCard.getCategory() == trumps){

            if (cpu1.getCategory() == trumps){
                cardNo1 = cpu1.getNumber();
            }

            if (cpu2.getCategory() == trumps){
                cardNo2 = cpu2.getNumber();
            }

            if (humanSelectedCard.getCategory() == trumps){
                cardNo3 = humanSelectedCard.getNumber();
            }

            chooseWinner(cardNo1, cardNo2, cardNo3, cpuPlayer1, cpuPlayer2, human);

        }

        else{
            if (cpu1.getCategory() == playType){
                cardNo1 = cpu1.getNumber();
            }

            if (cpu2.getCategory() == playType){
                cardNo2 = cpu2.getNumber();
            }

            if (humanSelectedCard.getCategory() == playType){
                cardNo3 = humanSelectedCard.getNumber();
            }

            chooseWinner(cardNo1, cardNo2, cardNo3, cpuPlayer1, cpuPlayer2, human);

        }


//        if((humanSelectedCard.getCardId() < cpu1.getCardId()) && (humanSelectedCard.getCardId() < cpu2.getCardId())){
//            this.winner = human;
//        }
//        else {
//            if(cpu1.getCardId() < cpu2.getCardId()){
//                this.winner = cpuPlayer1;
//            }
//            else {
//                this.winner = cpuPlayer2;
//            }
//        }

    }

    public void chooseWinner(int card1, int card2, int card3, Player cpuPlayer1, Player cpuPlayer2, Player human){

        if (card1 > card2 && card1 > card3)
        {
            this.winner = cpuPlayer1;
        }

        else if (card2 > card1 && card2 > card3){
            this.winner = cpuPlayer2;
        }

        else if (card3 > card1 && card3 > card2){

            this.winner = human;
        }
    }

    public void playRound(Card card1,Card card2, Card card3 ){

    }


    private void SetWinner(Card card1, Card card2,  Card card3){

        ArrayList<Integer> cardValues = new ArrayList<Integer>();
        cardValues.add(card1.getCardId());
        cardValues.add(card2.getCardId());
        cardValues.add(card3.getCardId());
        int maxValue = (Integer) Collections.max(cardValues);
        int maxCard = cardValues.indexOf(maxValue);

        if(maxCard == 0){
            //this.winner = "CPU1"
        }
        else if(maxCard == 1){

        }
        else{

        }


    }






    public Card getCompPlayer1Card() {
        return compPlayer1Card;
    }

    public void setCompPlayer1Card(Card compPlayer1Card) {
        this.compPlayer1Card = compPlayer1Card;
    }

    public Card getCompPlayer2Card() {
        return compPlayer2Card;
    }

    public void setCompPlayer2Card(Card compPlayer2Card) {
        this.compPlayer2Card = compPlayer2Card;
    }

    public Card getPlayerCard() {
        return playerCard;
    }

    public void setPlayerCard(Card playerCard) {
        this.playerCard = playerCard;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }









}


