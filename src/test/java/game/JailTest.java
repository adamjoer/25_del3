package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JailTest {

    Jail jail = new Jail("testJail", "testJail", "This is a test jail", 0, 2);
    Player[] players = {
            new Player("Alice", 18),
            new Player("Bob", 18),
            new Player("Bob", 18)
    };

    @Test
    void incarcerate() {

        int n = 0;
        for (int i = 0; i < players.length; i++) {
            jail.incarcerate(players[i]);

            Player[] prisoners = jail.getPrisoners();

            assertEquals(players[i], prisoners[i]);

            n++;
            assertEquals(n, prisoners.length);
        }

    }

    @Test
    void release() {
    }
}