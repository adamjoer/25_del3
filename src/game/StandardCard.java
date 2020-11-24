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


}
