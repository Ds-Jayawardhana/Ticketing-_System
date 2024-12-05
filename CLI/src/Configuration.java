import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

public class Configuration {

    private int totalTickets;

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }

    public int getNoVendors() {
        return noVendors;
    }

    public void setNoVendors(int noVendors) {
        this.noVendors = noVendors;
    }

    public int getNoCustomers() {
        return noCustomers;
    }

    public void setNoCustomers(int noCustomers) {
        this.noCustomers = noCustomers;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private int noVendors;
    private int noCustomers;
    private int remainingTickets;

 
    private static final String CONFIG_FILE = "ticketing_config.json";


    public Configuration() {
        this.remainingTickets = this.totalTickets;
    }

    
    public void saveConfig() {
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this, writer);
            System.out.println("Configuration saved successfully to " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

  
    public static Configuration loadConfig() {
        try (Reader reader = new FileReader(CONFIG_FILE)) {
            Gson gson = new Gson();
            Configuration config = gson.fromJson(reader, Configuration.class);
            System.out.println("Configuration loaded successfully from " + CONFIG_FILE);
            return config;
        } catch (IOException e) {
            System.out.println("No existing configuration found. Creating new configuration.");
            return new Configuration();
        }
    }
    public void decrementRemainingTickets(int count) {
        this.remainingTickets -= count;
        if (this.remainingTickets < 0) this.remainingTickets = 0;
    }

    public void displayConfig() {
        System.out.println("Number of Total Tickets: " + totalTickets);
        System.out.println("Remaining Tickets: " + remainingTickets);
        System.out.println("Release Rate Per Second: " + ticketReleaseRate);
        System.out.println("Retrieve Rate per Second: " + customerRetrievalRate);
        System.out.println("Max capacity: " + maxTicketCapacity);
        System.out.println("Number of vendors: " + noVendors);
        System.out.println("Number of customers: " + noCustomers);
    }


}