package game;

import java.awt.*;

public class Property extends Field {

    // Attributes
    private final Color color;
    private final int relatedPropertyPosition;

    // Constructor
    public Property(String title, String subText, String description, int position, Color color, int relatedPropertyPosition) {
        super(title, subText, description, position);
        this.color = color;
        this.relatedPropertyPosition = relatedPropertyPosition;
    }

    public Color getColor() {
        return color;
    }

    public int getRelatedPropertyPosition() {
        return relatedPropertyPosition;
    }
}
