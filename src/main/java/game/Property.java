package game;

import java.awt.*;

public class Property extends Field {

    // Attributes
    private final Color color;
    private final int relatedPropertyPosition;
    private Actor owner;

    // Constructor
    public Property(String title, String subText, String description, int position, Color color, int relatedPropertyPosition) {
        super(title, subText, description, position);
        this.color = color;
        this.relatedPropertyPosition = relatedPropertyPosition;
        // Set the default owner to be the bank, when property is purchased, players buy it from the bank
    }

    public void sellProperty(Actor buyer) {

    }

    public Color getColor() {
        return color;
    }

    public int getRelatedPropertyPosition() {
        return relatedPropertyPosition;
    }

    public Actor getOwner() {
        return owner;
    }

    public void setOwner(Actor owner) {
        this.owner = owner;
    }
}
