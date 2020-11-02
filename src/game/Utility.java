package game;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class Utility {
    public static void awaitEnter(){
        System.out.println("Press enter to roll the dice...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Tile[] tileGenerator(String filePath){
        Tile[] tileArr = new Tile[12];
        try {
            // Load XML file and make a list of tile elements.
            File tileXML = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document tileDoc = dBuilder.parse(tileXML);
            tileDoc.getDocumentElement().normalize();
            NodeList tileList = tileDoc.getElementsByTagName("tile");


            // Extract data from each tileList element and create Tile objects for the Tile[].
            for (int i = 0; i < tileList.getLength(); i++) {
                Node tile = tileList.item(i);

                if (tile.getNodeType() == Node.ELEMENT_NODE) {
                    Element ele = (Element) tile;
                    String name = ele.getElementsByTagName("name").item(0).getTextContent();
                    String flavor = ele.getElementsByTagName("flavor").item(0).getTextContent();
                    int value = Integer.parseInt(ele.getElementsByTagName("value").item(0).getTextContent());
                    boolean extraTurn = Boolean.parseBoolean(ele.getElementsByTagName("extraturn").item(0).getTextContent());
                    tileArr[i] = new Tile(name,flavor,value,extraTurn);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tileArr;
    }
}
