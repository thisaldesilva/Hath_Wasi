package com.example.fypui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class ComputerPlayerAgresive extends AbComputerPlayer {

    public ComputerPlayerAgresive(String name, DeckOfCards cardDeck ){
        super(name, cardDeck);
    }

    @Override
    public Card SelectRandomCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);
        Random rand = new Random();
        if(categoryOfCards.size() != 0) {
            return categoryOfCards.get(rand.nextInt(categoryOfCards.size()));
        }
        else {
            return this.getCardDeck().get(rand.nextInt(numberOfCardsRemaining));
        }
    }

    @Override
    public Card SelectTheHigHighestCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);

        Random rand = new Random();
        if(categoryOfCards.size() != 0) {
            return Collections.min(categoryOfCards);
        }
        else {
            return this.getCardDeck().get(rand.nextInt(numberOfCardsRemaining));
        }


    }

    @Override
    public Card selectSmallestCardFromCategory(String category){
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);

        Random rand = new Random();
        if(categoryOfCards.size() != 0) {
            return Collections.max(categoryOfCards);

        }
        else {
            return this.getCardDeck().get(rand.nextInt(numberOfCardsRemaining));
        }

    }

    @Override
    public Card selectHighestCard(){

        //to be implemented
        ///////////////////////////
        Random rand = new Random();
        return CardDeck.get(rand.nextInt(CardDeck.size()));

    }
}
