package game;

import java.util.Random;

public class ChanceCardController {

    private ChanceCard[] drawPile;

    /**
     * Variable used to keep track of which card is the next in the pile
     */
    private static int cardsDrawn = 0;


    /**
     * constructor
     */
    public ChanceCardController() {
        drawPile = createPile();
    }


    /**
     * Draw a chancecard and increment the counter
     * @return
     */
    public ChanceCard drawChanceCard() {
        if(cardsDrawn < drawPile.length) {
            return drawPile[cardsDrawn++];
        }
        else{
            newGame();
        }

    }

    /**
     * Method used to shuffle the pile, when a new game starts
     */
    private void shufflePile() {
        Random rand = new Random();

        for (int i = 0; i < drawPile.length; i++) {
            int randomIndexToSwap = rand.nextInt(drawPile.length);
            ChanceCard temp = drawPile[randomIndexToSwap];
            drawPile[randomIndexToSwap] = drawPile[i];
            drawPile[i] = temp;
        }
    }

    private ChanceCard[] createPile() {
        return Utility.chanceCardGenerator("src/main/resources/chanceCards.xml");
    }


    /**
     * Shuffle the pile, and set the counter to 0
     */
    public void newGame(){
        shufflePile();
        cardsDrawn = 0;
    }

}
