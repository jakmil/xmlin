package main;

import org.w3c.dom.Node;

import javax.xml.xpath.*;

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
        raport = "Przychód z pociągu: " +rev +"\nIlość pasażerów :"+foo+"\nIlość biletów ze zniżką: "+znz;
        return raport;
    }
}
