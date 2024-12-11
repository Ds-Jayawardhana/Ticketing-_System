package com.example.Backend.components;

import java.util.logging.Logger;
import com.example.Backend.services.ConfigServiceImpl;
import com.example.Backend.model.ActivityLog;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Vendor implements Runnable {
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());
    private final Ticketpool ticketPool;
    private final int releaseRate;
    private final int vendorId;
    private final ConfigServiceImpl configService;
    private final SimpMessagingTemplate messagingTemplate;

    public Vendor(Ticketpool ticketpool,
                  int releaseRate,
                  int vendorId,
                  ConfigServiceImpl configService,
                  SimpMessagingTemplate messagingTemplate) {
        this.ticketPool = ticketpool;
        this.releaseRate = releaseRate;
        this.vendorId = vendorId;
        this.configService = configService;
        this.messagingTemplate = messagingTemplate;
    }

    private void sendActivityMessage(String message) {
        ActivityLog activity = new ActivityLog(
                "Vendor",
                vendorId,
                message
        );
        messagingTemplate.convertAndSend("/topic/activity", activity);
        logger.info("Vendor " + vendorId + ": " + message);
    }

    @Override
    public void run() {
        int ticketCount = 1;
        try {
            sendActivityMessage("Started vendor service");

            while (configService.getRemainingTickets() > 0) {
                int actualReleaseRate = Math.min(releaseRate, configService.getRemainingTickets());
                if (actualReleaseRate > 0) {
                    for (int i = 0; i < actualReleaseRate; i++) {
                        String ticketNumber = "Ticket-V" + vendorId + "-" + ticketCount++;
                        ticketPool.addTicket(ticketNumber);
                    }
                    configService.decrementRemainingTickets(actualReleaseRate);
                    sendActivityMessage("Released " + actualReleaseRate + " tickets. Remaining: " + configService.getRemainingTickets());
                }
                Thread.sleep(1000);
            }

            sendActivityMessage("Finished - no more tickets to release");

        } catch (InterruptedException e) {
            sendActivityMessage("Service interrupted");
        }
    }
}