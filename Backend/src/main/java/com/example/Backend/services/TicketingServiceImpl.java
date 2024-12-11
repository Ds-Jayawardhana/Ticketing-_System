package com.example.Backend.services;

import java.util.logging.Logger;
import com.example.Backend.components.Ticketpool;
import com.example.Backend.components.Vendor;
import com.example.Backend.model.Config;
import com.example.Backend.config.TicketWebHandler;
import com.example.Backend.model.ActivityLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.example.Backend.components.Customer;

@Service
public class TicketingServiceImpl implements TicketingService {
    private static final Logger logger = Logger.getLogger(TicketingServiceImpl.class.getName());
    private boolean systemRunning = false;
    private Thread[] vendorThreads;
    private Thread[] customerThreads;

    @Autowired
    private ConfigServiceImpl configService;

    @Autowired
    private Ticketpool ticketpool;

    @Autowired
    private TicketWebHandler ticketWebHandler;  // Changed from SimpMessagingTemplate

    @Autowired
    private ApplicationContext applicationContext;

    public void userStart() {
        if (systemRunning) {
            logger.info("System is already running.");
            ticketWebHandler.broadcastActivity(
                    new ActivityLog("System", 0, "System is already running")
            );
            return;
        }

        Config currentConfig = configService.getLatestConfig();
        if (!validateConfiguration(currentConfig)) {
            logger.warning("Invalid configuration");
            ticketWebHandler.broadcastActivity(
                    new ActivityLog("System", 0, "Invalid configuration")
            );
            return;
        }

        configService.setRemainingTickets(currentConfig.getTotalTickets());
        ticketpool.init(currentConfig.getMaxCap(), currentConfig.getTotalTickets());

        int ratePerVendor = Math.max(1, currentConfig.getReleaseRate() / currentConfig.getNoVendors());

        vendorThreads = new Thread[currentConfig.getNoVendors()];
        customerThreads = new Thread[currentConfig.getNoCustomers()];

        // Create Vendor threads using Spring context
        for (int i = 0; i < currentConfig.getNoVendors(); i++) {
            Vendor vendor = applicationContext.getBean(Vendor.class,
                    ticketpool, ratePerVendor, i + 1, configService, ticketWebHandler);  // Updated to use ticketWebHandler
            vendorThreads[i] = new Thread(vendor);
            vendorThreads[i].start();
        }

        // Create Customer threads using Spring context
        for (int i = 0; i < currentConfig.getNoCustomers(); i++) {
            Customer customer = applicationContext.getBean(Customer.class,
                    ticketpool, 1000 / currentConfig.getRetrievalRate(), i + 1, ticketWebHandler);  // Updated to use ticketWebHandler
            customerThreads[i] = new Thread(customer);
            customerThreads[i].start();
        }

        systemRunning = true;
        String startMessage = "System started with " + currentConfig.getTotalTickets() + " total tickets";
        logger.info(startMessage);
        ticketWebHandler.broadcastActivity(
                new ActivityLog("System", 0, startMessage)
        );
    }

    private boolean validateConfiguration(Config config) {
        if (config == null) return false;
        if (config.getTotalTickets() <= 0) return false;
        if (config.getMaxCap() <= 0 || config.getMaxCap() > config.getTotalTickets()) return false;
        if (config.getNoVendors() <= 0) return false;
        if (config.getNoCustomers() <= 0) return false;
        return true;
    }

    public void stopSystem() {
        if (!systemRunning) {
            logger.info("System is not running.");
            ticketWebHandler.broadcastActivity(
                    new ActivityLog("System", 0, "System is not running")
            );
            return;
        }

        if (vendorThreads != null) {
            for (Thread thread : vendorThreads) {
                if (thread != null && thread.isAlive()) {
                    thread.interrupt();
                }
            }
        }

        if (customerThreads != null) {
            for (Thread thread : customerThreads) {
                if (thread != null && thread.isAlive()) {
                    thread.interrupt();
                }
            }
        }

        systemRunning = false;
        ticketpool = null;
        vendorThreads = null;
        customerThreads = null;

        String stopMessage = "System stopped successfully";
        logger.info(stopMessage);
        ticketWebHandler.broadcastActivity(
                new ActivityLog("System", 0, stopMessage)
        );
    }
}