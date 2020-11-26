package game;

import java.awt.*;

public class TargetedCard extends ChanceCard{

    /**
     * Targeted card like "player 2 moves to x"
     */

    private final int TARGETED_PLAYER;
    private final Color COLOR;

    public TargetedCard(String CARD_TEXT, int Targeted_player, Color color){
        super(CARD_TEXT);
        TARGETED_PLAYER = Targeted_player;
        COLOR = color;
    }

    public int getTargetedPlayer(){
        return TARGETED_PLAYER;
    }

    public Color getColor(){
        return COLOR;
    }
}