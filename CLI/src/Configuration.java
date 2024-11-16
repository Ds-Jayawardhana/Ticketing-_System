public class Configuration {
    public int getTotalTickets() {
        return totalTickets;
    }

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getNoVendors() {
        return noVendors;
    }

    public void setNoVendors(int noVendors) {
        this.noVendors = noVendors;
    }

    public int getNoCustomers() {
        return noCustomers;
    }

    public void setNoCustomers(int noCustomers) {
        this.noCustomers = noCustomers;
    }

    private int noVendors;
    private int noCustomers;
    private int remainingTickets;  // Added to track remaining tickets

    public Configuration() {
        this.remainingTickets = 0;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;  // Fixed: proper assignment
    }

    // ... other getters remain the same ...

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
        this.remainingTickets = totalTickets;  // Initialize remaining tickets
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void decrementRemainingTickets(int count) {
        this.remainingTickets -= count;
        if (this.remainingTickets < 0) this.remainingTickets = 0;
    }

    public void displayConfig() {
        System.out.println("Number of Total Tickets: " + totalTickets);
        System.out.println("Remaining Tickets: " + remainingTickets);
        System.out.println("Release Rate Per Second: " + ticketReleaseRate);
        System.out.println("Retrieve Rate per Second: " + customerRetrievalRate);
        System.out.println("Max capacity: " + maxTicketCapacity);
        System.out.println("Number of vendors: " + noVendors);
        System.out.println("Number of customers: " + noCustomers);
    }
}