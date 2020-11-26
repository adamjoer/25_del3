package game;

import java.awt.*;

public class GameBoard {

    // Attributes
    private final Field[] fields;
    private final ActorController actorController;
    private final ChanceCardController chanceCardController = new ChanceCardController();

    private int[] playersWithMoveCards;

    // Constructor. Loads XML info into Field array. Sets Player names.
    public GameBoard(int players) {
        this.fields = Utility.fieldGenerator("src/main/resources/tileList.xml");


        actorController = new ActorController(players);

        // Go over each tile and if it is a property, set the owner to the bank
        Actor[] actors = actorController.getActors();
        for (Field field : fields) {
            if (field instanceof Property) {
                ((Property) field).setOwner(actors[0]);
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

    // Execute action of the tile the player is on
    public void tileAction(int player) {

        // Get the field that the player has landed on from their position
        int position = actorController.getCurrentPosition(player);
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

            // If landed on Jail (just visiting) or parking lot, do nothing
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

    private void propertyFieldAction(int position, int player) {
        Actor owner = ((Property) fields[position]).getOwner();

    }

    private void goToJailFieldAction(int player) {

    }

    private void chanceFieldAction(int player) {
        ChanceCard cCard = chanceCardController.drawChanceCard();
        String cardType = cCard.getClass().getSimpleName();

        switch (cardType){
            case "MoveToColorCard":
                Color color = ((MoveToColorCard) cCard).getColor();

                moveToColorCard(color, player);
                break;

            case "TargetedCard":
                Color colorTargeted = ((TargetedCard) cCard).getColor();

                targetedCard(color, player);

        }
    }


    private void targetedCard(Color color, int player){
        int firstOwned = 0;
        for(int i = actorController.getCurrentPosition(player); i < fields.length; i++){
            int currentField = i % 24;
            if(fields[currentField].getField() == "Property"){
                if(((Property) fields[currentField]).getColor() == color){
                    if(((Property) fields[currentField]).getOwner() != actorController.getActors()[0]){
                        playersWithMoveCards[player] = currentField;
                        break;
                    }
                    else if(firstOwned == 0){
                        firstOwned = currentField;
                    }
                }
            }
        }

        //If there were no available unowned fields, set it the the first owned field of the color
        playersWithMoveCards[player] = firstOwned;
    }

    public int checkIfPlayerHasMoveCard(int player){
        if(playersWithMoveCards[player] != 0){
            return playersWithMoveCards[player];
        }
        else{
            return 0;
        }

    }
}
