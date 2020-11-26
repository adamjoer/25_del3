package game;



public abstract class Actor {

    final protected Account account;

    /**
     * @param startBalance
     */
    public Actor(int startBalance) {
        account = new Account(startBalance);
    }


    /**
     * @param amount
     * @return
     */
    // Make transaction on the account - and prevent negative balance. Returns true if transaction is successful.
    public boolean makeTransaction (int amount) {
        if (getBalance() >= -amount) {
            setBalance(getBalance() + amount);
            return true;
        }
        else {
            System.out.println("There is insufficient funds on the account to make the transaction.");
            return false;
        }
    }


    // Relevant getters
    public int getBalance() {
        return account.getBalance();
    }

    // Relevant setters
    public void setBalance(int newBalance) {
        account.setBalance(newBalance);
    }
}
