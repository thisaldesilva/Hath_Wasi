package com.example.fypui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.io.FileDescriptor.in;

public class HimashaPlayer extends AbComputerPlayer {

    public HimashaPlayer(String name, DeckOfCards cardDeck) {
        super(name, cardDeck);
    }

    @Override
    public Card SelectRandomCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);
        Random rand = new Random();
        if (categoryOfCards.size() != 0) {
            return categoryOfCards.get(rand.nextInt(categoryOfCards.size()));
        } else return null;
    }

    @Override
    public Card SelectTheHigHighestCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);

        if (categoryOfCards.size() != 0) {
            return Collections.min(categoryOfCards);
        } else return null;

    }

    @Override
    public Card selectSmallestCardFromCategory(String category) {
        ArrayList<Card> categoryOfCards = getCategoryOfCards(category);

        if (categoryOfCards.size() != 0) {
            return Collections.max(categoryOfCards);

        } else return null;

    }

    @Override
    public Card selectHighestCard() {
        return Collections.max(CardDeck);
    }

    //overriding method
    public Card selectSmallestCard() {
        return Collections.min(CardDeck);
    }

    public Card selectBestCard(String Category, String trumpCategory, Card myTeamCard, Card opponentCard) {

        final Integer myTeam = myTeamCard.getCardId();
        final Integer opponent = opponentCard.getCardId();
        final String myTeamCat = myTeamCard.getCategory();
        final String opponentCat = opponentCard.getCategory();
        final boolean categoryMatch = myTeamCat.equals(opponentCat);
        final Integer difference = myTeam.compareTo(opponent);


        if (categoryMatch == true) {
            if (difference < 0) {
                //my team player has a bigger card than opponent
                if (CheckForCardType(Category) == true) { return selectSmallestCardFromCategory(Category); }

                else { return selectSmallestCard(); }

            } else if (difference > 0) {

                //my player has a smaller card than opponent
                if (CheckForCardType(Category) == true) {
                    final Card myCard = SelectTheHigHighestCardFromCategory(Category);
                    //if my highest card is lower than the opponent's card
                    if (myCard.getCardId() > opponent) { return selectSmallestCardFromCategory(Category); }

                    else { return myCard; }
                }
                else { return selectSmallestCardFromCategory(trumpCategory); }
            }
        }
        else {
            //category of both players don't match
            if (CheckForCardType(Category) == true) {
                final Card myCard = SelectTheHigHighestCardFromCategory(Category);
                //if my highest card is lower than the opponent's card
                if (myCard.getCardId() > opponent) { return selectSmallestCardFromCategory(Category); }

                else { return myCard; }
            }

            else {
                if (CheckForCardType(trumpCategory) == true) { return selectSmallestCardFromCategory(trumpCategory); }

                //if trump category is not available
                else { return selectSmallestCard(); }
            }
        }
        return null;
    }

    public Card selectBestCard(String Category, String trumpCategory, Card Player1Card, Player player) {

        if (player.getTrump() == true) {
            final Integer opponentCard = Player1Card.getCardId();
            //check if i have the calling category
            if (CheckForCardType(Category) == true) {
                final Card myCard = SelectTheHigHighestCardFromCategory(Category);
                //if my highest card is lower than the opponent's card
                if (myCard.getCardId() > opponentCard) {
                    return selectSmallestCardFromCategory(Category);
                } else {
                    return myCard;
                }

            } else {
                if (Category == trumpCategory) {
                    return selectSmallestCard();
                } else {
                    return selectSmallestCardFromCategory(trumpCategory);
                }

            }
        } else {
            final Integer teamMemberCard = Player1Card.getCardId();
            if (CheckForCardType(Category) == true) {
                final Card myCard = SelectTheHigHighestCardFromCategory(Category);
                //if my highest card is lower than the opponent's card
                if (myCard.getCardId() > teamMemberCard) {
                    return selectSmallestCardFromCategory(Category);
                } else {
                    return myCard;
                }
            } else {
                return selectSmallestCard();
            }

        }

    }

    public Card selectBestCard( String trumpCategory, Player player){

        ArrayList<Card> trumpCards = getCategoryOfCards(trumpCategory);

        if(player.getTrump()==true){
            if(CardDeck.size() > 3) {
                if (trumpCards.size() > 2) {
                    return SelectTheHigHighestCardFromCategory(trumpCategory);
                }
                else {
                    final Card bestCard = selectHighestCard();
                    if (bestCard.getCategory() != trumpCategory) { return bestCard;}

                    else {
                        while (bestCard.getCategory() == trumpCategory) {
                            Card newBestCard = selectHighestCard();

                            if (newBestCard.getCategory() != trumpCategory) {
                                return newBestCard;
                            }

                            else {
                                continue;
                            }
                        }
                    }
                }
            }

            else {return selectHighestCard();}

        }
        else{
            if(CardDeck.size()< 3){return selectHighestCard(); }

            else{
                final Card bestCard = selectHighestCard();
                if (bestCard.getCategory() != trumpCategory) { return bestCard;}

                else {
                    while (bestCard.getCategory() == trumpCategory) {
                        Card newBestCard = selectHighestCard();

                        if (newBestCard.getCategory() != trumpCategory) {
                            return newBestCard;
                        }

                        else {
                            continue;
                        }
                    }
                }
            }
        }

        return null;
    }

}
