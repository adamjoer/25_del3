package game.field;

public class Jail extends Field {

    private final int bail;
    private final int goToJailPosition;

    public Jail(String title, String subText, String description, int position, int fine, int goToJailPosition) {
        super(title, subText, description, position);
        this.bail = fine;
        this.goToJailPosition = goToJailPosition;
    }

    public int getBail() {
        return bail;
    }

    public int getGoToJailPosition() {
        return goToJailPosition;
    }

    public String toString() {

        return super.toString() +
               String.format("\n\t[bail=%d]", getBail()) +
               String.format("\n\t[goToJailPosition=%d]", getGoToJailPosition());
    }
}
