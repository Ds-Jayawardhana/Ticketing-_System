public class vendor implements Runnable{

    private final TicketPool ticketPool;
    private final int releaseRate;
    private final int releaseInterval;

    public vendor(TicketPool ticketPool, int releaseRate, int releaseInterval) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {

    }
}
