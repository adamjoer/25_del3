package game;
import game.actor.Actor;
import game.actor.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest{
    /*
     @org.junit.jupiter.api.Test
    void createPlayerCheckName(){
        String playerName = "Bo";
        Player player = new Player(playerName, 500);
        assertEquals(playerName, player.getName());
    }

    @org.junit.jupiter.api.Test
    void createPlayerCheckBalance(){
        int accBalance = 500;
        Player player = new Player("Bo", accBalance);
        assertEquals(accBalance, player.getBalance());
    }

     */

    @Test
    void testMakeTransaction() {
        Actor[] actors;
        actors = new Actor[2];
        boolean transactionSucces;
        for (int i = 0; i < 2; i++)
        {
            String name = "bo"+i;
            actors[i] = new Player(16, name);
        }

        transactionSucces = actors[0].makeTransaction(16);
        assertTrue(transactionSucces);

        ((Player)actors[0]).setBalance(16);
        transactionSucces = actors[0].makeTransaction(17);
        assertTrue(transactionSucces);

        ((Player)actors[0]).setBalance(16);
        transactionSucces = actors[0].makeTransaction(-17);
        assertFalse(transactionSucces);

        ((Player)actors[0]).setBalance(16);
        transactionSucces = actors[0].makeTransaction(Integer.MAX_VALUE);
        assertFalse(transactionSucces);

        ((Player)actors[0]).setBalance(16);
        transactionSucces = actors[0].makeTransaction(Integer.MIN_VALUE);
        assertFalse(transactionSucces);


    }

    @Test
    void movePlayer() {

        String name = "bo";
        Player  player = new Player(100, name);
        Beaker beaker = new Beaker(1,6);



        for (int i = 0; i < 1000; i++) {
            beaker.roll();
            int rolled = beaker.getSum();
            player.movePlayer(rolled);
            assertTrue(player.getCurrentPosition() != player.getPreviousPosition() && player.getCurrentPosition() < 25);
        }



    }
}
