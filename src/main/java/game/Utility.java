package game;

import java.awt.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


public class Utility {
    /**
     * Generates an array of Fields from an XML file.
     * @param filePath  Filepath of the XML file with Field data.
     * @return  Field[] for the game.
     */
    public static Field[] fieldGenerator(String filePath){
        // Load XML and get a list of field data.
        NodeList fieldList = getXmlContent(filePath,"field");
        Field[] fieldArr = new Field[fieldList.getLength()];
        try {

            // Extract data from each fieldList element and create Field objects for the Field[].
            for (int i = 0; i < fieldList.getLength(); i++) {

                Node field = fieldList.item(i);

                if (field.getNodeType() == Node.ELEMENT_NODE) {

                    Element ele = (Element) field;
                    String fieldType = getString(ele,"fieldType");
                    String title = getString(ele, "title");
                    String subText = getString(ele, "subText");
                    String description = getString(ele, "description");

                    switch (fieldType){
                        case "Property":
                            Color color = Color.getColor(getString(ele,"color"));
                            int value = getInt(ele,"value");
                            int relatedPropertyPosition = getInt(ele, "relatedPropertyPosition");

                            fieldArr[i] = new Property(title,subText,description,i,value,color,relatedPropertyPosition);

                            break;
                        case "Chance":
                            fieldArr[i] = new Chance(title,subText,description,i);

                            break;
                        case "Jail":
                            int fine = getInt(ele,"fine");

                            fieldArr[i] = new Jail(title,subText,description,i,fine);

                            break;
                        case "GoToJail":
                            int jailPosition = getInt(ele,"jailPosition");

                            fieldArr[i] = new GoToJail(title,subText,description,i,jailPosition);

                            break;
                        case "ParkingLot":
                            fieldArr[i] = new ParkingLot(title,subText,description,i);

                            break;
                        case "Start":
                            int reward = getInt(ele,"reward");

                            fieldArr[i] = new Start(title,subText,description,i,reward);

                            break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldArr;
    }

    /**
     * Generates an array of chanceCards from an XML file.
     * @param filePath Filepath of the XML file.
     * @return A ChanceCard[] with the given information.
     */

    public static ChanceCard[] chanceCardGenerator(String filePath){
        // Load XML and extract a list of chanceCard data.
        NodeList cardList = getXmlContent(filePath,"chanceCard");
        ChanceCard[] chanceCards = new ChanceCard[cardList.getLength()];

        try {
            // Extract data from each tileList element and create Field objects for the Field[].
            for (int i = 0; i < cardList.getLength(); i++) {
                Node card = cardList.item(i);

                if (card.getNodeType() == Node.ELEMENT_NODE) {
                    Element ele = (Element) card;
                    String cardType = getString(ele,"cardType");
                    String cardText = getString(ele, "cardText");

                    switch (cardType) {
                        case "HeldCard":
                            chanceCards[i] = new HeldCard(cardText);

                            break;

                        case "MoveToColorCard":
                            String color = getString(ele, "color");

                            chanceCards[i] = new MoveToColorCard(cardText, Color.getColor(color));

                            break;

                        case "TargetedCard":
                            int targetPlayer = getInt(ele, "targetPlayer");
                            chanceCards[i] = new TargetedCard(cardText, targetPlayer);
                            break;

                        case "StandardCard":
                            String standardType = getString(ele,"standardType");
                            int amount = 0, moveDestination = 0, increment = 0;

                            switch (standardType){
                                case ("moveDestination"):
                                    moveDestination = getInt(ele,"moveDestination");

                                    break;
                                case ("moveIncrement"):
                                    increment = getInt(ele,"increment");

                                    break;
                                default:
                                    amount = getInt(ele,"amount");
                            }

                            chanceCards[i] = new StandardCard(cardText, standardType, moveDestination, increment, amount);

                            break;
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chanceCards;
    }

    /**
     * Extracts a boolean from an XML element.
     * @param ele An XML element extracted from a document.
     * @param tag The XML tag to extract a boolean value from.
     * @return The boolean requested.
     */
    /*
    private static boolean getBool (Element ele, String tag){
        boolean bool = false;
        try{
            bool = Boolean.parseBoolean(ele.getElementsByTagName(tag).item(0).getTextContent());
        } catch (Exception e){
            e.printStackTrace();
        }
        return bool;
    }
    */
    /**
     * Extracts a String from an XML element.
     * @param ele An XML element extracted from a document.
     * @param tag The XML tag to extract a String from.
     * @return The String requested.
     */
    private static String getString (Element ele, String tag){
        String str = new String();
        try{
            str = ele.getElementsByTagName(tag).item(0).getTextContent();
        } catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

    /**
     * Extracts an integer from an XML element.
     * @param ele An XML element extracted from a document.
     * @param tag The XML tag to extract an integer value from.
     * @return The integer requested.
     */
    private static int getInt (Element ele, String tag){
        int n = Integer.MAX_VALUE;
        try{
            n = Integer.parseInt(ele.getElementsByTagName(tag).item(0).getTextContent());
        } catch (Exception e){
            e.printStackTrace();
        }
        return n;
    }

    /**
     * Imports data from an XML file to a NodeList and returns the NodeList.
     * @param filePath Path to the XML file to read.
     * @param mainTag The primary XML element tag.
     * @return A NodeList with each element in the XML file.
     */
    private static NodeList getXmlContent (String filePath, String mainTag){
        NodeList nodeList = new NodeList() {
            @Override
            public Node item(int index) {
                return null;
            }

            @Override
            public int getLength() {
                return 0;
            }
        };
        try{
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(file);
            document.getDocumentElement().normalize();
            nodeList = document.getElementsByTagName(mainTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodeList;
    }
}
