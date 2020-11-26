package game;

public class ActorController
{
    //Defining variables

    private final Actor[] actors;
    private final int startBalance;
    private final int bankBalance = 90;
    private final String[] playerNames;
    //Momentary name
    private String name;


    /**
     * @param playerNames
     */
    public ActorController(String[] playerNames)
    {

        //Defining number of players.
        this.playerNames = playerNames;
        actors = new Actor[playerNames.length + 1];

        //Calculating each players starting balance, based on the number of players
        if (playerNames.length == 4)
         {
            startBalance = 16;
         }
        else if (playerNames.length == 3)
        {
            startBalance = 18;
        }
        else {
            startBalance = 20;
        }
        //Assigning player names and bank with their starting balance
        actors[0] = new Bank(bankBalance);
        for (int i = 1; i < actors.length; i++)
            {
                actors[i] = new Player(startBalance, playerNames[i]);
        }
    }


    /**
     * @param sender
     * @param receiver
     * @param amount
     * @return
     */
    //makes transaction between two actors and returns false if senders balance is below amount
    public boolean makeTransaction (int sender, int receiver, int amount) {
        if(actors[sender].makeTransaction(-amount) && amount <91){
            actors[receiver].makeTransaction(amount);
            return true;
        }

        else { return false;}
    }


    /**
     * @param Player
     * @param Increment
     */
    //Calls Player class method with actor array
    public void movePlayer(int Player, int Increment)
    {
        ((Player) actors[Player]).movePlayer(Increment);
    }

    // Relevant getters
    public Actor[] getActors() {
        return actors;
    }

    public int getCurrentPosition(int player) {
        return ((Player) actors[player]).getCurrentPosition();
    }

    public int getPreviousPosition(int player) {
        return ((Player) actors[player]).getPreviousPosition();
    }


    //Relevant setters
    public void setCurrentPosition(int player, int newPosition) {
        ((Player) actors[player]).setCurrentPosition(newPosition);
    }
}
