package game;

import game.actor.Player;
import game.field.Field;
import gui_fields.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GUIControllerTest {

    Player player1 = new Player(1000, "Jens");
    Player player2 = new Player(1000, "Bruh");
    Player[] players = new Player[]{player1,player2};

    @Test
    void testFieldCreation(){
        Field[] fields = Utility.fieldGenerator("src/main/resources/fieldList.xml");
        GUIController gct = new GUIController(fields, 24);
        gct.addPlayers(players);
        GUI_Field[] guiFields = gct.getGuiFields();
        for(int i=0; i<guiFields.length; i++){
            assertEquals(fields[i].getTitle(),guiFields[i].getTitle());
            assertEquals(fields[i].getSubText(),guiFields[i].getSubText());
            assertEquals(fields[i].getDescription(),guiFields[i].getDescription());
        }
        gct.close();
    }
}
