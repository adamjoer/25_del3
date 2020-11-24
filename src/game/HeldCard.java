package game;

public class HeldCard extends ChanceCard{

    private final int HELD_BY;

    public HeldCard(String CARD_TEXT, int heldBy){
        super(CARD_TEXT);
        HELD_BY = heldBy;
    }

    public String getCardText() {
        return CARD_TEXT;
    }

    public int getHeldBy(){
        return HELD_BY;
    }
}
