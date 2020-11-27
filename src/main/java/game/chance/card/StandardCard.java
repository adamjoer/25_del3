package game.chance.card;

public class StandardCard extends ChanceCard {

    private final String CARD_TYPE;
    private final int DESTINATION;
    private final int AMOUNT;


    public StandardCard(String CARD_TEXT, String cardType, int destination, int amount){
        super(CARD_TEXT);
        CARD_TYPE = cardType;
        DESTINATION = destination;
        AMOUNT = amount;
    }

    // Getters

    public String getCardAction(){ return CARD_TYPE; }
    public int getDestination() { return DESTINATION; }
    public int getAmount(){ return AMOUNT; }
}
