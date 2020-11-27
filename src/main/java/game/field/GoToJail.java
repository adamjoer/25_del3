package game.field;

public class GoToJail extends Field {

    private int jailPosition;

    public GoToJail(String title, String subText, String description, int position, int jailPosition) {
        super(title, subText, description, position);
        this.jailPosition = jailPosition;
    }

    public int getJailPosition() {
        return jailPosition;
    }

    public String toString() {
        return super.toString() + String.format("\n\t[jailPosition=%d]", getJailPosition());
    }
}
