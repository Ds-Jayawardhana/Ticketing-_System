public class Customer implements Runnable {
    private final TicketPool ticketpool;
    private final int retreivelInterval;

    public Customer(TicketPool ticketpool, int retreivelInterval) {
        this.ticketpool = ticketpool;
        this.retreivelInterval = retreivelInterval;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ticketpool.removeTicket();
                Thread.sleep(retreivelInterval);
            }
        } catch (InterruptedException e) {
            System.out.println("Customer Interrupted");
        }
    }
}



