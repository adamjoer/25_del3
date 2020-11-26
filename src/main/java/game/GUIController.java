package game;

import gui_fields.*;
import gui_main.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GUIController {


    private static final GUI gui = new GUI();
    private final List<GUI_Player> guiPlayerList = new ArrayList<>();
    private final List<String> playerNames = new ArrayList<>();

    public GUIController(){

        /*boolean checkPlayerCount = addPlayers(players);
        if (!checkPlayerCount){
            String userBtn = getUserButton("The maximum amount of players have been reach, do you want to:",
                                            "Close game", "Continue");
            if (userBtn.equals("Close game")){
                close();
            }
        }*/
    }

    //This part is ONLY for test
    public static void main(String[] args) {
        GUIController gct = new GUIController();
        gct.askForPlayerNames();
        List<String> playerNames = gct.returnPlayerNames();
        Player[] playerList = new Player[playerNames.size()];

        for(int i=0; i< gct.returnPlayerNames().size(); i++){
            Player player = new Player(playerNames.get(i), 1000);
            playerList[i] = player;
        }

        gct.addPlayers(playerList);

    }

    /**
     * Places buttons on the board with a message, and wait for the button pressed
     * @param msg : The message that the player will see
     * @param btn1 : The first btn a text string, that the player will see on the btn
     * @param btn2 : The 2nd btn a text string, that the player will see on the btn
     * @return returns a text string with the button pressed
     */
    public String getUserButton(String msg, String btn1, String btn2){
        return gui.getUserButtonPressed(
                msg,
                btn1, btn2
        );
    }

    /**
     * laces buttons on the board with a message, and wait for the button pressed
     * @param msg : The message that the player will see
     * @param btnLeft : If the player presses this button, the method will return true
     * @param btnRight : If the player presses this button, the method will return false
     * @return boolean
     */
    public boolean getUserLeftButtonPressed(String msg, String btnLeft, String btnRight){
        return gui.getUserLeftButtonPressed(msg, btnLeft, btnRight);
    }

    /**
     * Ask the player to write something and returns it
     * @param msg : The message that the player will see
     * @return the text what the player writes
     */
    public String getUserString(String msg){
        return gui.getUserString(msg);
    }

    /**
     * Ask the players for their names on the board.
     * Checks for the max. and min. amount of players.
     * Checks also if the name is already in use.
     */
    public void askForPlayerNames(){
        String userInputName;
        while(true) {
            boolean btnPressed = getUserLeftButtonPressed("Opret ny spiller", "Ja", "Nej");
            if(btnPressed) {
                if (playerNames.size() >= 4) {
                    String userBtn = getUserButton("The maximum amount of players have been reach, do you want to:",
                            "Close game", "Continue");
                    if (userBtn.equals("Close game")) {
                        close();
                    }
                    break;
                } else {
                    userInputName = getUserString("Skriv dit navn her").toLowerCase();
                    userInputName = userInputName.substring(0,1).toUpperCase() + userInputName.substring(1);
                    if(playerNames.contains(userInputName)){
                        showMessage("Det navn er allerede taget!");
                    } else {
                        playerNames.add(userInputName);
                    }
                }
            } else{
                if(playerNames.size() <2){
                    showMessage("Der skal mindst være 2 spillere for, at man kan starte spillet.");
                } else{
                    break;
                }
            }
        }
    }

    public List<String> returnPlayerNames(){
        return playerNames;
    }

    /**
     *  Adds all players to the board
     * @param players : players needs to be created before sending to GUI controller
     * @return true if the players are created otherwise false, can also return false if the player array size is over 4
     */
    public boolean addPlayers(Player[] players){
        boolean playerCheck = false;
        if (players.length > 4 || players.length < 2){
            return false;
        }

        for(Player p : players){
            GUI_Player player = new GUI_Player(p.getName(),p.getBalance());
            playerCheck = gui.addPlayer(player);
            guiPlayerList.add(player);
        }
        return playerCheck;
    }

    /**
     * Sets the balance for a player on the GUI
     * @param player : a player from the game
     * @param balance : the new balance which the player gets
     */
    public void setPlayerBalance(Player player, int balance){
        Objects.requireNonNull(getGuiPlayer(player)).setBalance(balance);
    }

    /**
     * Set the visibility of a players car
     * @param player : A player from the game
     * @param visibility : Whether the car is shown on the board or not
     */
    public void setCar(Player player, boolean visibility, int fieldPlacement){
        gui.getFields()[fieldPlacement].setCar(getGuiPlayer(player), visibility);
    }

    /**
     * Moves a players car
     * @param player : A player from the game
     * @param fieldPlacement : Position of the player (array position) not actual position
     * @param newFieldPlacement : The position where the player should be moved to (array position)
     */
    public void setCarPlacement(Player player, int fieldPlacement, int newFieldPlacement){
        gui.getFields()[fieldPlacement].setCar(getGuiPlayer(player),false);
        gui.getFields()[newFieldPlacement].setCar(getGuiPlayer(player), true);
    }

    /**
     * Displays two dice on the board with the given values and a fixed rotation,
     * at a random position on the board.
     * If the dice value is not between 1-6, the dice won't be displayed.
     * The method replaces the existing die/dice.
     *
     * @param faceValue1 : Value of first dice int [1:6]
     * @param rotation1 : Rotation of the first die, in degrees int [0:360]
     * @param faceValue2 : Value of second dice int [1:6]
     * @param rotation2 :  Rotation of the first die, in degrees int [0:360]
     */
    public void setDiceGui(int faceValue1, int rotation1, int faceValue2, int rotation2){
        gui.setDice(faceValue1, rotation1, faceValue2, rotation2);
    }

    public void setDiceGui(int faceValue1, int faceValue2){
        gui.setDice(faceValue1, faceValue2);
    }

    /**
     * Sets a field to be owned by a player, can be done for the following types of fields:
     *      - GUI_Street
     *      - GUI_Brewery
     *      - GUI_Shipping
     * @param subtext : Subtext on field
     * @param playerName : Name of the player who owns the field
     * @param rent : Rent of the field
     * @param fieldColor : Change of color to indicate change of ownership
     * @param fieldPosition : The position of the field that changes ownership (array position), not actual position
     */
    public void fieldOwnable(String subtext, String playerName, int rent, Color fieldColor, int fieldPosition){
        GUI_Ownable ownable = (GUI_Ownable) gui.getFields()[fieldPosition];
        ownable.setSubText(subtext);
        ownable.setOwnerName(playerName);
        ownable.setRent(Integer.toString(rent));
        ownable.setBackGroundColor(fieldColor);
    }

    /**
     * Removes ownership of field
     * @param fieldPosition : The position of the field (array position), not actual position
     */
    public void fieldOwnable(int fieldPosition){
        GUI_Ownable ownable = (GUI_Ownable) gui.getFields()[fieldPosition];
        ownable.setOwnerName(null);
    }

    /**
     * Sets houses or a hotel on a street.
     *      - The max number of houses is 4, so if number is >4 it will just be set to 4 houses
     * @param fieldPosition : The position of the field (array position), not actual position
     * @param houseAmount : Amount of houses to be put on the field
     * @param setHotel : Boolean (true if hotel should be on the street otherwise false)
     */
    public void setHouseOrHotelStreet(int fieldPosition, int houseAmount, boolean setHotel){
        GUI_Street street = (GUI_Street) gui.getFields()[fieldPosition];
        if(houseAmount>4){
            houseAmount = 4;
        }
        street.setHouses(houseAmount);
        street.setHotel(setHotel);
    }

    /**
     * Displays a message to the user, along with an 'OK'-button.
     * The program stops/hangs at the method call until the button
     * is pressed.
     *
     * @param msg The message to print
     */
    public void showMessage(String msg){
        gui.showMessage(msg);
    }

    /**
     * Sets the text for a chance card and shows it on the board
     * @param msg : Text that will be seen on the chance card
     */
    public void displayChanceCard(String msg){
        gui.displayChanceCard(msg);
    }

    /**
     * Display the current chance card text in the center
     */
    public void displayChanceCard(){
        gui.displayChanceCard();
    }

    /**
     * Closes the GUI window.
     */
    public void close(){
        gui.close();
    }

    /**
     * Takes a player from the game and retrieves it from board
     * @param player : a player from the main game
     * @return a GUI_Player
     */
    private GUI_Player getGuiPlayer(Player player){

        for(GUI_Player p : guiPlayerList){
            if (player.getName().equals(p.getName())){
                return p;
            }
        }
        return null;
    }
}
