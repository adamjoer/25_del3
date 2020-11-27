package game;

public class Game {

    final static int DICE = 1;
    final static int DIE_MAXVALUE = 6;
    private static Beaker beaker;
    private static GameBoard board;

    public static void main(String[] args) {
        beaker = new Beaker(DICE, DIE_MAXVALUE);
        board = new GameBoard();

        int playerTurn, moveCardValue;

        do {
            playerTurn = board.getNextPlayerTurn();

            moveCardValue = board.checkIfPlayerHasMoveCard(playerTurn);
            if (moveCardValue != 0) {
                board.setPlayerPosition(playerTurn, moveCardValue);

            } else {
                beaker.roll();
                board.castDie(beaker.getSum());

                board.movePlayer(playerTurn, beaker.getSum());
            }

            board.fieldAction(playerTurn);

        } while (!board.hasWinner());
    }
}
