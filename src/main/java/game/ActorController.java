package game;

public class ActorController
{
    //Defining variables
    private int number_player;
    private final Player[] players;
    private int startBalance;
    //Momentary name
    private String nametemp;

    public ActorController(int number_player)
    {

        this.number_player = number_player;
        players = new Player[number_player];

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
        for (int i = 0; i < number_player; i++)
        {
            players[i] = new Player(nametemp,startBalance);
        }
    }
    public void makeTransaction ()
    {

    }

}
