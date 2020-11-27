package game;

public class ActorController {
    //Defining variables

    private final Actor[] actors;
    private final int startBalance;
    private final int BANK_BALANCE = 90;
    //Momentary name
    private String name;


    /**
     * @param playerNames
     */
    public ActorController(String[] playerNames) {

        //Defining number of players.
        actors = new Actor[playerNames.length + 1];

        //Calculating each players starting balance, based on the number of players
        if (playerNames.length == 4) {
            startBalance = 16;
        } else if (playerNames.length == 3) {
            startBalance = 18;
        } else {
            startBalance = 20;
        }

        // Players get their money from the bank, the sum of money that is assigned to players is therefore withdrawn from the bank
        actors[0] = new Bank(BANK_BALANCE - (startBalance * playerNames.length));

        //Assigning player names and bank with their starting balance
        for (int i = 1; i < actors.length; i++) {
            actors[i] = new Player(startBalance, playerNames[i - 1]);
        }
    }


    /**
     * @param sender
     * @param receiver
     * @param amount
     * @return
     */
    //makes transaction between two actors and returns false if senders balance is below amount
    public boolean makeTransaction(int sender, int receiver, int amount) {
        if (actors[sender].makeTransaction(-amount) && amount < 91) {
            actors[receiver].makeTransaction(amount);
            return true;

        } else {
            actors[sender].setBalance(0);
            return false;
        }
    }


    /**
     * @param player
     * @param increment
     */
    //Calls Player class method with actor array
    public void movePlayer(int player, int increment) {
        ((Player) actors[player + 1]).movePlayer(increment);
    }

    // Relevant getters
    public Actor[] getActors() {
        return actors;
    }

    public int getPlayerNum(Actor player) {
        for (int i = 1; i < actors.length; i++) {
            if (actors[i].equals(player)) {
                return i;
            }
        }

        return 0;
    }

    public int getCurrentPosition(int player) {
        return ((Player) actors[player - 1]).getCurrentPosition();
    }

    public int getPreviousPosition(int player) {
        return ((Player) actors[player - 1]).getPreviousPosition();
    }

    //Relevant setters
    public void setCurrentPosition(int player, int newPosition) {
        ((Player) actors[player - 1]).setCurrentPosition(newPosition);
    }
}
