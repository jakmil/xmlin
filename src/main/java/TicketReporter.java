import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Jakub on 22.05.2017.
 */

public class TicketReporter {

    public TicketReporter() {
        super();
    }

    public String makeReport(Node train, Node tickets) throws XPathExpressionException {
        String raport;

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        String tCnt = "count(/bilety/bilet)";
        XPathExpression expr = xpath.compile(tCnt);
        String count = expr.evaluate(tickets, XPathConstants.STRING).toString();
        int foo = Integer.parseInt(count);
        float rev = 0;
        int znz = 0;
        for (int i = 1; i < foo+1; i++){
            String cena = "/bilety/bilet[" + i + "]/cena";
            XPathExpression exprs = xpath.compile(cena);
            String counts = exprs.evaluate(tickets, XPathConstants.STRING).toString();

            String znizka = "/bilety/bilet[" + i + "]/wartoscZnizki";
            XPathExpression exprx = xpath.compile(znizka);
            String znizk = exprx.evaluate(tickets, XPathConstants.STRING).toString();

            float z = Float.parseFloat(znizk);
            float c = Float.parseFloat(counts);
            float countx = c * z;
            rev = rev + countx;

            if (z > 0 & z < 1){
                znz ++;
            }

        }

        try {

            DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = df.newDocumentBuilder();

            Document doc = db.newDocument();
            Element rootElement = doc.createElement("pociag");
            doc.appendChild(rootElement);

            String numero = "/bilety/bilet/trasa/numerPociagu";
            XPathExpression exprn = xpath.compile(numero);
            String numer = exprn.evaluate(tickets, XPathConstants.STRING).toString();

            Attr attr = doc.createAttribute("id");
            attr.setValue(numer);
            rootElement.setAttributeNode(attr);

            // raport elements
            Element report = doc.createElement("raport");
            rootElement.appendChild(report);

            Element przychod = doc.createElement("przychod");
            przychod.appendChild(doc.createTextNode(""+rev));
            report.appendChild(przychod);

            Element iloscPasazerow = doc.createElement("iloscPasazerow");
            iloscPasazerow.appendChild(doc.createTextNode(""+foo));
            report.appendChild(iloscPasazerow);

            Element iloscZnizek = doc.createElement("iloscZnizek");
            iloscZnizek.appendChild(doc.createTextNode(""+znz));
            report.appendChild(iloscZnizek);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String time = dtf.format(now);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("report"+time+".xml"));

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

        raport = "Przychód z pociągu: " +rev +"\nIlość pasażerów :"+foo+"\nIlość biletów ze zniżką: "+znz;
        return raport;
    }


}
