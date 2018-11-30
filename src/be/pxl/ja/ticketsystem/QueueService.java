package be.pxl.ja.ticketsystem;

import java.util.*;

public class QueueService {

    private HashMap<String, Deque<User>> events;


    public QueueService() {
        events = new HashMap<>();
    }

    public void addToQueue(String eventID, User user) {
        if (!events.containsKey(eventID)) {
            Deque<User> deque = new ArrayDeque<>();
            deque.add(user);
            events.put(eventID, deque);
        } else {
            Deque<User> currentDeque = getQueue(eventID);
            currentDeque.add(user);
        }
    }

    public Deque<User> getQueue(String eventID) {
        return events.get(eventID);
    }

    public User getNextInLine(String eventID) {
        if (events.get(eventID).peekFirst() == null) {
            System.out.println("Queue is empty.");
        } else {
            return events.get(eventID).peekFirst();
        }
        return null;
    }

    public void removeFromQueue(String eventID) {
        events.get(eventID).removeFirst();
    }

    public void printQueue(String eventID) {
        if (getQueueSize(eventID) == 0) {
            System.out.println("Queue is empty.");
        } else {
            for (User user : events.get(eventID)) {
                System.out.println("ID: " + user.getId() + " - Firstname: " + user.getFirstname() + " Lastname: " + user.getLastname());
            }
        }
    }

    public int getQueueSize(String eventID) {
        return events.get(eventID).size();
    }
}
