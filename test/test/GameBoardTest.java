package test;

import org.junit.jupiter.api.Test;
import game.GameBoard;
import game.Die;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    final int players = 2;
    final int tests = 1000;
    final GameBoard gameBoard = new GameBoard(players, new String[]{"John", "Jane"});
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
                position = gameBoard.getPosition(j);
                gameBoard.movePlayer(j, increment);

                assertEquals((position + increment) % 12, gameBoard.getPosition(j));
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
                gameBoard.movePlayer(j, d.getFaceValue());

                System.out.printf("Position for player %d: %d", j, gameBoard.getPosition(j));
                xturn = gameBoard.getPosition(j) == 9;
                money = gameBoard.getBalance(j) + getTileAction(gameBoard.getPosition(j));

                gameBoard.tileAction(j);
                assertEquals(money, gameBoard.getBalance(j));
                assertEquals(xturn, gameBoard.getExtraTurn(j));
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