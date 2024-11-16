import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class TicketPool {
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());
    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void addTicket(String ticket) {
        synchronized (tickets) {
            if (tickets.size() < maxCapacity) {
                tickets.add(ticket);
                logger.info("Ticket Added: " + ticket);
            } else {
                logger.warning("Ticket Pool is Full. Cannot add more tickets to the Ticket Pool.");
            }
        }
    }

    public String removeTicket() {
        synchronized (tickets) {
            if (!tickets.isEmpty()) {
                String ticket = tickets.removeFirst();
                logger.info("Ticket Removed: " + ticket);
                return ticket;
            } else {
                logger.warning("There are no tickets in the TicketPool.");
                return null;
            }
        }
    }

    public int noTickets() {
        synchronized (tickets) {
            return tickets.size();
        }
    }
}