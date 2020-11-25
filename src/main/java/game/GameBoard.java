package game;

public class GameBoard {

    // Declarations
    final private Field[] fields;
    final private ActorController actorController;

    // Constructor. Loads XML info into Field array. Sets Player names.
    public GameBoard(int players) {
        this.fields = Utility.fieldGenerator("src/main/resources/tileList.xml");


        actorController = new ActorController(players);
    }

    // Move the player on the board.
    public void movePlayer(int player, int increment) {
/*
        int currentPosition = this.players[player].getPosition();
        int newPosition = (currentPosition + increment) % fields.length;
        this.players[player].setPosition(newPosition);
*/

    }

    // Execute action of the tile the player is on
    public void tileAction(int player) {

        // Get the field that the player has landed on from their position
        int position = players[player].getPosition();
        String field = fields[position].getField();

        // Act based on which field the player landed on
        switch (field) {

            case "Start":
                startFieldAction(player);
                break;

            case "Property":
                propertyFieldAction(position, player);
                break;

            case "GoToJail":
                goToJailFieldAction(player);
                break;

            // If landed on Jail or parking lot, do nothing
            case "Jail":
            case "ParkingLot":
                break;

            case "Chance":
                chanceFieldAction(player);
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

    private void startFieldAction(int player) {

    }

    private void propertyFieldAction(int position, int player) {
        Actor owner = ((Property) fields[position]).getOwner();

    }

    private void goToJailFieldAction(int player) {

    }

    private void chanceFieldAction(int player) {

    }
}
