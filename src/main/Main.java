package main;

import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static Node train;
    private static Node tickets;
    private static boolean blackFlag = false;
    public static void main(String[] args) {

        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                //br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter something : ");
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
                        setTrain(inputReader.readInput(urla));
                        setTickets(inputReader.readInput(urlb));
                        inputReader.readInput(urlb);
                        System.out.println(urla);
                        System.out.println(urlb);
                        DomSimplePrinter simplePrinter = new DomSimplePrinter();



                        //TODO
                        simplePrinter.printDomTree(train);
                        simplePrinter.printDomTree(tickets);
                        //TODO



                        System.out.println("Files loaded");
                        break;
                    case "CONTROL":
                        //TODO
                        TicketController controller = new TicketController();
                        String com = controller.controlTrain(train, tickets);
                        System.out.println(com);
                        if(com == "VALID"){
                            setBlackFlag(true);
                        }
                        break;
                    case "REPORT":
                        //TODO
                        TicketReporter reporter = new TicketReporter();
                        if(blackFlag){
                            String report = reporter.makeReport(train, tickets);
                            System.out.println(report);
                        } else {
                            System.out.println("Input invalid, use Valid to validate input");
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
}