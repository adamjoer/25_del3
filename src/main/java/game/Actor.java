package game;

public abstract class Actor {

    final protected Account account;

    public Actor(int startBalance) {
        account = new Account(startBalance);
    }

    // Make transaction on the account - and prevent negative balance. Returns true if transaction is successful.
    public boolean makeTransaction(Actor receiver, int amount) {

        int senderBalance = getBalance();
        int receiverBalance = receiver.getBalance();

        if (senderBalance < amount) {
            return false;
        }

        senderBalance -= amount;
        setBalance(senderBalance);

        receiverBalance += amount;
        receiver.setBalance(receiverBalance);

        return true;
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
