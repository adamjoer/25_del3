package test;
import game.Die;
import static org.junit.jupiter.api.Assertions.*;

class DieTest {

    int n = 6;
    Die die = new Die(n);
    int tests = 10000;
    int diceSpread = 400;

    @org.junit.jupiter.api.Test
    void roll() {
        /**
         * Tests if the rolled number is within the amount of die faces
         */
        for (int i = 0; i < tests; i++) {

            die.roll();
            int result = die.getFaceValue();
            assertTrue(result>=1 && result<=n);
        }

        /**
         * saves the result of the die and tests if the collective spread is within the interval of uncertainty
         */
        int[] spread = new int[n];

        for (int i = 0; i < tests; i++) {
            die.roll();
            int result = die.getFaceValue();
            spread[result-1]++;

        }

        for (int i = 0; i < spread.length-1; i++) {
            assertTrue(spread[i]>tests/n-diceSpread && spread[i]<tests/n+diceSpread);
        }
    }
}