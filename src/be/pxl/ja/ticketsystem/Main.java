package be.pxl.ja.ticketsystem;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        QueueService queueService = new QueueService();
        TicketSystem ticketSystem = new TicketSystem(queueService);
        User u = new User("Wouter", "Kenis", LocalDate.now());
        queueService.addToQueue("QUE-00002", u);
        queueService.addToQueue("QUE-00002", new User("Fons", "Kenis", LocalDate.now()));
        queueService.addToQueue("QUE-00002", new User("Jef", "Kenis", LocalDate.now()));
        queueService.addToQueue("QUE-00002", new User("Arthur", "Kenis", LocalDate.now()));
        queueService.addToQueue("QUE-00002", new User("Cletus", "Kenis", LocalDate.now()));

        queueService.printQueue("QUE-00002");
        System.out.println(queueService.getNextInLine("QUE-00002").getLastname());
        queueService.removeFromQueue("QUE-00002");
        queueService.addToQueue("QUE-00002", new User("last", "Kenis", LocalDate.now()));

        queueService.printQueue("QUE-00002");

        System.out.println(queueService.getNextInLine("QUE-00002").getLastname());

        System.out.println(queueService.getQueueSize("QUE-00002"));
        ticketSystem.assignTickets("QUE-00002", 4);

        ticketSystem.viewNext("QUE-00002");
        ticketSystem.requestTicket(ticketSystem.getEvent("QUE-00002"), u);

        queueService.printQueue("QUE-00002");
        ticketSystem.assignTickets("QUE-00002", 4);
        ticketSystem.viewNext("QUE-00002");
        queueService.printQueue("QUE-00002");
    }
}