package com.example.Backend.model;

public class TicketStatus {
    private int totalTickets;        // Total number of tickets in the system
    private int ticketsReleased;     // Number of tickets released by vendors
    private int ticketsPurchased;    // Number of tickets purchased by customers
    private String timestamp;        // When this status was created
    private int remainingTickets;    // Number of tickets still available

    // Constructor
    public TicketStatus(int totalTickets, int ticketsReleased, int ticketsPurchased) {
        this.totalTickets = totalTickets;
        this.ticketsReleased = ticketsReleased;
        this.ticketsPurchased = ticketsPurchased;
        this.remainingTickets = totalTickets - ticketsPurchased;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketsReleased() {
        return ticketsReleased;
    }

    public void setTicketsReleased(int ticketsReleased) {
        this.ticketsReleased = ticketsReleased;
    }

    public int getTicketsPurchased() {
        return ticketsPurchased;
    }

    public void setTicketsPurchased(int ticketsPurchased) {
        this.ticketsPurchased = ticketsPurchased;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }
}