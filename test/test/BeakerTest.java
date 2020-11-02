package test;
import game.Beaker;

import static org.junit.jupiter.api.Assertions.*;

class BeakerTest {

    int dies = 2;
    int dieFaces = 6;
    int tests = 1000;

    Beaker beaker = new Beaker(dies, dieFaces);


    //test if the isIdentical returns the correct boolean
    @org.junit.jupiter.api.Test
    void isIdentical() {
        for (int j = 0; j < tests; j++) {
            beaker.roll();
            boolean identical = true;

            //find the expected value
            for (int i = 1; i < dies; i++) {
                if (beaker.getFaceValue(i) == beaker.getFaceValue(i - 1)) {
                    identical = true;
                } else {
                    identical = false;
                    break;
                }
            }

            assertEquals(identical, beaker.isIdentical());
        }

    }

    @org.junit.jupiter.api.Test
    void getSum() {
        for (int j = 0; j < tests; j++) {
            beaker.roll();

            int sum = 0;

            for (int i = 0; i < dies; i++) {
                sum += beaker.getFaceValue(i);
            }

            assertEquals(sum, beaker.getSum());
        }

    }
}