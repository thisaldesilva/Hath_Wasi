package wasanawa.hath.fypui;

public interface SelectingCard {
    public Card selectSmallestCardFromCategory(String category);
    public Card SelectTheHigHighestCardFromCategory(String category);
    public Card SelectRandomCardFromCategory(String category);
    public Card selectHighestCard();
}
