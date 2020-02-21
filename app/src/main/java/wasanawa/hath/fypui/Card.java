package wasanawa.hath.fypui;

public class Card implements Comparable<Card> {

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public boolean isTrump() {
        return trump;
    }

    public void setTrump(boolean trump) {
        this.trump = trump;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }

    private int cardId;
    private boolean trump;
    private String category;
    private int imageSource;
    private int number;


    public Card (int cardId, boolean trump, String category, int imageSource, int number){
        this.cardId = cardId;
        this.number =  number;
        this.trump = trump;
        this.category = category;
        this.imageSource = imageSource;
    }



    public int compareTo(Card card){
        if(this.cardId > card.getCardId()){
            return 1;
        }

        else if(this.cardId < card.getCardId()){
            return -1;
        }

        else{
            return 0;
        }
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
