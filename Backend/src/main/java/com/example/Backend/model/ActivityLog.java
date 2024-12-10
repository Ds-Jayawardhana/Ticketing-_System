package com.example.Backend.model;

public class ActivityLog {
    private String actorType;    // "Vendor" or "Customer"
    private int actorId;         // ID of the vendor or customer
    private String action;       // Description of the action taken
    private String timestamp;    // When the activity occurred
    private String ticketId;     // ID of the ticket involved (if applicable)
    private String status;       // Status of the action (success/failure)

    // Constructor
    public ActivityLog(String actorType, int actorId, String action) {
        this.actorType = actorType;
        this.actorId = actorId;
        this.action = action;
        this.timestamp = java.time.LocalDateTime.now().toString();
        this.status = "success"; // Default status
    }

    // Detailed constructor
    public ActivityLog(String actorType, int actorId, String action, String ticketId) {
        this(actorType, actorId, action);
        this.ticketId = ticketId;
    }

    // Getters and Setters
    public String getActorType() {
        return actorType;
    }

    public void setActorType(String actorType) {
        this.actorType = actorType;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Helper method to format the log message
    public String formatLogMessage() {
        return String.format("%s %d %s at %s",
                actorType,
                actorId,
                action,
                timestamp
        );
    }
}