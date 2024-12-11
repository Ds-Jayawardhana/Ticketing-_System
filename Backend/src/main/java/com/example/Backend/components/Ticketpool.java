package com.example.Backend.components;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.example.Backend.model.ActivityLog;
import com.example.Backend.model.TicketStatus;
import com.example.Backend.services.ConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Ticketpool {
    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());
    private static final Logger logger = Logger.getLogger(Ticketpool.class.getName());
    private int maxCap;
    private volatile boolean isSystemRunning = true;
    private int totalSoldTickets = 0;
    private int totalTickets;

    @Autowired
    private ConfigServiceImpl configService;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public Ticketpool(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void init(int maxCap, int totalTickets) {
        this.maxCap = maxCap;
        this.totalTickets = totalTickets;
    }

    public void addTicket(String ticket) {
        synchronized(this.tickets) {
            if (this.isSystemRunning) {
                if (this.tickets.size() < this.maxCap) {
                    this.tickets.add(ticket);
                    logger.info("Ticket Added: " + ticket);

                    TicketStatus status = new TicketStatus(
                            totalTickets,
                            tickets.size(),
                            totalSoldTickets
                    );
                    messagingTemplate.convertAndSend("/topic/ticket-status", status);

                    ActivityLog activity = new ActivityLog(
                            "Vendor",
                            0,
                            "Released ticket: " + ticket
                    );
                    messagingTemplate.convertAndSend("/topic/activity", activity);
                } else {
                    logger.warning("Ticket Pool is Full. Cannot add more tickets to the Ticket Pool.");
                }
            }
        }
    }

    public String removeTicket() {
        synchronized(this.tickets) {
            if (!this.isSystemRunning) {
                return null;
            } else if (!this.tickets.isEmpty()) {
                String ticket = this.tickets.remove(0);
                ++this.totalSoldTickets;
                logger.info("Ticket Removed: " + ticket);

                TicketStatus status = new TicketStatus(
                        totalTickets,
                        tickets.size(),
                        totalSoldTickets
                );
                messagingTemplate.convertAndSend("/topic/ticket-status", status);

                ActivityLog activity = new ActivityLog(
                        "Customer",
                        0,
                        "Purchased ticket: " + ticket
                );
                messagingTemplate.convertAndSend("/topic/activity", activity);

                if (this.totalSoldTickets >= this.totalTickets) {
                    logger.info("All tickets have been sold. System will now stop.");
                    this.isSystemRunning = false;
                }
                return ticket;
            } else {
                if (this.totalSoldTickets < this.totalTickets) {
                    logger.warning("Ticket Pool is empty. Waiting for more tickets.");
                }
                return null;
            }
        }
    }

    public int noTickets() {
        synchronized(this.tickets) {
            return this.tickets.size();
        }
    }

    public boolean isSystemRunning() {
        return this.isSystemRunning;
    }

    public void stopSystem() {
        synchronized(this.tickets) {
            this.isSystemRunning = false;
        }
    }
}