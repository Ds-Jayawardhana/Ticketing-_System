import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Scanner scanner = new Scanner(System.in);
    private static Configuration config = new Configuration();
    private static TicketPool ticketPool;
    private static boolean systemRunning = false;

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
                """;

        systemStart();
    }

    public static void systemStart() {
        while (true) {
            System.out.println("Enter command: ");
            String command = scanner.nextLine();
            switch (command) {
                case "1":
                    userStart();
                    break;
                case "2":
                    System.out.println("Enter the Exit Word");
                    break;
                case "3":
                    SystemConfig();
                    break;
                case "4":
                    config.displayConfig();
                    System.out.println();
                    break;
            }
        }
    }

    public static void userStart() {
        if (!systemRunning) {
            logger.info("Starting the System...");

            if (config.getTotalTickets() <= 0) {
                SystemConfig();
            }

            ticketPool = new TicketPool(config.getMaxTicketCapacity());

            // Calculate release rate per vendor, ensuring minimum of 1
            int ratePerVendor = Math.max(1, config.getTicketReleaseRate() / config.getNoVendors());

            // Start vendor threads
            for (int i = 1; i <= config.getNoVendors(); i++) {
                new Thread(new Vendor(ticketPool, ratePerVendor, 1000, config, i)).start();
            }

            // Start customer threads
            for (int i = 1; i <= config.getNoCustomers(); i++) {
                new Thread(new Customer(ticketPool, 1000 / config.getCustomerRetrievalRate(), i)).start();
            }

            systemRunning = true;
            logger.info("System started with " + config.getTotalTickets() + " total tickets");
        } else {
            logger.info("System is already running.");
        }
    }



    public static void SystemConfig() {
        logger.info("Configuring the system...");
        System.out.println("Please enter the following system Configuration values");
        System.out.println("Enter the Number of total tickets");
        config.setTotalTickets(scanner.nextInt());
        System.out.println("Enter the release rate per Second");
        config.setTicketReleaseRate(scanner.nextInt());
        System.out.println("Enter the retrieve rate per Second");
        config.setCustomerRetrievalRate(scanner.nextInt());
        System.out.println("Enter the Max Ticket Capacity");
        config.setMaxTicketCapacity(scanner.nextInt());
        System.out.println("Enter the Number of Vendors");
        config.setNoVendors(scanner.nextInt());
        System.out.println("Enter the Number of Customers");
        config.setNoCustomers(scanner.nextInt());
    }
}