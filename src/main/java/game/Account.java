package game;

public class Account {
    // Variables
    private int balance;

    // Constructor, sets starting balance
    public Account (int startBalance) { balance = startBalance; }

    // Get balance
    public int getBalance () { return this.balance; }

    // Set balance
    public void setBalance (int newBalance) { this.balance = newBalance; }

    // Make transaction on the account - and prevent negative balance. Returns true if transaction is successful.
    public boolean makeTransaction (int value) {
        if (this.balance >= -value) {
            this.balance += value;
            return true;
        }
        else {
            System.out.println("There is an insufficient balance on the account to make the transaction.");
            return false;
        }
    }

}
