import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool {
    private final List<String>tickets=Collections.synchronizedList(new LinkedList<>());
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void addTicket(String ticket){
        synchronized(tickets){
            if(tickets.size()< maxCapacity){
                tickets.add(ticket);
                System.out.println("Ticket Added"+ticket);
            }else{
                System.out.println("Ticket Pool is Full Cannot add more ticekts to the Ticket Pool");
            }
        }

    }
    public String removeTicket(){
        synchronized (tickets){
            if(!tickets.isEmpty()){
                String ticket=tickets.removeFirst();
                System.out.println("Tickets are removed");
                return ticket;
            }else{
                System.out.println("There are no any tickets in the TicketPool");
                return null;
            }
        }
    }
    public int noTickets(){
        synchronized (tickets){
            return tickets.size();
        }
    }
}
