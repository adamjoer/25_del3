package game;


public abstract class Field {

    // Variables
    final private String title;
    final private String subText;
    final private String description;
    final private int position;

    // Constructor
    public Field(String title, String subText, String description, int position) {
        this.title = title;
        this.subText = subText;
        this.description = description;
        this.position = position;
    }

    public String getField(){
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

}
