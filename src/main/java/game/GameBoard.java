package game;

import java.awt.*;

public class GameBoard {

    // Attributes
    private final Field[] fields;
    private final ActorController actorController;
    private final ChanceCardController chanceCardController = new ChanceCardController();
    private int[] playersWithMoveCards;
    private int playerWithJailCard;
    private final Player[] players;
    private final Bank bank;
    private int jailPosition;
    private int goToJailPosition;

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
            
            } else if (field instanceof Jail) {
                jailPosition = field.getPosition();
            
            } else if (field instanceof GoToJail) {
                goToJailPosition = field.getPosition();
            }
        }

        playersWithMoveCards = new int[actors.length];
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
                goToJailFieldAction(player);
                break;

            // If landed on Jail (just visiting) or parking lot, do nothing
            case "Jail":
                success = jailFieldAction(player);

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
            System.out.println("You don't have   enough gold coins. You are broke.");
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

    private void goToJailFieldAction(int player) {
        players[player].setCurrentPosition(jailPosition);
    }

    private boolean jailFieldAction(int player) {
        if (players[player].getPreviousPosition() != goToJailPosition) {
            return true;
        }

        // If player has free card, take it away

        // If player doesn't have free card, try to pay fine (to bank)
        // Return whether transaction was successful

        return false;
    }

    /**
     * Method
     * @param player
     */
    private void chanceFieldAction(int player) {
        //draw a chancecard
        ChanceCard cCard = chanceCardController.drawChanceCard();

        //get the type of chancecard
        String cardType = cCard.getClass().getSimpleName();

        //check what action to do, based on what chancecard was drawn
        switch (cardType){
            case "MoveToColorCard":
                //get the color to move to
                Color color = ((MoveToColorCard) cCard).getColor();

                //update the list
                moveToColor(color, player);

                //move the player to the field
                actorController.setCurrentPosition(player, playersWithMoveCards[player]);
                playersWithMoveCards[player] = 0;

                //make sure the player does the action of the tile after moving
                tileAction(player);
                break;


            case "TargetedCard":
                //get the color to move to
                Color colorTargeted = ((TargetedCard) cCard).getColor();

                //get the player that is going to be moved
                int target = ((TargetedCard) cCard).getTargetedPlayer();

                //update the list of what players have to move at the start of their turn.
                moveToColor(colorTargeted, target);
                break;

            case "HeldCard":
                //set what player has the jailcard
                playerWithJailCard = player;
                break;


            case "StandardCard":
                //get the destination, amount and the action of the card
                int destination = ((StandardCard) cCard).getDestination();
                int amount = ((StandardCard) cCard).getAmount();
                String action = ((StandardCard) cCard).getCardAction();

                standardCardAction(player, destination, amount, action);
                break;


        }
    }


    /**
     * Method used to indicate what players have to move at the start of their turn.
     * @param color Which color the field they have to move to has
     * @param player Which player is being moved
     */
    private void moveToColor(Color color, int player){
        //variable used to hold the position of the first field owned by a player
        int firstOwned = 0;

        //iterate over all the fields
        for(int i = actorController.getCurrentPosition(player); i < fields.length; i++){
            int currentField = i % 24;
            //check if the field is of type "Property"
            if(fields[currentField].getField() == "Property"){
                //check if the field has the correct color
                if(((Property) fields[currentField]).getColor() == color){
                    //check if it's owned by a player, if it not, set the player to move there on their next turn
                    if(((Property) fields[currentField]).getOwner() == actorController.getActors()[0]){
                        playersWithMoveCards[player] = currentField;
                        break;
                    }
                    //If field is owned by a player, set the variable to the position, and go to next iteration
                    else if(firstOwned == 0){
                        firstOwned = currentField;
                    }
                }
            }
        }

        //If there were no available unowned fields, set it the the first owned field of the color
        if(firstOwned != 0){
            playersWithMoveCards[player] = firstOwned;
        }
    }

    /**
     * Used to check if the player has to move on the start of their turn
     * @param player What player to check
     * @return Return the field they have to move to, otherwise, return 0
     */
    public int checkIfPlayerHasMoveCard(int player){
        if(playersWithMoveCards[player] != 0){
            return playersWithMoveCards[player];
        }
        else{
            return 0;
        }

    }

    public int getPlayerWithJailCard(){
        return playerWithJailCard;
    }

    private void standardCardAction(int player, int destination, int amount, String action){
        //check what action the card has to do
        switch(action){
            case "fine":
                //remove some money from the players account
                actorController.makeTransaction(player, 0, amount);
                break;

            case "gift":
                //insert some money into the players account
                actorController.makeTransaction(0, player, amount);
                break;

            case "playerGift":
                //insert money into the players account from the other players
                playerGift(player, amount);
                break;

            case "move":
                //move the player an amount
                actorController.movePlayer(player, destination);
                tileAction(player);
                break;

            case "moveDesination":
                //move the player to a specific field
                actorController.setCurrentPosition(player, destination);
                tileAction(player);
                break;
        }
    }

    /**
     * Method to transfer money to a player, from all the other players
     * @param receiver The receiver of money
     * @param amount The amount to receive from every player
     */
    private void playerGift(int receiver, int amount){
        for(int j = 1; j < actorController.getActors().length; j++){
            if(j != receiver){
                actorController.makeTransaction(j, receiver, amount);
            }
        }

    }
}
