package game;

public class Account {
    // Variables
    private int balance;

    /**
     * @param startBalance
     */
    // Constructor, sets starting balance
    public Account(int startBalance)
    {
        balance = startBalance;
    }

    // Get balance
    public int getBalance()
    {
        return this.balance;
    }

    // Set balance
    public void setBalance(int newBalance)
    {
        this.balance = newBalance;
    }

}
