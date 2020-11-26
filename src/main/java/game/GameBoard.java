package game;

public class GameBoard {

    // Attributes
    private final Field[] fields;
    private final ActorController actorController;
    private final Player[] players;
    private final Bank bank;

    // Constructor. Loads XML info into Field array. Sets Player names.
    public GameBoard(int numberOfPlayers) {
        this.fields = Utility.fieldGenerator("src/main/resources/tileList.xml");


        this.actorController = new ActorController(numberOfPlayers);
        Actor[] actors = actorController.getActors();
        this.bank = (Bank) actors[0];

        this.players = new Player[actors.length - 1];
        for (int i = 1; i < actors.length; i++) {
            players[i] = (Player) actors[i];
        }

        // Go over each tile and if it is a property, set the owner to the bank
        for (Field field : fields) {
            if (field instanceof Property) {
                ((Property) field).setOwner(bank);
            
            }
        }
    }

    // Move the player on the board.
    public void movePlayer(int player, int increment) {

        actorController.movePlayer(player, increment);
    }

    // Returns whether player has passed Start field   !!!!!! PROVIDED THAT START FIELD'S POSITION IS 0 !!!!!!
    public boolean hasPassedStart(int player) {

        // If start field position is zero, player will have passed start if their position has overflowed to a smaller value
        // a.i. their previous position is larger than their current position
        return actorController.getPreviousPosition(player) > actorController.getCurrentPosition(player);
    }

    public boolean giveStartReward(int player) {
        return bank.makeTransaction(players[player], ((Start) fields[0]).getReward());
    }

    // Execute action of the tile the player is on
    public void tileAction(int player) {

        // Get the field that the player has landed on from their position
        int position = actorController.getCurrentPosition(player);
        String field = fields[position].getField();

        // Act based on which field the player landed on
        boolean success;
        switch (field) {

            case "Start":
                startFieldAction(player);
                break;

            case "Property":
                success = propertyFieldAction(position, player);
                break;

            case "GoToJail":
                goToJailFieldAction(position, player);
                break;

            // If landed on Jail (just visiting) or parking lot, do nothing
            case "Jail":
                success = jailFieldAction(position, player);

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
        // Call hasPassedStart?
    }

    private boolean propertyFieldAction(int position, int player) {

        // Get property and owner
        Property property = ((Property) fields[position]);
        Actor owner = property.getOwner();

        // If property is owned by player, do nothing
        if (owner.equals(players[player])) {
            return true;

        // If property isn't owned by anyone (i.e. is owned by the bank), try to buy it
        } else if (owner.equals(bank)) {

            // If player failed to buy property, they are broke
            return property.sellProperty(players[player]);

        // Property is owned by another player
        } else {

            // Value of fine is the value of property
            int fine = property.getValue();

            // Check if owner also owns the related property
            Property relatedProperty = (Property) fields[property.getRelatedPropertyPosition()];
            if (relatedProperty.getOwner().equals(owner)) {

                // If they do, player needs to pay double the fine
                fine *= 2;
            }

            // Try to pay fine to owner, if the failed they are broke
            return players[player].makeTransaction(owner, fine);
        }
    }

    private void goToJailFieldAction(int position, int player) {
        players[player].setCurrentPosition(((GoToJail) fields[position]).getJailPosition());
    }

    private boolean jailFieldAction(int position, int player) {
        if (players[player].getPreviousPosition() != ((Jail) fields[position]).getGoToJailPosition()) {
            return true;
        }

        // If player has free card, take it away

        // If player doesn't have free card, try to pay fine (to bank)
        // Return whether transaction was successful

        return false;
    }

    private void chanceFieldAction(int player) {

    }
}
