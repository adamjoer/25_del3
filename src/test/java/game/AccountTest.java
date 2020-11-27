package game;

import game.actor.Player;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest
{
    @org.junit.jupiter.api.Test
    void makeTransaction() {
        Player player =new Player(20,"Dennis");

        boolean fail = player.makeTransaction(50);
        assertTrue(fail);
    }
    @org.junit.jupiter.api.Test
    void makeTransaction1()
    {
        Player player =new Player(20, "Dennis");

        boolean success = player.makeTransaction(-19);
        assertTrue(success);
    }
    @org.junit.jupiter.api.Test
    void makeTransaction2()
    {
        Player player =new Player(20, "Dennis");

        boolean fail = player.makeTransaction(-1000000000);
        assertFalse(fail);
    }
    @org.junit.jupiter.api.Test
    void makeTransaction3()
    {
        Player player =new Player(20, "Dennis");

        boolean success = player.makeTransaction(+1000000000);
        assertFalse(success);
    }
    @org.junit.jupiter.api.Test
    void makeTransaction4()
    {
        Player player =new Player(20, "Dennis");

        boolean success = player.makeTransaction(-1000);
        assertFalse(success);
    }
}
