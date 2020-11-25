package game;

public class GameBoard {

    // Declarations
    final private Field[] gameBoard;
    final private Player[] scoreBoard;

    // Constructor. Loads XML info into Field array. Sets Player names.
    public GameBoard(int players, String[] playerNames) {
        this.gameBoard = Utility.fieldGenerator("src/main/resources/tileList.xml");
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

        // Get the field that the player has landed on from their position
        int position = scoreBoard[player].getPosition();
        String field = gameBoard[position].getField();

        // Act based on which field the player landed on
        switch (field) {

            case "Start":
                startFieldAction();
                break;

            case "Property":
                propertyFieldAction();
                break;

            case "GoToJail":
                goToJailFieldAction();
                break;

            // If landed on Jail or parking lot, do nothing
            case "Jail":
            case "ParkingLot":
                break;

            case "Chance":
                chanceFieldAction();
                break;

            // Error: Field name not recognised
            default:
                throw new IllegalArgumentException();
        }

/*
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
*/
    }

    private void startFieldAction() {

    }

    private void propertyFieldAction() {

    }

    private void goToJailFieldAction() {

    }

    private void chanceFieldAction() {

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
