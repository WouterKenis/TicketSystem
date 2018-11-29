package be.pxl.ja.ticketsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class TicketSystem {

    private QueueService queueService;
    private HashMap<String, User> users;
    private HashMap<String, Event> events;
    private HashMap<String, Venue> venues;

    public TicketSystem(QueueService queueService) {
        this.queueService = queueService;
        venues = GetAllVenues();
        users = GetAllUsers();
        events = GetAllEvents();

    }

    public HashMap<String, User> GetAllUsers() {
        this.users = new HashMap<>();
        UserMapper userMapper = new UserMapper();

        BufferedReader br = null;

        try {
            br = Files.newBufferedReader(Paths.get("data\\userdata.txt"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                users.put(data[0], userMapper.map(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public HashMap<String, Event> GetAllEvents() {
        this.events = new HashMap<>();
        EventMapper eventMapper = new EventMapper(this);

        BufferedReader br = null;

        try {
            br = Files.newBufferedReader(Paths.get("data\\eventdata.txt"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                events.put(data[0], eventMapper.map(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    public HashMap<String, Venue> GetAllVenues() {
        this.venues = new HashMap<>();
        VenueMapper venueMapper = new VenueMapper();

        BufferedReader br = null;

        try {
            br = Files.newBufferedReader(Paths.get("data\\venuedata.txt"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                venues.put(data[0], venueMapper.map(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return venues;
    }

    public void requestTicket(Event event, User user) {
        queueService.addToQueue(Integer.parseInt(event.getId()), user);
    }

    public void viewNext(int eventID) {
        System.out.println("Next in line for event (ID: " + eventID + ")" + queueService.getNextInLine(eventID));
    }

    public Event getEvent(String eventID) {
        return events.get(eventID);
    }
    public Venue getVenue(String venueID) {
        return venues.get(venueID);
    }
    public User getUser(String userID) {
        return users.get(userID);
    }


}
