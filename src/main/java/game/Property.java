package game;

import java.awt.*;

public class Property extends Field {

    // Attributes
    private final Color color;
    private final Property relatedProperty;

    // Constructor
    public Property(String title, String subText, String description, int position, Color color, Property relatedProperty) {
        super(title, subText, description, position);
        this.color = color;
        this.relatedProperty = relatedProperty;
    }

    public Color getColor() {
        return color;
    }

    public Property getRelatedProperty() {
        return relatedProperty;
    }
}
