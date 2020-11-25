package game;

public class HeldCard extends ChanceCard{

    /**
     * A card like get out of jail, that a player holds for some time
     */

    private int heldBy;

    public HeldCard(String CARD_TEXT){
        super(CARD_TEXT);
    }

    public void setHELD_BY(int num){
        heldBy = num;
    }

    public int getHeldBy(){
        return heldBy;
    }
}
