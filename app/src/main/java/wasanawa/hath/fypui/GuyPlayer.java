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

        Card cardCategory[] = new Card[12];
        Card trumpCategory[] = new Card[12];

        // If there is one card played in the round and the player is a single player.
        if(this.getName() == game.getSinglePlayer().getName())
        {
            int index = 0;
            int smallestCard = 0;

            for( int i=0; i < getNoOfCards(); i++){

                // If the category of the played card is equal to the category of my card (check in a loop).
                if(somecard.getCategory() == getCardFromIndex(i).getCategory()){

                    categoryFound = true;
                    cardCategory[index] = getCardFromIndex(i);
                    index++;

                    // If the number of the card played is smaller than my card, return the highest card of my card set.
                    if (somecard.getNumber() < getCardFromIndex(i).getNumber()){
                        return cardCategory[0];
                    }

                }
            }

            // Return the smallest card of the set if the category is found in my card set and the played card is higher than my card.
            if (categoryFound) {

                return cardCategory[index - 1];
            }

            // If no cards of similar category, check if there are cards from the trump category.
            else{

                index = 0;

                for (int i = 0; i < getNoOfCards(); i++){
                    if(somecard.getCategory() == game.getTrumps()){
                        trumpFound = true;
                        trumpCategory[index] = getCardFromIndex(i);
                        index++;
                    }
                }

                // If trump category found, get the smallest card from that category.
                if(trumpFound){
                    return trumpCategory[index - 1];
                }

                // Else, check other categories and get the smallest card and play.
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

        //return null;

        // Else if player is a team player..
        else {
            Player lastPlayer = game.getLastWinner();
            int index = 0;
            int smallestCard = 0;

            if (this.getName() == game.getTeamPlayer1().getName() || this.getName() == game.getTeamPlayer2().getName()){

                for (int i=0; i < getNoOfCards(); i++){

                    if (somecard.getCategory() == getCardFromIndex(i).getCategory()){
                        categoryFound = true;
                        cardCategory[index] = getCardFromIndex(i);
                        index++;

                        // If played card is smaller than my card, return the highest card.
                        if (somecard.getNumber() < getCardFromIndex(i).getNumber()){
                            return cardCategory[0];
                        }
                    }
                }

                if (categoryFound){
                    return cardCategory[index - 1];
                }

                else{
                    index = 0;

                    for (int i = 0; i < getNoOfCards(); i++){
                        if(somecard.getCategory() == game.getTrumps()){
                            trumpFound = true;
                            trumpCategory[index] = getCardFromIndex(i);
                            index++;
                        }
                    }

                    // If trump category found, get the smallest card from that category.
                    if(trumpFound){
                        return trumpCategory[index - 1];
                    }

                    // Else, check other categories and get the smallest card and play.
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





    }


















}
