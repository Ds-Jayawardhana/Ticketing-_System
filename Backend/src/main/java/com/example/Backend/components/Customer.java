package com.example.Backend.components;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Component

public class Customer implements Runnable {
    private static final Logger logger = Logger.getLogger(Customer.class.getName());
    private final Ticketpool ticketPool;
    @Autowired
    private final int customerId;


    public Customer(Ticketpool ticketPool, int customerId) {
        this.ticketPool = ticketPool;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    logger.info("Customer " + customerId + " retrieved ticket: " + ticket);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            logger.info("Customer " + customerId + " Interrupted");
        }
    }
}


