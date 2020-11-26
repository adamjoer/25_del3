package game;

public class ActorController
{
    //Defining variables
    private final int numberPlayer;
    private final Actor[] actors;
    private final int startBalance;
    private final int bankBalance = 90;
    //Momentary name
    private String name;


    /**
     * @param numberPlayer
     */
    public ActorController(int numberPlayer)
    {
        //Defining number of players.
        this.numberPlayer = numberPlayer;
        actors = new Actor[numberPlayer + 1];

        //Calculating each players starting balance, based on the number of players
        if (numberPlayer == 4)
         {
            startBalance = 16;
         }
        else if (numberPlayer == 3)
        {
            startBalance = 18;
        }
        else {
            startBalance = 20;
        }
        //Assigning player names and bank with their starting balance
        actors[0] = new Bank(bankBalance);
        for (int i = 1; i < numberPlayer; i++)
        {
            actors[i] = new Player(startBalance, name);
        }
    }


    /**
     * @param sender
     * @param receiver
     * @param amount
     * @return
     */
    //calls method to make transactions
    public boolean makeTransaction (int sender, int receiver, int amount) {
        return actors[sender].makeTransaction(actors[receiver], amount);
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
