package com.example.fypui;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;


public class Player {
    private String name;
    private int score;
    protected int numberOfCardsRemaining;

    private boolean trumpCalled;


    //Card [] CardDeck = new Card[12];
    protected ArrayList <Card> CardDeck = new ArrayList();


    public Player(String name, DeckOfCards cardDeck ) {
        this.name = name;
        this.score = 0;
        this.trumpCalled = false;
        this.numberOfCardsRemaining = 12;
        this.CardDeck = cardDeck.DealingCards();
        Collections.sort(this.CardDeck);

    }

    public Card getCardFromIndex(int index){
        return CardDeck.get(index);
    };

    public int getCardImagePathFromIndex(int index){
        return getCardFromIndex(index).getImageSource();
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Card> getPlayerCards(){
        return CardDeck;
    }

    public int getNoOfCards(){
        return this.CardDeck.size();

    }

    //this method will take a card type as an input and return
    //true if the given card type exist within the players cards
    public boolean CheckForCardType(String checkType){

        for (int i = 0; i < this.numberOfCardsRemaining; i++){
            if(CardDeck.get(i).getCategory() == checkType){
                return true;
            }
        }
        return false;

    }

    public void setTrump(boolean trumpCalled){this.trumpCalled=trumpCalled;}

    public void setNewCards(DeckOfCards cardDeck){
        this.CardDeck = cardDeck.DealingCards();
        Collections.sort(this.CardDeck);
    }


    public int getNumberOfCardsRemaining() {
        return numberOfCardsRemaining;
    }

    public boolean getTrump(){ return trumpCalled;}


    public void setNumberOfCardsRemaining(int numberOfCardsRemaining) {
        this.numberOfCardsRemaining = numberOfCardsRemaining;
    }

    public ArrayList<Card> getCardDeck() {
        return CardDeck;
    }

    public void setCardDeck(ArrayList<Card> cardDeck) {
        CardDeck = cardDeck;
    }


    public void displayDetails(){

        for(int i = 0; i < this.numberOfCardsRemaining; i++ ){
            Log.println( Log.ERROR, "TAG", "Player Name - " + this.name +"Card info - Category - " + this.CardDeck.get(i).getCategory() + " | Number - " +
                    CardDeck.get(i).getNumber());

        }

    }

    public void removeCardFromID(int id){
        for(int i = 0; i < this.numberOfCardsRemaining; i++){
            if(this.CardDeck.get(i).getCardId() == id){
                CardDeck.remove(i);
                break;
            }
        }
    }




}
