package game;

import java.awt.*;

public class Property extends Field {

    // Attributes
    private final Color color;
    private final Property partnerProperty;

    // Constructor
    public Property(String title, String subText, String description, int position, Color color, Property partnerProperty) {
        super(title, subText, description, position);
        this.color = color;
        this.partnerProperty = partnerProperty;
    }

    public Color getColor() {
        return color;
    }

    public Property getPartnerProperty() {
        return partnerProperty;
    }
}
