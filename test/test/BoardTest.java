package test;

import org.junit.jupiter.api.Test;
import game.Board;
import game.Die;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    final int players = 2;
    final int tests = 1000;
    final Board board = new Board(players, new String[]{"John", "Jane"});
    final Die d = new Die(12);

    @Test
    void movePlayer() {
        int increment;
        int position;

        for (int i = 0; i < tests; i++) {

            // Test all players alternately
            for (int j = 0; j < players; j++) {
                d.roll();
                increment = d.getFaceValue();
                position = board.getPosition(j);
                board.movePlayer(j, increment);

                assertEquals((position + increment) % 12, board.getPosition(j));
            }
        }
    }

    @Test
    void tileAction() {
        boolean xturn;
        int money;

        for (int i = 0; i < tests; i++) {

            // Test all players alternately
            for (int j = 0; j < players; j++) {
                d.roll();
                board.movePlayer(j, d.getFaceValue());

                System.out.printf("Position for player %d: %d", j, board.getPosition(j));
                xturn = board.getPosition(j) == 9;
                money = board.getBalance(j) + getTileAction(board.getPosition(j));

                board.tileAction(j);
                assertEquals(money, board.getBalance(j));
                assertEquals(xturn, board.getExtraTurn(j));
            }
        }
    }

    int getTileAction(int number) {
        return switch (number) {
            case 0 -> -60;
            case 1 -> 250;
            case 2 -> -100;
            case 3 -> 100;
            case 4 -> -20;
            case 5 -> 180;
            case 6 -> 0;
            case 7 -> -70;
            case 8 -> 60;
            case 9 -> -80;
            case 10 -> -50;
            case 11 -> 650;
            default -> throw new AssertionError();
        };
    }
}