package game;

import java.awt.*;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.*;

public class Utility {
    public static void awaitEnter(){
        System.out.println("Press enter to roll the dice...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Field[] fieldGenerator(String filePath){
        Field[] fieldArr = new Field[12];
        try {
            // Load XML file and make a list of field elements.
            File fieldXML = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document fieldDoc = dBuilder.parse(fieldXML);
            fieldDoc.getDocumentElement().normalize();
            NodeList fieldList = fieldDoc.getElementsByTagName("field");


            // Extract data from each tileList element and create Field objects for the Field[].
            for (int i = 0; i < fieldList.getLength(); i++) {
                Node field = fieldList.item(i);

                if (field.getNodeType() == Node.ELEMENT_NODE) {
                    Element ele = (Element) field;
                    String name = ele.getElementsByTagName("name").item(0).getTextContent();
                    String flavor = ele.getElementsByTagName("flavor").item(0).getTextContent();
                    int value = Integer.parseInt(ele.getElementsByTagName("value").item(0).getTextContent());
                    boolean extraTurn = Boolean.parseBoolean(ele.getElementsByTagName("extraturn").item(0).getTextContent());
                    fieldArr[i] = new Field(name,flavor,value,extraTurn);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldArr;
    }


    public static ChanceCard[] chanceCardGenerator(String filePath){
        ChanceCard[] chanceCards = new ChanceCard[20];

        try {
            // Load XML file and make a list of field elements.
            File fieldXML = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document fieldDoc = dBuilder.parse(fieldXML);
            fieldDoc.getDocumentElement().normalize();
            NodeList cardList = fieldDoc.getElementsByTagName("chancecard");


            // Extract data from each tileList element and create Field objects for the Field[].
            for (int i = 0; i < cardList.getLength(); i++) {
                Node field = cardList.item(i);

                if (field.getNodeType() == Node.ELEMENT_NODE) {
                    Element ele = (Element) field;
                    String cardType = ele.getElementsByTagName("cardType").item(0).getTextContent();
                    int cardNum = Integer.parseInt(ele.getElementsByTagName("number").item(0).getTextContent());
                    String cardText = Files.readAllLines(Paths.get("src/main/resources/chanceCardTexts.txt")).get(2+((cardNum - 1) * 3));

                    switch (cardType) {
                        case "HeldCard":
                            chanceCards[i] = new HeldCard(cardText);

                            break;

                        case "MoveToColorCard":
                            String color = ele.getElementsByTagName("color").item(0).getTextContent();

                            chanceCards[i] = new MoveToColorCard(cardText, Color.getColor(color));

                            break;

                        case "TargetedCard":
                            int targetPlayer = Integer.parseInt(ele.getElementsByTagName("targetPlayer").item(0).getTextContent());

                            chanceCards[i] = new TargetedCard(cardText, targetPlayer);
                            break;

                        case "StandardCard":
                            Boolean fine = Boolean.parseBoolean(ele.getElementsByTagName("fine").item(0).getTextContent());
                            Boolean gift = Boolean.parseBoolean(ele.getElementsByTagName("gift").item(0).getTextContent());
                            Boolean move = Boolean.parseBoolean(ele.getElementsByTagName("moveDestination").item(0).getTextContent());
                            int amount = 0, moveDestination = 0;

                            if(fine){
                                amount = Integer.parseInt(ele.getElementsByTagName("amount").item(0).getTextContent());
                            }
                            else if(gift){
                                amount = Integer.parseInt(ele.getElementsByTagName("amount").item(0).getTextContent());
                            }
                            else if(move){
                                moveDestination = Integer.parseInt(ele.getElementsByTagName("moveTo").item(0).getTextContent());

                                chanceCards[i] = new StandardCard(cardText, fine, gift, move, moveDestination, amount);
                            }
                            else{
                                chanceCards[i] = new StandardCard(cardText, fine, gift, move, moveDestination, amount);
                            }
                            break;

                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chanceCards;
    }
}
