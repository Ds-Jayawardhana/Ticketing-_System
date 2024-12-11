package com.example.Backend.components;

import java.util.logging.Logger;

import com.example.Backend.config.TicketWebHandler;
import com.example.Backend.services.ConfigServiceImpl;
import com.example.Backend.model.ActivityLog;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Vendor implements Runnable {
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());
    private final Ticketpool ticketPool;
    private final int releaseRate;
    private final int vendorId;
    private final ConfigServiceImpl configService;
    private final TicketWebHandler ticketWebHandler;

    public Vendor(Ticketpool ticketpool,
                  int releaseRate,
                  int vendorId,
                  ConfigServiceImpl configService,
                  TicketWebHandler ticketWebHandler) {
        this.ticketPool = ticketpool;
        this.releaseRate = releaseRate;
        this.vendorId = vendorId;
        this.configService = configService;
        this.ticketWebHandler = ticketWebHandler;
    }

    private void sendActivityMessage(String message) {
        ActivityLog activity = new ActivityLog(
                "Vendor",
                vendorId,
                message
        );
        ticketWebHandler.broadcastActivity(activity);  // Using ticketWebHandler instead
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