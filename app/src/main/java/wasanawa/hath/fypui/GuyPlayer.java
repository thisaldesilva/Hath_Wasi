package wasanawa.hath.fypui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GuyPlayer extends AbComputerPlayer {

    public GuyPlayer(String name, DeckOfCards cardDeck ){
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

    Game game = Game.getInstance();



    public Card SelectCard(Card somecard){

        Boolean categoryFound = false;
        Boolean trumpFound = false;

        if(this.getName() == game.getSinglePlayer().getName())
        {
            int index = 0;
            Card categories[] = new Card[12];
            Card trumpCategory[] = new Card[12];
            int smallestCard = 0;

            for( int i=0; i < getNoOfCards(); i++){

                if(somecard.getCategory() == getCardFromIndex(i).getCategory()){

                    categoryFound = true;
                    categories[index] = getCardFromIndex(i);
                    index++;

                    if (somecard.getNumber() < categories[i].getNumber()){
                        return categories[0];
                    }

                }
            }

            if (categoryFound) {

                return categories[index - 1];
            }

            else{

                index = 0;

                for (int i = 0; i < getNoOfCards(); i++){
                    if(somecard.getCategory() == game.getTrumps()){
                        trumpFound = false;
                        trumpCategory[index] = getCardFromIndex(i);
                        index++;
                    }
                }

                if(trumpFound){
                    return trumpCategory[index - 1];
                }

                else{

                    for (int i = 0; i < getNoOfCards(); i++){

                        if(smallestCard > getCardFromIndex(i).getNumber()){
                            smallestCard = getCardFromIndex(i).getNumber();
                            index = i;
                        }
                    }

                    return getCardFromIndex(index);

                }
            }

        }

        return null;

//        else {
//            Player lastPlayer = game.getLastWinner();
//
//
//
//
//
//        }
//
//
//
//
//
    }

















}
