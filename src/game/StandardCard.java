package game;

public class StandardCard extends ChanceCard {

    private final boolean FINE;
    private final boolean GIFT;
    private final boolean MOVE;
    private final int DESTINATION;
    private final int AMOUNT;


    public StandardCard(String CARD_TEXT, boolean fine, boolean gift, boolean move, int destination, int amount){
        super(CARD_TEXT);
        this.FINE = fine;
        this.GIFT = gift;
        this.MOVE = move;
        this.DESTINATION = destination;
        this.AMOUNT = amount;
    }

    public String getCardText(){
        return CARD_TEXT;
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


    public int getDestination(){
        return DESTINATION;
    }

    public int getAmount(){
        return AMOUNT;
    }

}
