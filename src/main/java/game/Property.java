package game;

import java.awt.*;

public class Property extends Field {

    // Attributes
    private final int value;
    private final Color color;
    private final int relatedPropertyPosition;
    private Actor owner;

    // Constructor
    public Property(String title, String subText, String description, int position, int value, Color color, int relatedPropertyPosition) {
        super(title, subText, description, position);
        this.value = value;
        this.color = color;
        this.relatedPropertyPosition = relatedPropertyPosition;
    }

    public boolean sellProperty(Actor buyer) {
        if (buyer.equals(owner)) {
            return false;
        }

        // Make transaction and check if it went through
        if (!buyer.makeTransaction(owner, value)) {
            return false;
        }

        // Assign new owner
        owner = buyer;
        return true;
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
