package com.example.Backend.services;

import java.util.Scanner;
import java.util.logging.Logger;

import com.example.Backend.components.Ticketpool;
import com.example.Backend.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.components.Customer;
@Service
public class TicketingServiceImpl implements TicketingService {
        private static final Logger logger = Logger.getLogger(TicketingServiceImpl.class.getName());
        private static final Scanner scanner = new Scanner(System.in);
        private static boolean systemRunning=true;

        @Autowired
        private static ConfigServiceImpl configServiceImpl;

        @Autowired
        private Ticketpool ticketpool;

        private static Config config;

    public void userStart() {
        if (systemRunning) {
            logger.info("System is already running.");
            return;
        }

        if (!validateConfiguration()) {
            configureSystem();
            return;
        }

        configServiceImpl.setRemainingTickets(config.getTotalTickets());

       
        ticketpool = new Ticketpool(config.getMaxCap(), config.getTotalTickets());

        // Calculate vendor release rate
        int ratePerVendor = Math.max(1, config.getReleaseRate() / config.getNoVendors());

        // Prepare thread arrays
        vendorThreads = new Thread[config.getNoVendors()];
        customerThreads = new Thread[config.getNoCustomers()];

        // Start vendor threads
        for (int i = 0; i < config.getNoVendors(); i++) {
            vendorThreads[i] = new Thread(new Vendor(
                    ticketpool,
                    ratePerVendor,
                    1000,
                    config,
                    i + 1
            ));
            vendorThreads[i].start();
        }

        // Start customer threads
        for (int i = 0; i < config.getNoCustomers(); i++) {
            customerThreads[i] = new Thread(new Customer(
                    ticketpool,
                    1000 / config.getRetrievalRate(),
                    i + 1
            ));
            customerThreads[i].start();
        }

        // Mark system as running
        systemRunning = true;
        logger.info("System started with " + config.getTotalTickets() + " total tickets");
    }

    private static boolean validateConfiguration() {
        if (config.getTotalTickets() <= 0) {
            System.out.println("Invalid configuration. Please configure the system.");
            return false;
        }

        if (config.getMaxCap()<= 0 ||
                config.getMaxCap() > config.getTotalTickets()) {
            System.out.println("Invalid max ticket capacity. Must be between 1 and total tickets.");
            return false;
        }

        return true;
    }

    public void configureSystem() {
        try {
            logger.info("Configuring the system...");
            System.out.println("Please enter the following system Configuration values");

            // Validate total tickets
            System.out.println("Enter the Number of total tickets (must be > 0)");
            int totalTickets;
            do {
                totalTickets = Integer.parseInt(scanner.nextLine());
                if (totalTickets <= 0) {
                    System.out.println("Total tickets must be greater than 0. Try again.");
                }
            } while (totalTickets <= 0);
            config.setTotalTickets(totalTickets);

            // Validate release rate
            System.out.println("Enter the release rate per Second (must be > 0)");
            int releaseRate;
            do {
                releaseRate = Integer.parseInt(scanner.nextLine());
                if (releaseRate <= 0) {
                    System.out.println("Release rate must be greater than 0. Try again.");
                }
            } while (releaseRate <= 0);
            configServiceImpl.setTicketReleaseRate(releaseRate);

            // Validate retrieval rate
            System.out.println("Enter the retrieve rate per Second (must be > 0)");
            int retrievalRate;
            do {
                retrievalRate = Integer.parseInt(scanner.nextLine());
                if (retrievalRate <= 0) {
                    System.out.println("Retrieval rate must be greater than 0. Try again.");
                }
            } while (retrievalRate <= 0);
            configServiceImpl.setCustomerRetrievalRate(retrievalRate);

            // Validate max ticket capacity
            System.out.println("Enter the Max Ticket Capacity (must be less than " + totalTickets + ")");
            int maxCapacity;
            do {
                maxCapacity = Integer.parseInt(scanner.nextLine());
                if (maxCapacity <= 0 || maxCapacity > totalTickets) {
                    System.out.println("Invalid max capacity. Must be between 1 and " + totalTickets);
                }
            } while (maxCapacity <= 0 || maxCapacity > totalTickets);
            configServiceImpl.setMaxTicketCapacity(maxCapacity);

            // Validate number of vendors
            System.out.println("Enter the Number of Vendors (must be > 0)");
            int vendors;
            do {
                vendors = Integer.parseInt(scanner.nextLine());
                if (vendors <= 0) {
                    System.out.println("Number of vendors must be greater than 0. Try again.");
                }
            } while (vendors <= 0);
            configServiceImpl.setNoVendors(vendors);

            // Validate number of customers
            System.out.println("Enter the Number of Customers (must be > 0)");
            int customers;
            do {
                customers = Integer.parseInt(scanner.nextLine());
                if (customers <= 0) {
                    System.out.println("Number of customers must be greater than 0. Try again.");
                }
            } while (customers <= 0);
            configServiceImpl.setNoCustomers(customers);

            // Save the configuration
            configServiceImpl.saveConfig();
            System.out.println("System configuration updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numeric values.");
        }
    }

    public static void displayConfiguration() {
        config.displayConfig();
    }

    public void stopSystem() {
        if (!systemRunning) {
            System.out.println("System is not running.");
            return;
        }

        // Interrupt vendor threads
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
        ticketPool = null;
        vendorThreads = null;
        customerThreads = null;

        System.out.println("System stopped successfully.");
        logger.info("System stopped.");
    }
}   
