package game;

public class Board {

    // Declarations
    final private Tile[] gameBoard;
    final private Player[] scoreBoard;

    // Constructor. Loads XML info into Tile array. Sets Player names.
    public Board (int players, String[] playerNames) {
        this.gameBoard = Utility.tileGenerator("src/game/tileList.xml");
        this.scoreBoard = new Player[players];

        if (playerNames.length < players) {
            System.out.println("Number of player names doesn't match number of players!");
            return;
        }

        // Make an array with the requested number of players.
        for (int i = 0; i < players; i++) {
            this.scoreBoard[i] = new Player(playerNames[i], 1000);
        }
    }

    // Move the player on the board.
    public void movePlayer(int player,int increment){
        int currentPosition = this.scoreBoard[player].getPosition();
        int newPosition = (currentPosition + increment) % gameBoard.length;
        this.scoreBoard[player].setPosition(newPosition);
    }
    // Execute action of the tile the player is on
    public void tileAction(int player){
        int tile = this.scoreBoard[player].getPosition();
        int value = this.gameBoard[tile].getPoints();

        System.out.printf("You've landed on tile %d: ", tile + 1);
        System.out.println(this.gameBoard[tile].getName());
        System.out.println(this.gameBoard[tile].getFlavorText());

        // Announce the coming change in player balance.
        if (value > 0){ System.out.printf("You get %d gold coins.\n", value); }
        else if (value < 0){ System.out.printf("You lose %d gold coins.\n", -value); }
        else { System.out.println("You don't get any gold coins, but you don't lose any either."); }

        // Attempt to make the transaction. If transaction fails, set balance to 0, and announce player broke.
        boolean transaction = this.scoreBoard[player].makeTransaction(value);
        if (!transaction){
            System.out.println("You don't have enough gold coins. You are broke.");
            this.scoreBoard[player].setBalance(0);
        }

        // Announce new balance
        System.out.printf("You now have %d gold coins in total.\n", this.scoreBoard[player].getBalance());

        // Set extra turn false - if
        this.scoreBoard[player].setExtraTurn(false);


        // Set extra turn
        if (this.gameBoard[tile].getExtraTurn()) {
            System.out.println("You get an extra turn.");
            this.scoreBoard[player].setExtraTurn(true);
        }
    }

    // Relevant getters for players
    public int getPosition(int player){ return this.scoreBoard[player].getPosition(); }
    public int getBalance(int player){ return this.scoreBoard[player].getBalance(); }
    public String getName(int player){ return this.scoreBoard[player].getName(); }
    public boolean getExtraTurn(int player){ return this.scoreBoard[player].getExtraTurn(); }

    // Relevant setters for players
    public void setPosition(int player,int newPosition){ this.scoreBoard[player].setPosition(newPosition); }
    public void setBalance(int player,int newBalance){ this.scoreBoard[player].setBalance(newBalance); }
    public void setExtraTurn(int player, boolean extraTurn){ this.scoreBoard[player].setExtraTurn(extraTurn); }
}
