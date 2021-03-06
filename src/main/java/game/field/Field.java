package game.field;


public abstract class Field {

    // Variables
    final protected String title;
    final protected String subText;
    final protected String description;
    final protected int position;

    // Constructor
    public Field(String title, String subText, String description, int position) {
        this.title = title;
        this.subText = subText;
        this.description = description;
        this.position = position;
    }

    public String getField() {
        return getClass().getSimpleName();
    }

    public String getTitle() {
        return title;
    }

    public String getSubText() {
        return subText;
    }

    public String getDescription() {
        return description;
    }

    public int getPosition() {
        return position;
    }

    public String toString() {

        return String.format("%s\n\t[title=%s]", getField(), getTitle()) +
               String.format("\n\t[subText=%s]", getSubText()) +
               String.format("\n\t[description=%s]", getDescription()) +
               String.format("\n\t[position=%d]", getPosition());
    }
}
