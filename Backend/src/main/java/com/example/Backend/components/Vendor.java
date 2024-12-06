package com.example.Backend.components;

import java.lang.module.Configuration;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class Vendor implements Runnable {
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());

    private Ticketpool ticketPool;
    private final int releaseRate;
    private Configuration config;
    private final int vendorId;


    public Vendor(Ticketpool ticketPool, int releaseRate, Configuration config, int vendorId) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
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
                    logger.info("Vendor " + vendorId + " released " + actualReleaseRate +
                            " tickets. Remaining total: " + config.getRemainingTickets());
                }
                Thread.sleep(1000);
            }
            logger.info("Vendor " + vendorId + " finished - no more tickets to release");
        } catch (InterruptedException e) {
            logger.info("Vendor " + vendorId + " Interrupted");
        }
    }
}
