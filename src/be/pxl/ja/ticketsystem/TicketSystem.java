package be.pxl.ja.ticketsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TicketSystem {

    private QueueService queueService;
    private List<User> users;
    private List<Event> events;
    private List<Venue> venues;

    public TicketSystem(QueueService queueService) {
        this.queueService = queueService;
        loadVenues();
        loadEvents();
        loadUsers();
    }

    public void assignTickets(String eventID, int number) {
        Deque<User> users = queueService.getQueue(eventID);
        if (number > users.size()) {
            System.out.println("Number of tickets is greater than the queue count. Emptying queue...");
            number = users.size();
        }
        for (int i = 1; i <= number; i++) {
            users.removeFirst();
        }
    }


    private <T> List<T> loadData(Path path, Mapper<T> mapper) {

        List<T> data = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = null;
            while ((line = reader.readLine()) != null) {

                data.add(mapper.map(line.split(";")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void loadUsers() {
        users = loadData(Paths.get("data/userdata.txt"), new UserMapper());
    }

    private void loadVenues() {
        venues = loadData(Paths.get("data/venuedata.txt"), new VenueMapper());
    }

    private void loadEvents() {
        events = loadData(Paths.get("data/eventdata.txt"), new EventMapper(this));
    }

    public void requestTicket(Event event, User user) {
        queueService.addToQueue(event.getId(), user);
    }

    public void viewNext(String eventID) {
        if (queueService.getNextInLine(eventID) == null) ;
        else {
            User nextUserInLine = queueService.getNextInLine(eventID);
            System.out.println("Next in line for event (ID: " + eventID + ")" + ": " + nextUserInLine.getFirstname() + " " + nextUserInLine.getLastname());
        }
    }

    public Event getEvent(String eventID) {
        for (Event e : events) {
            if (e.getId().equalsIgnoreCase(eventID)) {
                return e;
            }
        }
        return null;
    }

    public Venue getVenue(String venueID) {
        for (Venue v : venues) {
            if (v.getId().equalsIgnoreCase(venueID)) {
                return v;
            }
        }
        return null;
    }

    public User getUser(String userID) {
        for (User u : users) {
            if (u.getId().equalsIgnoreCase(userID)) {
                return u;
            }
        }
        return null;
    }

    public void addEvent(String localDateTime, String name, String description, double price, String locationID) {

        BufferedWriter bw = null;

        String dts = localDateTime;
        int year = Integer.parseInt(dts.substring(4, 8));
        int month = Integer.parseInt(dts.substring(2, 4));
        int day = Integer.parseInt(dts.substring(0, 2));
        int hour = Integer.parseInt(dts.substring(8, 10));
        int minutes = Integer.parseInt(dts.substring(10));

        LocalDateTime time = LocalDateTime.of(year, month, day, hour, minutes);

        Event event = new Event(name, time, description, price);
        event.setVenue(getVenue(locationID));

        try {
            bw = Files.newBufferedWriter(Paths.get("data\\eventdata.txt"), StandardOpenOption.APPEND);
            bw.write(event.getId() + ";" + localDateTime + ";" + name + ";" + description + ";" + price + ";" + locationID);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        events.add(event);
    }

    public void addVenue(String name, String street, int streetNumber, String zipCode, String city, int capacity) {
        BufferedWriter bw = null;
        Venue venue = new Venue(name, street, zipCode, city, capacity);
        try {
            bw = Files.newBufferedWriter(Paths.get("data\\venuedata.txt"), StandardOpenOption.APPEND);
            bw.write(venue.getId() + ";" + name + ";" + street + ";" + streetNumber + ";" + zipCode + ";" + city + ";" + capacity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        venues.add(venue);
    }

    public void addUser(String name, String firstName, String birthDay) {
        BufferedWriter bw = null;
        LocalDate birthDayy = LocalDate.of(Integer.parseInt(birthDay.substring(4)), Integer.parseInt(birthDay.substring(2, 4)), Integer.parseInt(birthDay.substring(0, 2)));
        User user = new User(name, firstName, birthDayy);
        try {
            bw = Files.newBufferedWriter(Paths.get("data\\userdata.txt"), StandardOpenOption.APPEND);
            bw.write(user.getId() + ";" + user.getLastname() + ";" + user.getFirstname() + ";" + birthDay);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        users.add(user);
    }

}
