package game;

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
}
