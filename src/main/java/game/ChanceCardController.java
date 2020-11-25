package game;

import java.awt.*;
import java.util.Random;

public class ChanceCardController {
    private static ChanceCard[] drawPile;
    private static int cardsDrawn = 0;

    public ChanceCardController(){
        createPile();
    }



    public ChanceCard drawChanceCard() {
        return drawPile[cardsDrawn++];
    }

    public static void shufflePile(){
        Random rand = new Random();

        for (int i = 0; i < drawPile.length; i++) {
            int randomIndexToSwap = rand.nextInt(drawPile.length);
            ChanceCard temp = drawPile[randomIndexToSwap];
            drawPile[randomIndexToSwap] = drawPile[i];
            drawPile[i] = temp;
        }

    }

    public void createPile(){

    }


}
