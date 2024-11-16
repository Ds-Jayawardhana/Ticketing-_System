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
        int tickeCount=1;
            try{
                while (true){
                    for(int i=0;i<releaseRate;i++){
                        ticketPool.addTicket("Ticket -"+tickeCount++);
                    }
                    Thread.sleep(releaseInterval);
                }
            }catch (InterruptedException e){
                System.out.println("Vendor Interrupted");
            }
    }
}
