package game;

public class Player extends Actor{
    //Variable
    final private String name;
    private int position = 0;


    //Constructor: Defining variables
    public Player(int startBalance, String name) {
        super(startBalance);
        this.name = name;
    }

    // Relevant setters
    public String getName() { return this.name; }
    public int getPosition() { return this.position; }

    // Relevant setters
    public void setPosition(int newPosition) { this.position = newPosition; }

    


}
