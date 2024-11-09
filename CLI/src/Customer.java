public class Customer implements Runnable {
    private final TicketPool ticketpool;
    private final int retreivelInterval;

    public Customer(TicketPool ticketpool, int retreivelInterval) {
        this.ticketpool = ticketpool;
        this.retreivelInterval = retreivelInterval;
    }

    @Override
    public void run() {

    }
}

