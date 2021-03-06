import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.*;
import static org.w3c.dom.Node.ELEMENT_NODE;

/**
 * Created by Jakub on 22.05.2017.
 */


public class TicketController {

    public TicketController() {
        super();
    }

    public String controlTrain(Node train, Node tickets) throws SAXException {

        /*SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new File("bilet.xsd"));
        Schema schema = factory.newSchema(schemaFile);
        Validator validator = schema.newValidator();

        try {
            validator.validate(new DOMSource(tickets));
        } catch (SAXException e) {
            return "INVALID - UNPARSED WITH SCHEMA";
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        ListMultimap<String, String> trainMap = ArrayListMultimap.create();

        Node lawnMower = train.getFirstChild();
        NodeList trainList = lawnMower.getChildNodes();
        for (int i = 0; i < trainList.getLength(); i++) {
            if (trainList.item(i).getNodeType() == ELEMENT_NODE & Objects.equals(trainList.item(i).getNodeName(), "odcinek")) {

                Node xx = trainList.item(i);
                NodeList odc = xx.getChildNodes();
                String nazwa = null;

                for (int j = 0; j < odc.getLength(); j++){
                    String miejsce = null;

                    if (odc.item(j).getNodeType() == ELEMENT_NODE & Objects.equals(odc.item(j).getNodeName(), "nazwa")){
                        nazwa = odc.item(j).getFirstChild().getNodeValue();
                    }

                    if (odc.item(j).getNodeType() == ELEMENT_NODE & Objects.equals(odc.item(j).getNodeName(), "miejsce")){
                        miejsce = odc.item(j).getFirstChild().getNextSibling().getFirstChild().getNodeValue();
                    }

                    if (nazwa != null & miejsce != null){
                        trainMap.put(nazwa, miejsce);
                    }

                }
            }
        }

        Node work = tickets.getFirstChild();
        NodeList ticketsList = work.getChildNodes();
        for (int i = 0; i < ticketsList.getLength(); i++){
            if (ticketsList.item(i).getNodeType() == ELEMENT_NODE & Objects.equals(ticketsList.item(i).getNodeName(), "bilet")) {

                Node bilet = ticketsList.item(i);
                NodeList biletO = bilet.getChildNodes();
                String miejsce = null;

                for (int j = 0; j < biletO.getLength(); j++){

                    if (biletO.item(j).getNodeType() == ELEMENT_NODE & Objects.equals(biletO.item(j).getNodeName(), "miejsce")){
                        miejsce = biletO.item(j).getFirstChild().getNodeValue();
                    }

                    if (biletO.item(j).getNodeType() == ELEMENT_NODE & Objects.equals(biletO.item(j).getNodeName(), "odcinki")){
                        Node odcinki = biletO.item(j);
                        NodeList odList = odcinki.getChildNodes();
                        for (int k = 0; k < odList.getLength(); k++){
                            if (odList.item(k).getNodeType() == ELEMENT_NODE & Objects.equals(odList.item(k).getNodeName(), "odcinek")){
                                String odcinek = odList.item(k).getFirstChild().getNodeValue();
                                if (odcinek != null & miejsce != null){
                                    if (trainMap.containsEntry(odcinek, miejsce)){
                                        trainMap.remove(odcinek, miejsce);
                                    } else {
                                        return "INVALID";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "VALID";
    }
}
