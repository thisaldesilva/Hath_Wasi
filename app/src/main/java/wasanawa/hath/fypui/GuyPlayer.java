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

                    index = 0;
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

            //Player lastPlayer = game.getLastWinner();
            int index = 0;
            int smallestCard = 0;

                for (int i=0; i < getNoOfCards(); i++){

                    // If the played card category is equal to my card category.
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

                // If card category found and the played card is larger than my card, return the smallest card.
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

                        // If the played card is not an Ace.
                        if (somecard.getNumber() != 14){
                            return trumpCategory[index - 1];
                        }

                    }
                    // Else, check other categories and get the smallest card and play.
                    for (int i = 0; i < getNoOfCards(); i++){

                        if(smallestCard >= getCardFromIndex(i).getNumber()){
                            smallestCard = getCardFromIndex(i).getNumber();
                            index = i;
                        }
                    }

                    return getCardFromIndex(index);

                }
            //}
        }
    }


    public Card SelectCard(Card player1Card, Card player2Card){

        Card highestCard, firstCard = null, highestTrump = null, teamPlayerCard;
        Player myTeamPlayer;
        int index = 0, smallestCard = 0;
        Card cardCategory[] = new Card[12], trumpCategory[] = new Card[12];
        Boolean categoryFound = false, trumpFound = false;

        //Check the category of both cards played.
        if(player1Card.getCategory() == player2Card.getCategory()){

            // Check which player has put the highest card.
            if(player1Card.getNumber() > player2Card.getNumber()){
                highestCard = player1Card;
            }

            else{
                highestCard = player2Card;
            }
        }
        else{

            if(player1Card.getCategory() == game.getTrumps()){
                highestCard = player1Card;
            }
            else if(player2Card.getCategory() == game.getTrumps()){
                highestCard = player2Card;
            }
            else{
                highestCard = null;
            }

            // Check who is the first player to play the round.
            if(game.getLastWinner().getName() == game.getHumanPlayer().getName()){
                firstCard = player1Card;
            }
            else{
                firstCard = player2Card;
            }

        }

        // Check if this player is a single player.
        if(this.getName() == game.getSinglePlayer().getName()){
            index = 0;

            for( int i =0; i < getNoOfCards(); i++){
                if(firstCard.getCategory() == this.getCardFromIndex(i).getCategory()){
                    cardCategory[index] = getCardFromIndex(i);
                    index++;
                    categoryFound = true;
                }
            }

            if(categoryFound){
                return cardCategory[0];
            }

            else{
                // Check if highest card is a trump.
                index = 0;

                if(highestCard.getCategory() == game.getTrumps()){
                    for(int i = 0; i < getNoOfCards(); i++){

                        if(getCardFromIndex(i).getCategory() == game.getTrumps()){
                            trumpCategory[index] = getCardFromIndex(i);
                            index++;
                            trumpFound = true;
                        }
                    }
                    // If a trump is found in my set of cards, check if that card is larger than the trump card played, If true, return
                    // the highest trump card.
                    // Else, check for other cards and return the smallest card in my card deck.
                    if(trumpFound){
                        for(int i = 0; i < trumpCategory.length; i++){
                            if(trumpCategory[i].getNumber() > highestCard.getNumber()){
                                highestTrump = trumpCategory[i];
                            }
                        }

                        if(highestTrump.getNumber() > highestCard.getNumber()){
                            return highestTrump;
                        }
                    }

                    for( int i = 0; i < getNoOfCards(); i++){

                        if(smallestCard > getCardFromIndex(i).getNumber()){
                            smallestCard = getCardFromIndex(i).getNumber();
                            index = i;
                        }
                    }

                    return getCardFromIndex(index);
                }
            }

        }

        // If I am a team player.
        else{

            // Check which player is my team player.
            if(this.getName() != game.getTeamPlayer1().getName()){
                myTeamPlayer = game.getTeamPlayer1();
            }
            else{
                myTeamPlayer = game.getTeamPlayer2();
            }
            // Check if the team player is human player.
            if(myTeamPlayer.getName() == game.getHumanPlayer().getName()){
                teamPlayerCard = player1Card;
            }
            else{
                teamPlayerCard = player2Card;
            }

            //Check if both players cards are similar.
            if(player1Card.getCategory() == player2Card.getCategory()){

                for(int i = 0; i < getNoOfCards(); i++){
                    if(getCardFromIndex(i).getCategory() == highestCard.getCategory()){
                        cardCategory[index] = getCardFromIndex(i);
                        index++;
                        categoryFound = true;
                    }
                }

                // Check if highest card is my team mate's card.
                if(highestCard.getNumber() == teamPlayerCard.getNumber()){

                    if(categoryFound){
                        return cardCategory[index - 1];
                    }

                    else{
                        for(int i = 0; i < getNoOfCards(); i++){
                            if(getCardFromIndex(i).getCategory() != game.getTrumps()){
                                if(smallestCard >= getCardFromIndex(i).getNumber()){
                                    smallestCard = getCardFromIndex(i).getNumber();
                                }
                            }
                        }
                    }
                }

                else{
                    index = 0;
                    // If i have cards of same category.
                    if (cardCategory.length != 0){
                        for(int i = 0; i < cardCategory.length; i++){
                            if(cardCategory[i].getNumber() > highestCard.getNumber()){
                                index = i;
                            }
                        }

                        return cardCategory[index];

                    }

                    else{
                        index = 0;
                        for(int i = 0; i < getNoOfCards(); i++){
                            if(getCardFromIndex(i).getCategory() == game.getTrumps()){
                                trumpCategory[index] = getCardFromIndex(i);
                                index++;
                                trumpFound = true;
                            }
                        }

                        if (trumpFound){
                            return trumpCategory[index - 1];
                        }

                        else{
                            smallestCard = 0;
                            index = 0;
                            for (int i = 0; i < getNoOfCards(); i++) {

                                if (smallestCard >= getCardFromIndex(i).getNumber()) {
                                    smallestCard = getCardFromIndex(i).getNumber();
                                    index = i;
                                }
                            }
                            return getCardFromIndex(index);
                        }
                    }
                }

            }

            else{
                index = 0;
                // Check if highest card is equal to first card.
                if(highestCard == firstCard){

                    // Check if highest card is my team players card
                    if(highestCard == teamPlayerCard){

                        // If i have same category cards.
                        for(int i = 0; i < getNoOfCards(); i++){
                            if(highestCard.getCategory() == getCardFromIndex(i).getCategory()){
                                cardCategory[index] = getCardFromIndex(i);
                                index++;
                                categoryFound = true;
                            }
                        }

                        // If category found, return the lowest card from my category index.
                        if(categoryFound){
                            return cardCategory[index - 1];
                        }

                        else{
                            index = 0; smallestCard = 0;
                            for(int i = 0; i < getNoOfCards(); i++){
                                if (smallestCard >= getCardFromIndex(i).getNumber()) {
                                    smallestCard = getCardFromIndex(i).getNumber();
                                    index = i;
                                }
                            }

                            return getCardFromIndex(index);
                        }
                    }

                    else{
                        index = 0;
                        for(int i = 0; i < getNoOfCards(); i++){
                            if(getCardFromIndex(i).getCategory() == highestCard.getCategory()){
                                cardCategory[index] = getCardFromIndex(i);
                                index++;
                                categoryFound = true;
                            }
                        }

                        if(categoryFound){
                            return cardCategory[0];
                        }

                        else{
                            index = 0;
                            for(int i = 0; i < getNoOfCards(); i++){
                                if(getCardFromIndex(i).getCategory() == game.getTrumps()){
                                    trumpCategory[index] = getCardFromIndex(i);
                                    index++;
                                    trumpFound = true;
                                }
                            }

                            if(trumpFound){
                                return trumpCategory[index - 1];
                            }

                            else{
                                index = 0; smallestCard = 0;
                                for(int i = 0; i < getNoOfCards(); i++){
                                    if (smallestCard >= getCardFromIndex(i).getNumber()) {
                                        smallestCard = getCardFromIndex(i).getNumber();
                                        index = i;
                                    }
                                }

                                return getCardFromIndex(index);
                            }
                        }
                    }
                }

                else{
                    index = 0;
                    for (int i = 0; i < getNoOfCards(); i++){
                        if(getCardFromIndex(i).getCategory() == firstCard.getCategory()){
                            cardCategory[index] = getCardFromIndex(i);
                            index++;
                            categoryFound = true;
                        }
                    }

                    if(highestCard == teamPlayerCard){
                        if(categoryFound){
                            return cardCategory[index - 1];
                        }

                        for (int i =0; i < getNoOfCards(); i++){
                            if (smallestCard >= getCardFromIndex(i).getNumber()) {
                                smallestCard = getCardFromIndex(i).getNumber();
                                index = i;
                            }
                        }

                        return getCardFromIndex(index);
                    }

                    else{
                        if(categoryFound){
                            return cardCategory[index - 1];
                        }

                        else{
                            index = 0;
                            for(int i = 0; i < getNoOfCards(); i++){
                                if(getCardFromIndex(i).getCategory() == game.getTrumps()){
                                    trumpCategory[index] = getCardFromIndex(i);
                                    index++;
                                    trumpFound = true;
                                }
                            }

                            if(trumpFound){

                                for (int i=0; i< getNoOfCards(); i++){
                                    if (smallestCard >= getCardFromIndex(i).getNumber()) {
                                        smallestCard = getCardFromIndex(i).getNumber();
                                        index = i;
                                    }
                                }
                                return trumpCategory[index];
                            }

                            else{
                                for (int i =0; i < getNoOfCards(); i++){
                                    if (smallestCard >= getCardFromIndex(i).getNumber()) {
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
        }
    }

}
