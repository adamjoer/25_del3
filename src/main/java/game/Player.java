package game;

public class Player extends Actor{
    //Variable
    final private String name;
    final private Account account;
    private int position = -1;


    //Constructor: Defining variables
    public Player(String inputName, int startBalance) {
        account = new Account(startBalance);
        name = inputName;
    }

    // Relevant setters
    public int getBalance() { return this.account.getBalance(); }
    public String getName() { return this.name; }
    public int getPosition() { return this.position; }

    // Relevant setters
    public void setBalance(int newBalance) { this.account.setBalance(newBalance); }
    public void setPosition(int newPosition) { this.position = newPosition; }

    // Attempt to make a transaction, returns true if successful.
    public boolean makeTransaction(int points) { return this.account.makeTransaction(points); }
}
