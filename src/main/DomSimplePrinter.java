package main;

import org.w3c.dom.Node;

/**
 * Created by Jakub on 24.05.2017.
 */
public class DomSimplePrinter {

    /**Domyslny konstruktor. */
    public DomSimplePrinter() {
        super();
    }

    /**Przechodzi podane drzewo DOM i wypisuje proste
     * informacje o niektorych typach wezlow.
     * @param nd wezel, ktorego podrzewo jest wypisywane
     */
    public void printDomTree(Node nd) {
        switch(nd.getNodeType()) {
            case Node.DOCUMENT_NODE :
                System.out.println("DOKUMENT");
                printChildren(nd);
                break;
            case Node.ELEMENT_NODE :
                System.out.println("ELEMENT: qName="+nd.getNodeName()+" URI= "+nd.getNamespaceURI()+
                        " localName="+nd.getLocalName()+" attributes:"+nd.getAttributes().getLength());
                printChildren(nd);
                break;
            case Node.TEXT_NODE :
                String napis = nd.getNodeValue();
      /* obcinam gdyby byl bardzo dlugi */
                if(napis.length() > 512)
                    napis = napis.substring(0, 512);
                System.out.println("TEXT: "+napis);
                break;
        }
    }

    /**Przechodzi dzieci podanego wezla i wywoluje dla nich
     * metode printDomTree.
     * @param nd wezel, ktorego dzieci przechodzimy
     */
    private void printChildren(Node nd) {
        for(Node cur = nd.getFirstChild(); cur != null; cur = cur.getNextSibling()) {
            printDomTree(cur);
        }
    }
}

