package test;
import game.Player;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

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
}
