import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.print.DocFlavor;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathExpressionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static Node train;
    private static Node tickets;
    private static boolean blackFlag = false;
    private static boolean kFlag = true;
    public static void main(String[] args) {

        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                //br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter command: ");
                String input = br.readLine();
                input = input.toUpperCase();
                switch (input) {
                    case "EXIT":
                        System.out.println("Exit!");
                        System.exit(0);
                        break;
                    case "HELP":
                        System.out.println("exit - exit \n help - help \n input - input mode " +
                                "\n control - doublebooking control mode \n report - revenue report");
                        break;
                    case "INPUT":
                        InputReader inputReader = new InputReader();
                        System.out.print("Train path: ");
                        String urla = br.readLine();
                        System.out.print("Tickets path: ");
                        String urlb = br.readLine();

                        Node a = inputReader.readInputTrain(urla);
                        Node b = inputReader.readInputTicket(urlb);
                        if(kFlag){
                            setTrain(a);
                            setTickets(b);
                        } else {
                            System.out.println("Files unloaded - INVALID WITH SCHEMA");
                        }
                        System.out.println(urla);
                        System.out.println(urlb);
                        setBlackFlag(false);
                        setkFlag(true);
                        System.out.println("Files loaded");
                        break;
                    case "CONTROL":
                        if (train != null & tickets != null) {
                            TicketController controller = new TicketController();
                            String com = controller.controlTrain(train, tickets);
                            System.out.println(com);
                            if (com == "VALID") {
                                setBlackFlag(true);
                                System.out.println("Use 'report' to get report");
                            }
                        } else {
                            System.out.println("No data, use 'input' to select data");
                        }
                        break;
                    case "REPORT":
                        TicketReporter reporter = new TicketReporter();
                        if(blackFlag){
                            String report = reporter.makeReport(train, tickets);
                            System.out.println(report);
                        } else {
                            System.out.println("Input invalidated, use 'control' to validate input");
                        }
                        break;
                    default:
                        String k = "Invalid command: " + input + "\nuse 'help' for help";
                        System.out.println(k);
                        break;

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Node getTrain() {
        return train;
    }

    public static void setTrain(Node train) {
        Main.train = train;
    }

    public static Node getTickets() {
        return tickets;
    }

    public static void setTickets(Node tickets) {
        Main.tickets = tickets;
    }

    public static boolean isBlackFlag() {
        return blackFlag;
    }

    public static void setBlackFlag(boolean blackFlag) {
        Main.blackFlag = blackFlag;
    }

    public static boolean iskFlag() {
        return kFlag;
    }

    public static void setkFlag(boolean kFlag) {Main.kFlag = kFlag; }
}