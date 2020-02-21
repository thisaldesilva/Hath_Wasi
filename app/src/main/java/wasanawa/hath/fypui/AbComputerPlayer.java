package wasanawa.hath.fypui;

import java.util.ArrayList;

public abstract class AbComputerPlayer extends Player implements SelectingCard {

    public AbComputerPlayer(String name, DeckOfCards cardDeck ){
        super(name, cardDeck);
    }

    //This method returns an array of cards from the players list of cards
    //relating to a particular category
    //useful to get all the cards that can be played depending upon the card that is being played on that specific round
    public ArrayList<Card> getCategoryOfCards(String category){

        ArrayList<Card> categoryOfCards =  new ArrayList<Card>();

        for(int i =0; i < this.numberOfCardsRemaining; i++){
            if(this.CardDeck.get(i).getCategory() == category){
                categoryOfCards.add(this.CardDeck.get(i));
            }
        }
        return categoryOfCards;
    }


}
