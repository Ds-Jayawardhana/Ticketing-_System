import java.util.logging.Logger;

public class Vendor implements Runnable {
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());
    private final TicketPool ticketPool;
    private final int releaseRate;
    private final int releaseInterval;
    private final Configuration config;
    private final int vendorId;

    public Vendor(TicketPool ticketPool, int releaseRate, int releaseInterval, Configuration config, int vendorId) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.releaseInterval = releaseInterval;
        this.config = config;
        this.vendorId = vendorId;
    }

    @Override
    public void run() {
        int ticketCount = 1;
        try {
            while (config.getRemainingTickets() > 0) {
                int actualReleaseRate = Math.min(releaseRate, config.getRemainingTickets());
                if (actualReleaseRate > 0) {
                    for (int i = 0; i < actualReleaseRate; i++) {
                        ticketPool.addTicket("Ticket-V" + vendorId + "-" + ticketCount++);
                    }
                    config.decrementRemainingTickets(actualReleaseRate);
                    logger.info("Vendor " + vendorId + " released " + actualReleaseRate + " tickets. Remaining total: " + config.getRemainingTickets());
                }
                Thread.sleep(releaseInterval);
            }
            logger.info("Vendor " + vendorId + " finished - no more tickets to release");
        } catch (InterruptedException e) {
            logger.info("Vendor " + vendorId + " Interrupted");
        }
    }
}


