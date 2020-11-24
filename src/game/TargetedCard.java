package game;

public class TargetedCard extends ChanceCard{

    private final int TARGETED_PLAYER;

    public TargetedCard(String CARD_TEXT, int Targeted_player){
        super(CARD_TEXT);
        this.TARGETED_PLAYER = Targeted_player;
    }
}
