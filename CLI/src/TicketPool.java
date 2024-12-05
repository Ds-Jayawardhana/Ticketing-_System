import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class TicketPool {
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());
    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());
    private final int maxCapacity;
    private volatile boolean isSystemRunning = true;
    private int totalSoldTickets = 0;
    private final int totalTickets;

    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
    }

    public void addTicket(String ticket) {
        synchronized (tickets) {
            if (!isSystemRunning) {
                return;
            }

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
            // Check if system is no longer running
            if (!isSystemRunning) {
                return null;
            }

            // If tickets are available in the pool, remove and return
            if (!tickets.isEmpty()) {
                String ticket = tickets.removeFirst();
                totalSoldTickets++;
                logger.info("Ticket Removed: " + ticket);

                // Check if all tickets have been sold
                if (totalSoldTickets >= totalTickets) {
                    logger.info("All tickets have been sold. System will now stop.");
                    isSystemRunning = false;
                }
                return ticket;
            }

            // Only show waiting message if there are still tickets to be sold
            if (totalSoldTickets < totalTickets) {
                logger.warning("Ticket Pool is empty. Waiting for more tickets.");
            }
            return null;
        }
    }

    public int noTickets() {
        synchronized (tickets) {
            return tickets.size();
        }
    }

    public boolean isSystemRunning() {
        return isSystemRunning;
    }

    public void stopSystem() {
        synchronized (tickets) {
            isSystemRunning = false;
        }
    }
}