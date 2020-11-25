package game;

import java.util.Random;

public class ChanceCardController {

    private ChanceCard[] drawPile;

    private static int cardsDrawn = 0;


    public ChanceCardController(){
        drawPile = createPile();
    }


    public ChanceCard drawChanceCard() {
        return drawPile[cardsDrawn++];
    }

    public void shufflePile(){
        Random rand = new Random();

        for (int i = 0; i < drawPile.length; i++) {
            int randomIndexToSwap = rand.nextInt(drawPile.length);
            ChanceCard temp = drawPile[randomIndexToSwap];
            drawPile[randomIndexToSwap] = drawPile[i];
            drawPile[i] = temp;
        }
    }

    public ChanceCard[] createPile(){
        return Utility.chanceCardGenerator("src/main/resources/chanceCards.xml");
    }
}
