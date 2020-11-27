package game;

public class Game {

    final static int DICE = 1;
    final static int DIE_MAXVALUE = 6;
    private static Beaker beaker;
    private static GameBoard board;

    public static void main(String[] args) {

        // Initialize Beaker and GameBoard
        beaker = new Beaker(DICE, DIE_MAXVALUE);
        board = new GameBoard();

        // Variables to keep track of game loop
        int playerTurn, moveCardValue;

        // Keep playing until a winner is found
        do {

            // Get player whose turn it is
            playerTurn = board.getNextPlayerTurn();

            // Check if this player has drawn a chance card where they have to move to a certain field
            moveCardValue = board.checkIfPlayerHasMoveCard(playerTurn);
            if (moveCardValue != 0) {

                // If player has drawn chance card, move to that position immediately
                board.setPlayerPosition(playerTurn, moveCardValue);

            } else { // Else cast dice and move that number of fields
                beaker.roll();

                // Show faceValue on GUI
                board.castDie(beaker.getSum());

                // Move player that number of fields
                board.movePlayer(playerTurn, beaker.getSum());

            }

            // Check if player has passed start, and if they have, reward them
            board.checkStartPass(playerTurn);

            // Execute fieldAction for the given field
            board.fieldAction(playerTurn);

        } while (!board.hasWinner());
    }
}
