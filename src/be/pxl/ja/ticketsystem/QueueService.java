package be.pxl.ja.ticketsystem;

import java.util.*;

public class QueueService {

    private HashMap<Integer, Deque<User>> events;

    public QueueService() {
        events = new HashMap<>();
    }

    public void addToQueue(int eventID, User user) {
        if (!events.containsKey(eventID)) {
            Deque<User> deque = new ArrayDeque<>();
            deque.add(user);
            events.put(eventID, deque);
        } else {
            Deque<User> currentDeque = getQueue(eventID);
            currentDeque.add(user);
        }
    }

    public Deque<User> getQueue(int eventID) {
        return events.get(eventID);
    }

    public User getNextInLine(int eventID) {
        return events.get(eventID).peekLast();
    }

    public void removeFromQueue(int eventID) {
        events.remove(eventID).removeLast();
    }

    public void printQueue(int eventID) {
        for (User user : events.get(eventID)) {
            System.out.println("ID: " + user.getId() + " - Firstname: " + user.getFirstname() + " Lastname: " + user.getLastname());
        }
    }

    public int getQueueSize(int eventID) {
        return events.get(eventID).size();
    }
}
