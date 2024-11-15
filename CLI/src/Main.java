import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Configuration config;
    private TicketPool ticketpool;

    public static void main(String[] args) {
        System.out.println("-------Welcome To Realtime Ticketing Booking System-------");
        String prn_op = """
                *************************
                *      Menu Section     *
                *************************
                1. Start the System
                2. Stop the System
                3. Configure System
                4. Display Configuration
                5. Exit
                """;
                systemStart();

    }
    public static void systemStart() {
        while (true) {
            System.out.println("Enter command: ");
            String command = scanner.nextLine();
            switch (command) {
                case "1":
                    System.out.println("Starting the System");
                    break;
                case "2":
                    System.out.println("Stopping the System");
                    break;
                case "3":
                    System.out.println("----------------------Delete Student------------------------");
                    SystemConfig();
                    System.out.println();

                    break;
                case "4":
                    System.out.println("-----------------------Find Student--------------------------");
                    config.displayConfig();
                    System.out.println();

                    break;
                case "5":
                    System.out.println("------------------------Store students-------------------------");
                    System.out.println();
                    break;
            }
        }
    }

    public static void SystemConfig() {
        System.out.println("Please enter the following system Configuration values");
        System.out.println("Enter the Number of total tickets");
        config.setTotalTickets(scanner.nextInt());
        System.out.println("Enter the release rate per Second");
        config.setTicketReleaseRate(scanner.nextInt());
        System.out.println("Enter the retrieve rate :-");
        System.out.println("Enter the Max Ticket Capacity");
        config.setMaxTicketCapacity(scanner.nextInt());
        System.out.println("Enter the Number of Vendors");
        config.setNoVendors(scanner.nextInt());
        System.out.println("Enter the Number of total tickets");
        config.setNoCustomers(scanner.nextInt());

            }

    }


