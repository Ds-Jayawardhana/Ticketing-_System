public class Configuration {
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

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

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;


    public void displayConfig(){
        System.out.println("Number of Total Tickets:-"+ totalTickets);
        System.out.println("Release Rate Per Second:-"+ticketReleaseRate);
        System.out.println("Retrieve Rate per Second:-"+customerRetrievalRate);
        System.out.println("Max capacity"+maxTicketCapacity);
        System.out.println("Number of vendors");
        System.out.println("Number of customers");
    }
    



}
