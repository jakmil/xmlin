package main;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Objects;

import static org.w3c.dom.Node.ELEMENT_NODE;

/**
 * Created by Jakub on 24.05.2017.
 */
public class InputReader {


    public InputReader() {
        super();
    }

    public Node readInput(String input){

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = db.parse(input);
        } catch (SAXException | IOException e1) {
            e1.printStackTrace();
        }

        Node lawnMower = doc.getFirstChild();
        NodeList nodeList = lawnMower.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == ELEMENT_NODE ) {
                Node questionNode = nodeList.item(i);

                System.out.println(questionNode.toString());
            }
        }
    return doc;
    }
}
