package game;

public class Game {

    final static int DICE = 2;
    final static int DIE_MAXVALUE = 6;
    private static Beaker beaker;
    private static GameBoard board;

    public static void main(String[] args) {
        beaker = new Beaker(DICE, DIE_MAXVALUE);
        board = new GameBoard();

    }
}
