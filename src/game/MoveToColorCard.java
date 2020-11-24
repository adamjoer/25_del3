package game;

public class MoveToColorCard extends ChanceCard{

    private final String COLOR;

    public MoveToColorCard(String CARD_TEXT, String color){
        super(CARD_TEXT);
        this.COLOR = color;
    }

    public String getCardText() {
        return CARD_TEXT;
    }

    public String getColor(){
        return COLOR;
    }


}
