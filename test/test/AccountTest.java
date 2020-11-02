package test;

import game.Player;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest
{
    @org.junit.jupiter.api.Test
    void makeTransaction() {
        Player player =new Player("Dennis",1000);

        boolean fail = player.makeTransaction(-1050);
        assertFalse(fail);
    }
    @org.junit.jupiter.api.Test
    void makeTransaction1()
    {
        Player player =new Player("Dennis",1000);

        boolean success = player.makeTransaction(-950);
        assertTrue(success);
    }
    @org.junit.jupiter.api.Test
    void makeTransaction2()
    {
        Player player =new Player("Dennis",1000);

        boolean fail = player.makeTransaction(-1000000000);
        assertFalse(fail);
    }
    @org.junit.jupiter.api.Test
    void makeTransaction3()
    {
        Player player =new Player("Dennis",1000);

        boolean success = player.makeTransaction(+1000000000);
        assertTrue(success);
    }
    @org.junit.jupiter.api.Test
    void makeTransaction4()
    {
        Player player =new Player("Dennis",1000);

        boolean success = player.makeTransaction(-1000);
        assertTrue(success);
    }
}
