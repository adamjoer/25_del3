package game;

import gui_main.GUI;

import java.awt.*;

public class GameBoard {

    // Attributes
    private final Field[] fields;
    private final ActorController actorController;
    private final GUIController guiController;
    private final ChanceCardController chanceCardController;
    private int[] playersWithMoveCards;
    private int playerWithJailCard = 0;
    private final Player[] players;
    private int playerTurn = 0;

    // Constructor. Loads XML info into Field array. Sets Player names.
    public GameBoard() {
        fields = Utility.fieldGenerator("src/main/resources/fieldList.xml");

        guiController = new GUIController(fields, fields.length);

        guiController.askForPlayerNames();
        actorController = new ActorController(guiController.returnPlayerNames());


        chanceCardController = new ChanceCardController();

        Actor[] actors = actorController.getActors();

        players = new Player[actors.length - 1];
        for (int i = 1; i < actors.length; i++) {
            players[i] = (Player) actors[i];
        }

        if (!guiController.addPlayers(players)) {
            return;
        }

        for (Player player : players) {
            guiController.setCar(player, true, 0);
        }

        playersWithMoveCards = new int[actors.length];
    }

    public void castDice(int faceValue1, int faceValue2) {

        // Show 2 dice being cast, with randomized spins
        guiController.setDiceGui(faceValue1, (int) (Math.random() * 360), faceValue2, ((int) (Math.random() * 360)));
    }

    public void castDie(int faceValue) {

        // Show a die being cast
        guiController.setDiceGui(faceValue);
    }

    public int getNextPlayerTurn() {
        playerTurn = (playerTurn + 1) % players.length;
        return playerTurn;
    }

    // Move the player on the board.
    public void movePlayer(int player, int increment) {

        // Move player forward by the specified amount of steps
        actorController.movePlayer(player, increment);

        // Show player moving forward in GUI
        guiController.setCarPlacement(players[player], players[player].getPreviousPosition(), players[player].getCurrentPosition());
    }

    // Returns whether player has passed Start field
    public boolean hasPassedStart(int player) {

        // If start field position is zero, player will have passed start if their position has overflowed to a smaller value
        // a.i. their previous position is larger than their current position
        return players[player].getPreviousPosition() > players[player].getCurrentPosition();
    }

    public boolean giveStartReward(int player) {
        boolean successfulTransaction = actorController.makeTransaction(player, 0, ((Start) fields[0]).getReward());

        // Show in GUI that money has been withdrawn from player
        guiController.setPlayerBalance(players[player], players[player].getBalance());

        return successfulTransaction;
    }

    // Execute action of the field the player is on
    public void fieldAction(int player) {

        // Give player start reward if they have passed start
        if (hasPassedStart(player)) {
            giveStartReward(player);
        }

        // Get the field that the player has landed on from their position
        int position = actorController.getCurrentPosition(player);
        String field = fields[position].getField();

        // Act based on which field the player landed on
        boolean success = false, doNothing = false;
        switch (field) {

            case "Property":
                success = propertyFieldAction(position, player);
                break;

            case "GoToJail":
                success = goToJailFieldAction(position, player);
                break;

            // If player landed on jail (just visiting), start or parking lot field, do nothing
            case "Jail":
            case "Start":
            case "ParkingLot":
                doNothing = true;
                break;

            case "Chance":
                success = chanceFieldAction(player);
                break;

            // Error: Field name not recognised
            default:
                throw new IllegalArgumentException();
        }

        if (doNothing) {
            return;
        }

        // A transaction didn't go through, someone is broke
        if (!success) {

            // The bank has gone broke, the winner is the player with the most money

            // Find the player with the most money and the player with the least money (playerWithMinBalance)
            int playerWithMaxBalance = 0, playerWithMinBalance = 0, maxBalance = 0, currentBalance;
            for (int i = 0; i < players.length; i++) {

                currentBalance = players[i].getBalance();

                if (currentBalance > maxBalance) {
                    playerWithMaxBalance = i;

                } else if (currentBalance == 0) {
                    playerWithMinBalance = i;
                }
            }

            if (actorController.getActors()[0].getBalance() == 0) {

                // Announce that bank has gone broke
                guiController.showMessage("The bank has gone bankrupt.");

            } else {
                // Announce which player has gone broke
                // playerWithMinBalance = players[playerWithMinBalance]
                guiController.showMessage(String.format("%s has gone bankrupt.", players[playerWithMinBalance].getName()));
            }

            // Winner is players[playerWithMaxBalance]
            // Announce that winner
            guiController.showMessage(String.format("%s wins as the player with the most money. Congratulations!", players[playerWithMaxBalance]));
        }
    }

    private boolean propertyFieldAction(int position, int player) {

        // Get property and owner
        Property property = ((Property) fields[position]);
        int owner = property.getOwner();

        // If property is owned by player, do nothing
        if (owner == player) {
            return true;
        }

        boolean successfulTransaction;

        // Property isn't owned by any players (i.e. is owned by the bank)
        if (owner == 0) {

            // Try to buy property
            // Make transaction and check if it went through
            successfulTransaction = actorController.makeTransaction(player, 0, property.getValue());

            // Assign new owner if successful
            if (successfulTransaction) {
                property.setOwner(player);

                // Show in GUI that property has been bought
                guiController.fieldOwnable(
                        property.getSubText(),
                        players[player].getName(),
                        property.getValue(),
                        property.getColor(),
                        property.getPosition()
                );
            }

        } else { // Property is owned by another player

            // Value of rent is the value of property
            int rent = property.getValue();

            // Check if owner also owns the related property
            Property relatedProperty = (Property) fields[property.getRelatedPropertyPosition()];
            if (relatedProperty.getOwner() == owner) {

                // If they do, player needs to pay double the rent
                rent *= 2;
            }

            // Make transaction and check if it went through
            successfulTransaction = actorController.makeTransaction(player, owner, rent);

            // Show in GUI that money has been deposited to owner
            if (successfulTransaction) {
                guiController.setPlayerBalance(players[owner], players[owner].getBalance());
            }
        }

        // Show in GUI that money has been withdrawn from player
        guiController.setPlayerBalance(players[player], players[player].getBalance());

        return successfulTransaction;
    }

    private boolean goToJailFieldAction(int position, int player) {

        int jailPos = ((GoToJail) fields[position]).getJailPosition();
        int bail = ((Jail) fields[jailPos]).getBail();

        players[player].setCurrentPosition(jailPos);

        // If player has free card, take it away
        if (getPlayerWithJailCard() == player) {

            guiController.displayChanceCard("Card used");
            playerWithJailCard = 0;
            return true;

        } else { // If player doesn't have free card, try to pay fine (to bank)
            boolean successfulTransaction = actorController.makeTransaction(player, 0, bail);

            // Show in GUI that money has been withdrawn from player
            guiController.setPlayerBalance(players[player], players[player].getBalance());

            // Return whether transaction was successful
            return successfulTransaction;
        }
    }

    /**
     * Method to handle what happens when a player lands on a chance card field
     *
     * @param player : The current players turn
     */
    private boolean chanceFieldAction(int player) {
        //draw a chance card
        ChanceCard cCard = chanceCardController.drawChanceCard();
        guiController.displayChanceCard(cCard.getChanceCardText());

        //get the type of chance card
        String cardType = cCard.getClass().getSimpleName();

        //check what action to do, based on what chance card was drawn
        switch (cardType) {
            case "MoveToColorCard":
                //get the color to move to
                Color color = ((MoveToColorCard) cCard).getColor();

                //update the list
                moveToColor(color, player, false);

                //move the player to the field
                actorController.setCurrentPosition(player, playersWithMoveCards[player]);
                playersWithMoveCards[player] = 0;
                guiController.setCarPlacement(players[player], players[player].getPreviousPosition(), players[player].getCurrentPosition());

                //make sure the player does the action of the field after moving
                fieldAction(player);
                return true;

            case "TargetedCard":

                //get the player that is going to be moved
                int target = ((TargetedCard) cCard).getTargetedPlayer();

                //update the list of what players have to move at the start of their turn.
                moveToColor(Color.white, target, true);

                chanceFieldAction(player);
                return true;

            case "HeldCard":
                //set which player has the jail card
                playerWithJailCard = player;
                return true;

            case "StandardCard":
                //get the destination, amount and the action of the card
                int destination = ((StandardCard) cCard).getDestination();
                int amount = ((StandardCard) cCard).getAmount();
                String action = ((StandardCard) cCard).getCardAction();

                return standardCardAction(player, destination, amount, action);

            default:
                throw new IllegalArgumentException();
        }
    }


    /**
     * Method used to indicate what players have to move at the start of their turn.
     *
     * @param color  Which color the field they have to move to has
     * @param player Which player is being moved
     */
    private void moveToColor(Color color, int player, boolean targetedCard) {

        //variable used to hold the position of the first field owned by a player
        int firstOwned = 0;

        //iterate over all the fields

        for (int i = 0; i < fields.length; i++) {
            int currentField = (i + actorController.getCurrentPosition(player)) % 24;
            //check if the field is of type "Property"
            if (fields[currentField].getField().equals("Property")) {
                //check if this method is being used for a targetedCard
                if (targetedCard) {
                    //Check if field is owned by the bank
                    if (((Property) fields[currentField]).getOwner() == 0) {
                        playersWithMoveCards[player] = currentField;
                        break;

                    } else if (firstOwned == 0) {
                        firstOwned = currentField;
                    }
                }
                //check if the field has the correct color if it's looking for a color field
                else if (((Property) fields[currentField]).getColor() == color) {
                    //check if it's owned by a player, if it not, set the player to move there on their next turn
                    playersWithMoveCards[player] = currentField;
                    break;
                }
            }
        }

        //If there were no available unowned fields, set it the the first owned field of the color
        if (firstOwned != 0) {
            playersWithMoveCards[player] = firstOwned;
        }
    }

    /**
     * Used to check if the player has to move on the start of their turn
     *
     * @param player What player to check
     * @return Return the field they have to move to, otherwise, return 0
     */
    public int checkIfPlayerHasMoveCard(int player) {
        if (playersWithMoveCards[player] != 0) {
            int i = playersWithMoveCards[player];
            playersWithMoveCards[player] = 0;
            return i;

        } else {
            return 0;
        }

    }

    private boolean standardCardAction(int player, int destination, int amount, String action) {

        boolean successfulTransaction;
        //check what action the card has to do
        switch (action) {
            case "fine":
                //remove some money from the players account
                successfulTransaction = actorController.makeTransaction(player, 0, amount);

                // Show in GUI that money has been withdrawn from player
                guiController.setPlayerBalance(players[player], players[player].getBalance());

                return successfulTransaction;

            case "gift":
                //insert some money into the players account
                successfulTransaction = actorController.makeTransaction(0, player, amount);

                if (successfulTransaction) {
                    guiController.setPlayerBalance(players[player], players[player].getBalance());
                }
                return successfulTransaction;

            case "playerGift":
                //insert money into the players account from the other players
                successfulTransaction = playerGift(player, amount);

                if (successfulTransaction) {
                    guiController.setPlayerBalance(players[player], players[player].getBalance());
                }
                return successfulTransaction;

            case "moveIncrement":
                //move the player an amount
                actorController.movePlayer(player, destination);
                guiController.setCarPlacement(players[player], players[player].getPreviousPosition(), players[player].getCurrentPosition());
                fieldAction(player);
                return true;

            case "moveDestination":
                //move the player to a specific field
                actorController.setCurrentPosition(player, destination);
                guiController.setCarPlacement(players[player], players[player].getPreviousPosition(), players[player].getCurrentPosition());
                fieldAction(player);
                return true;

            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Method to transfer money to a player, from all the other players
     *
     * @param receiver The receiver of money
     * @param amount   The amount to receive from every player
     */
    private boolean playerGift(int receiver, int amount) {
        boolean successfulTransaction;
        for (int j = 1; j < actorController.getActors().length; j++) {
            if (j != receiver) {
                successfulTransaction = actorController.makeTransaction(j, receiver, amount);

                // Show in GUI that money has been withdrawn from player
                guiController.setPlayerBalance(players[j], players[j].getBalance());

                if (!successfulTransaction) {
                    return false;
                }
            }
        }
        return true;
    }

    // Relevant getters
    public int getPlayerWithJailCard() {
        return playerWithJailCard;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public String toString() {

        StringBuilder output = new StringBuilder();
        for (Field field : fields) {
            output.append(field.toString());
            output.append("\n\n");
        }

        return output.toString();
    }
}
