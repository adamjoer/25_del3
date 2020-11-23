package game;

public class Field {
    // Variables
    final private String name;
    final private String flavorText;
    private int points;
    final private boolean extraTurn;

    // Constructor
    public Field(String iName, String iFlavorText, int iPoints, boolean iExtraTurn){
        name = iName;
        flavorText = iFlavorText;
        points = iPoints;
        extraTurn = iExtraTurn;
    }

    // Relevant setters
    public void setPoints (int add) { this.points = this.points + add; }

    // Relevant getters
    public String getName () { return this.name; }
    public String getFlavorText () { return this.flavorText; }
    public boolean getExtraTurn () { return this.extraTurn; }
    public int getPoints () { return this.points; }
}
