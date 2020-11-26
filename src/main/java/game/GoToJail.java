package game;

public class GoToJail extends Field {

    private int jailPosition;

    public GoToJail(String title, String subText, String description, int position, int jailPosition) {
        super(title, subText, description, position);
        this.jailPosition = jailPosition;
    }

    public int getJailPosition() {
        return jailPosition;
    }
}
