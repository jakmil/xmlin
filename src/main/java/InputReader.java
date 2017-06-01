import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static org.w3c.dom.Node.ELEMENT_NODE;

/**
 * Created by Jakub on 22.05.2017.
 */
public class InputReader {


    public InputReader() {
        super();
    }

    public Node readInputTicket(String input) throws Exception {

        DocumentBuilderFactory dbf = saDocumentBuilderFactory("bilet.xsd").newInstance();

        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = db.parse(new File(input));
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e2){
            e2.printStackTrace();
        }
        return doc;
    }

    public Node readInputTrain(String input){

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

        return doc;
    }

    private static DocumentBuilderFactory saDocumentBuilderFactory(String xsd) throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(new File(xsd)));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setValidating(true);
        dbf.setSchema(schema);
        return dbf;
    }
}
