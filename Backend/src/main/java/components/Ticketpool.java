package components;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;
import lombok.Getter;


@Component
@Getter

public class Ticketpool {
    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());
    private int maxCapacity;
    private volatile boolean isSystemRunning = true;
    private int totalSoldTickets = 0;
    private  int totalTickets;

    public void TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        
    }
    
}
