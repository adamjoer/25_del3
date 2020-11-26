package game;

public class GameBoard {

    // Attributes
    private final Field[] fields;
    private final ActorController actorController;
    private final GUIController guiController;
    private final Player[] players;
    private final Bank bank;

    // Constructor. Loads XML info into Field array. Sets Player names.
    public GameBoard() {
        fields = Utility.fieldGenerator("src/main/resources/tileList.xml");

        guiController = new GUIController();

        guiController.askForPlayerNames();
        actorController = new ActorController(guiController.returnPlayerNames());

        Actor[] actors = actorController.getActors();
        bank = (Bank) actors[0];

        players = new Player[actors.length - 1];
        for (int i = 1; i < actors.length; i++) {
            players[i] = (Player) actors[i];
        }

        if (!guiController.addPlayers(players)) {
            return;
        }

        // Go over each tile and if it is a property, set the owner to the bank
        for (Field field : fields) {
            if (field instanceof Property) {
                ((Property) field).setOwner(bank);
            
            }
        }

        for (Player player : players) {
            guiController.setCar(player, true, 0);
        }
    }

    // Move the player on the board.
    public void movePlayer(int player, int increment) {

        actorController.movePlayer(player, increment);
        guiController.setCarPlacement(players[player], players[player].getPreviousPosition(), players[player].getCurrentPosition());
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
        }

        // If property isn't owned by any players (i.e. is owned by the bank)
        boolean bought;
        if (owner.equals(bank)) {

            // Try to buy property
            bought = property.sellProperty(players[player]);


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
            bought = players[player].makeTransaction(owner, fine);
        }

        if (bought) {
            guiController.setPlayerBalance(players[player], players[player].getBalance());

            guiController.fieldOwnable(
                    property.getSubText(),
                    players[player].getName(),
                    property.getValue(),
                    property.getColor(),
                    property.getPosition()
            );
            return true;

        } else {
            return false;
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
