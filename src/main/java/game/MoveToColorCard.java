package game;

public class MoveToColorCard extends ChanceCard{

    private final String COLOR;

    public MoveToColorCard(String CARD_TEXT, String color){
        super(CARD_TEXT);
        COLOR = color;
    }


    public String getColor(){
        return COLOR;
    }


}