package com.example.Backend.components;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.example.Backend.model.ActivityLog;
import java.util.logging.Logger;

@Component
@Scope("prototype")
public class Customer implements Runnable {
    private static final Logger logger = Logger.getLogger(Customer.class.getName());
    private final Ticketpool ticketpool;
    private final int retrievalInterval;
    private final int customerId;
    private final SimpMessagingTemplate messagingTemplate;

    public Customer(Ticketpool ticketpool,
                    int retrievalInterval,
                    int customerId,
                    SimpMessagingTemplate messagingTemplate) {
        this.ticketpool = ticketpool;
        this.retrievalInterval = retrievalInterval;
        this.customerId = customerId;
        this.messagingTemplate = messagingTemplate;
    }

    private void sendActivityMessage(String message) {
        ActivityLog activity = new ActivityLog(
                "Customer",
                customerId,
                message
        );
        messagingTemplate.convertAndSend("/topic/activity", activity);
        logger.info("Customer " + customerId + ": " + message);
    }

    @Override
    public void run() {
        try {
            sendActivityMessage("Started customer service");

            while (true) {
                String ticket = ticketpool.removeTicket();
                if (ticket != null) {
                    sendActivityMessage("Retrieved ticket: " + ticket);
                } else {
                    // Optionally log when no tickets are available
                    sendActivityMessage("Waiting for tickets...");
                }
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            sendActivityMessage("Service interrupted");
        }
    }
}