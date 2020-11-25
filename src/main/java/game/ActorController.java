package game;

public class ActorController
{
    //Defining variables
    private final int number_player;
    private final Actor[] actors;
    private final int startBalance;
    private final int BANK_BALANCE = 90;
    //Momentary name
    private String nametemp;

    public ActorController(int number_player)
    {
        //Defining number of players.
        this.number_player = number_player;
        actors = new Actor[number_player + 1];

        //Calculating each players starting balance, based on the number of players
        if (number_player == 4)
         {
            startBalance = 16;
         }
        else if (number_player == 3)
        {
            startBalance = 18;
        }
        else {
            startBalance = 20;
        }
        //Assigning player names with there starting balance
        actors[0] = new Bank(BANK_BALANCE);
        for (int i = 1; i < number_player; i++)
        {
            actors[i] = new Player(startBalance, nametemp);
        }
    }

    public boolean makeTransaction (int sender, int receiver, int amount) {
        return actors[sender].makeTransaction(actors[receiver], amount);
    }

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
