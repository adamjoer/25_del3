package game;

public class StandardCard extends ChanceCard {

    private final boolean FINE;
    private final boolean GIFT;
    private final boolean MOVE;
    private int destination;
    private final int AMOUNT;


    public StandardCard(String CARD_TEXT, boolean fine, boolean gift, boolean moveDestination, int destination, int amount){
        super(CARD_TEXT);
        FINE = fine;
        GIFT = gift;
        MOVE = moveDestination;
        this.destination = destination;
        AMOUNT = amount;
    }

    public String getCardAction(){
        if(FINE){
            return "fine";
        }
        else if(GIFT){
            return "gift";
        }
        else{
            return "move";
        }
    }

    public void setDestination(int num){
        destination = num;
    }

    public int getDestination() {
        return destination;
    }



    public int getAmount(){
        return AMOUNT;
    }

}
