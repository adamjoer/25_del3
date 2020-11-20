package game;

import java.util.Scanner;

public class Game {

    final static int DICE = 2;
    final static int DIE_MAXVALUE = 6;
    final static int PLAYERS = 2;
    final static int POINTS_GOAL = 3000;

    static Beaker beaker;
    static GameBoard gameBoard;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        String newInput;

        // Make an array with the requested number of players.
        String[] playerNames = new String[PLAYERS];
        for (int i = 0; i < PLAYERS; i++) {

            // While loop to ensure that all players get names, also prevents whitespaces in console.
            while (true) {

                System.out.printf("Please enter name of player %d: ", i + 1);
                newInput = scan.nextLine();

                if (newInput.equals("")) {
                    System.out.println("Invalid input.");
                } else {
                    playerNames[i] = newInput;
                    break;
                }
            }
        }

        // Create instances of objects
        beaker = new Beaker(DICE, DIE_MAXVALUE);
        gameBoard = new GameBoard(PLAYERS, playerNames);

        int playerTurn;
        boolean end = false;

        // Loop for restarting game
        do {
            playerTurn = 0;
            for (int i = 0; i < PLAYERS; i++) { gameBoard.movePlayer(i,0); }
            // Play game until someone wins
            while (true) {

                // Announce player turn
                System.out.printf("\nIt is %s's turn.\n", gameBoard.getName(playerTurn));

                // Prompt player for input and wait
                Utility.awaitEnter();

                // Roll the dice and move that number of tiles on the board
                beaker.roll();
                int diceResult = beaker.getSum();
                System.out.printf("You rolled %d\n", diceResult);
                gameBoard.movePlayer(playerTurn, diceResult);

                // Execute tileAction for the given tile
                gameBoard.tileAction(playerTurn);

                // Has the player won? If so, announce winner and break the loop
                if (gameBoard.getBalance(playerTurn) >= POINTS_GOAL) {
                    System.out.printf("You have reached the goal of %d points, and win! Congratulations, %s!\n", POINTS_GOAL, gameBoard.getName(playerTurn));
                    break;

                } else { // Go on to next turn

                    // Check if current player got an extra turn
                    if (!gameBoard.getExtraTurn(playerTurn)) {

                        // If they didn't get an extra turn, move on to the next one
                        playerTurn = (playerTurn + 1) % PLAYERS;
                    }
                }
            }
            System.out.println("\nGG.");

            // Check if user wants to play again
            char c;
            while (true) {
                System.out.print("Play again? (Y/N) ");
                newInput = scan.nextLine();

                try {
                    c = newInput.charAt(0);
                    break;

                } catch (Exception e) {
                    if (e instanceof StringIndexOutOfBoundsException) {
                        continue;
                    }
                    throw e;
                }
            }

            // Act based on input
            switch (c) {
                case 'Y':
                case 'y':
                    for (int i = 0; i < PLAYERS; i++) {
                        gameBoard.setBalance(i, 1000);
                        gameBoard.setExtraTurn(i, false);
                        gameBoard.setPosition(i, -1);
                    }
                    break;

                default:
                    System.out.println("Input not recognized: Ending game.");
                case 'N':
                case 'n':
                    end = true;
            }

        } while (!end);
        scan.close();
    }
}
