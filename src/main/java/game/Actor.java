package game;

public abstract class Actor {

    final protected Account account;

    public Actor(int startBalance)
    {
        account = new Account(startBalance);
    }

    public int getBalance()
    {
        return this.account.getBalance();
    }


    public void setBalance(int newBalance)
    {
        this.account.setBalance(newBalance);
    }


    // Make transaction on the account - and prevent negative balance. Returns true if transaction is successful.
    public boolean makeTransaction(Actor reciever, int amount)
    {
        int currentBalance = getBalance();
        int recieverBalance = reciever.getBalance();
        if (currentBalance < amount)
        {
            return false;
        }
        else
        {
            currentBalance -= amount;
            setBalance(currentBalance);
            recieverBalance += amount;
            reciever.setBalance(currentBalance);
            return true;
        }
}
