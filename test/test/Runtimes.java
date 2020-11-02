package test;

import game.*;

public class Runtimes {
    public static void main(String[] args){
        Beaker beaker = new Beaker(2, 6);
        Player player = new Player("Test",1000);
        Tile[] gameBoard = Utility.tileGenerator("src/game/tileList.xml");

        int tests = 1_000_000;
        int currentPosition;
        int newPosition;
        int value;
        System.out.printf("Executing loop %d times.\n", tests);
        long timeStart = System.currentTimeMillis();
        System.out.println("Time before executing loop = " + timeStart);
        for (int i = 0; i < tests; i++){
            // Roll the dice and move that number of tiles on the board
            beaker.roll();
            int diceResult = beaker.getSum();
            // movePlayer here
            currentPosition = player.getPosition();
            newPosition = (currentPosition + diceResult) % gameBoard.length;
            player.setPosition(newPosition);
            // tileAction here

            value = gameBoard[currentPosition].getPoints();

            // Attempt to make the transaction. If transaction fails, set balance to 0, and announce player broke.
            boolean transaction = player.makeTransaction(value);
            if (!transaction){
                player.setBalance(0);
            }
        }

        // Calculate how long it took to run the for loop.
        long timeEnd = System.currentTimeMillis();
        System.out.println("Time after executing loop = " + timeEnd);
        long runTime = timeEnd-timeStart;
        System.out.println("Time elapsed (milliseconds) = " + runTime);
        // Divide it by the number of tests, to have an estimate on how long each test took.
        double perTurn = (double) (runTime) / (double) (tests);

        System.out.println("Upper bound on average time per roll: " + perTurn);
        System.out.println("Is this less than 150ms? " + (perTurn < 150));

    }

}
