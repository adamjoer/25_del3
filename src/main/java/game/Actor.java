package game;

public abstract class Actor {

    final protected Account account;

    public Actor(int startBalance){
        account = new Account(startBalance);
    }

    public int getBalance() { return this.account.getBalance(); }


    public void setBalance(int newBalance) { this.account.setBalance(newBalance); }

    // Attempt to make a transaction, returns true if successful.
    public boolean makeTransaction(int points) { return this.account.makeTransaction(points); }
}
