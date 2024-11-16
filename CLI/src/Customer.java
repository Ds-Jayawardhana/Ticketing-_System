import java.util.logging.Logger;

public class Customer implements Runnable {
    private static final Logger logger = Logger.getLogger(Customer.class.getName());
    private final TicketPool ticketPool;
    private final int retrievalInterval;
    private final int customerId;

    public Customer(TicketPool ticketPool, int retrievalInterval, int customerId) {
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    logger.info("Customer " + customerId + " retrieved ticket: " + ticket);
                }
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            logger.info("Customer " + customerId + " Interrupted");
        }
    }
}