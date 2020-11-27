package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChanceCardControllerTest {

    ChanceCardController controller = new ChanceCardController();

    @Test
    void drawChanceCard() {
        ChanceCard cCard = controller.drawChanceCard();
        assertEquals("TargetedCard", cCard.getClass().getSimpleName());

        for
    }


    @Test
    void newGame() {
        controller.newGame();

    }
}