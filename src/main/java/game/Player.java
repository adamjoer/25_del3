package game;

public class Player extends Actor {
    //Variable
    private final String name;
    private int currentPosition = 0;
    private int previousPosition = 0;

    //Constructor: Defining variables
    public Player(int startBalance, String name) {
        super(startBalance);
        this.name = name;
    }

    // Move player forwards specific number of steps
    public void movePlayer(int Increment) {
        previousPosition = currentPosition;
        currentPosition = (currentPosition + Increment) % 24;
    }

    // Relevant getters
    public String getName() {
        return name;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getPreviousPosition() {
        return previousPosition;
    }

    // Relevant setters
    public void setCurrentPosition(int position) {
        previousPosition = currentPosition;
        currentPosition = position;
    }
}
