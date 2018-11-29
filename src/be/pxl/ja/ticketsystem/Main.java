package be.pxl.ja.ticketsystem;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
            QueueService queueService = new QueueService();
            TicketSystem ticketSystem = new TicketSystem(queueService);
            System.out.println(ticketSystem.getEvent("STE-00004").getName());
            System.out.println(ticketSystem.getUser("U-000005").getLastname());
            System.out.println(ticketSystem.getVenue("V-0004").getName());


    }
}