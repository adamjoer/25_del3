package game;

import java.awt.*;

public class MoveToColorCard extends ChanceCard{

    private final Color COLOR;

    public MoveToColorCard(String CARD_TEXT, Color color){
        super(CARD_TEXT);
        COLOR = color;

    }


    public Color getColor(){
        return COLOR;
    }
}