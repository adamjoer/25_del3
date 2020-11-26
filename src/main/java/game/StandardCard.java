package game;

public class StandardCard extends ChanceCard {

    private final String CARD_TYPE;
    private final int DESTINATION;
    private final int INCREMENT;
    private final int AMOUNT;


    public StandardCard(String CARD_TEXT, String cardType, int destination, int increment, int amount){
        super(CARD_TEXT);
        CARD_TYPE = cardType;
        DESTINATION = destination;
        AMOUNT = amount;
        INCREMENT = increment;
    }

    // Getters

    public String getCardAction(){ return CARD_TYPE; }
    public int getDestination() { return DESTINATION; }
    public int getIncrement() {return INCREMENT; }
    public int getAmount(){ return AMOUNT; }
}
