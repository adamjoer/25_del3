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
}
