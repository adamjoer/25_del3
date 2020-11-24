package game;

public class Start extends Field {

    private final int reward;

    public Start(String title, String subText, String description, int position, int reward) {
        super(title, subText, description, position);
        this.reward = reward;
    }

    public boolean hasPassed(int prevPos, int curPos) {
        return false;
    }

    public int getReward() {
        return reward;
    }
}
