package game.field;

import java.awt.*;

public class Property extends Field {

    // Attributes
    private final int value;
    private final Color color;
    private final int relatedPropertyPosition;
    private int owner = 0;

    // Constructor
    public Property(String title, String subText, String description, int position, int value, Color color, int relatedPropertyPosition) {
        super(title, subText, description, position);
        this.value = value;
        this.color = color;
        this.relatedPropertyPosition = relatedPropertyPosition;
    }

    // Relevant getters
    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getRelatedPropertyPosition() {
        return relatedPropertyPosition;
    }

    public int getOwner() {
        return owner;
    }

    // Relevant setters
    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String toString() {
        return super.toString() +
               String.format("\n\t[value=%d]", getValue()) +
               String.format("\n\t[color=%s]", getColor().toString()) +
               String.format("\n\t[relatedPropertyPosition=%d]", getRelatedPropertyPosition()) +
               String.format("\n\t[owner=%d]", getOwner());
    }
}
