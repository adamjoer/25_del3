package game;

import java.awt.*;

public class TargetedCard extends ChanceCard{

    /**
     * Targeted card like "player 2 moves to x"
     */

    private final int TARGETED_PLAYER;

    public TargetedCard(String CARD_TEXT, int Targeted_player){
        super(CARD_TEXT);
        TARGETED_PLAYER = Targeted_player;
    }

    public int getTargetedPlayer(){
        return TARGETED_PLAYER;
    }
}