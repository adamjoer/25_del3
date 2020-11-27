package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActorControllerTest {

    @Test
    void testMakeTransaction() {
        String[] playerNames = new String[]{"Bruh", "Jens"};
        ActorController act = new ActorController(playerNames);
        Actor[] actArr = act.getActors();
        int amountToSend = 5;
        //Index 0 of this array is saved for the bank, this is a test between players
        Actor actor1 = actArr[1];
        Actor actor2 = actArr[2];

        int balance1Before = actor1.getBalance();
        int balance2Before = actor2.getBalance();

        act.makeTransaction(1, 2, amountToSend);

        assertEquals(balance1Before - amountToSend, actor1.getBalance());
        assertEquals(balance2Before + amountToSend, actor2.getBalance());

    }

    @Test
    void setPosition(){
        String[] playerNames = new String[]{"Bruh", "Jens"};
        ActorController act = new ActorController(playerNames);
        int spacesToMove = 4;

        act.setCurrentPosition(2-1, spacesToMove);

        assertEquals(spacesToMove, act.getCurrentPosition(2-1));

    }
}
