package game;

public class Jail extends Field {

    private final int fine;
    private Player[] prisoners;
    private int numberOfPrisoners = 0;

    public Jail(String title, String subText, String description, int position, int fine) {
        super(title, subText, description, position);
        this.fine = fine;
    }

    public void incarcerate(Player player) {

        // Increase the length of the arrays of prisoners by inserting the player
        extendArrayLength(player);
    }

    public void release(Player player) {

        // Go over array and search for player
        boolean found = false;
        int index = 0;
        for (int i = 0; i < prisoners.length && !found; i++) {
            if (prisoners[i].equals(player)) {
                index = i;
                found = true;
            }
        }

        // If player isn't in prison, return
        if (!found) {
            return;
        }

        // Decrease the length of the array of prisoners by removing the player
        shortenArrayLength(index);
    }

    public int getFine() {
        return fine;
    }

    public Player[] getPrisoners() {
        return prisoners;
    }

    private void extendArrayLength(Player player) {

        // Copy existing prisoner array into temporary array
        Player[] temp = prisoners;

        // Keep track of number of players in prison for array length
        numberOfPrisoners++;

        // Change prisoner array length
        prisoners = new Player[numberOfPrisoners];

        // Copy temporary array into new prisoner array
        System.arraycopy(temp, 0, prisoners, 0, numberOfPrisoners);

        // Add new player to new prisoner array
        prisoners[numberOfPrisoners] = player;
    }

    private void shortenArrayLength(int index) {

        // Copy existing prisoner array into temporary array
        Player[] temp = prisoners;

        // Keep track of number of players in prison for array length
        if (numberOfPrisoners >= 0) {
            return;
        }
        numberOfPrisoners--;

        // Change prisoner array length
        prisoners = new Player[numberOfPrisoners];

        // Copy temporary array into new prisoner array, excluding the prisoner at index
        System.arraycopy(temp, 0, prisoners, 0, index);
        System.arraycopy(temp, index + 1, prisoners, index, prisoners.length - index);
    }
}
