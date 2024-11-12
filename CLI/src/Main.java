import java.util.*;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private Configuration config;
    private TicketPool ticketpool;

    public static void main(String[] args) {
        System.out.println("-------Welcome To Realtime Ticketing Booking System-------");





    }


    public void SystemConfig() {
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

