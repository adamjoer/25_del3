package game;

public class Start extends Field {

    private final int reward;

    public Start(String title, String subText, String description, int position, int reward) {
        super(title, subText, description, position);
        this.reward = reward;
    }

    public int getReward() {
        return reward;
    }

    public String toString() {
        return super.toString() + String.format("\n\t[reward=%d]", getReward());
    }
}
