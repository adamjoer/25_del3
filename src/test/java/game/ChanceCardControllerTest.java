package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChanceCardControllerTest {

    ChanceCardController controller = new ChanceCardController();

    @Test
    void drawChanceCard() {
        ChanceCard cCard = controller.drawChanceCard();
        assertEquals("TargetedCard", cCard.getClass().getSimpleName());

        boolean works = true;
        try {
            for(int i = 0; i < 50; i++){
                controller.drawChanceCard();
            }
        } catch (Exception e) {
            works = false;
        }

        assertTrue(works);

    }


    @Test
    void newGame() {
        controller.newGame();


        boolean works = true;
        try {
            for(int i = 0; i < 50; i++){
                controller.drawChanceCard();
            }
        } catch (Exception e) {
            works = false;
        }

        assertTrue(works);
    }
}