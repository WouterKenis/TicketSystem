package be.pxl.ja.ticketsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Deque;
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

    public void assignTickets(int eventID, int number) {
        Deque<User> users = queueService.getQueue(eventID);

        for (int i = 1; i <= number; i++) {
            users.removeLast();


        }
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

    public void addEvent(String localDateTime, String name, String description, double price, String locationID) {
        BufferedWriter bw = null;
        String dts = localDateTime;

        int year = Integer.parseInt(dts.substring(4,8));
        int month = Integer.parseInt(dts.substring(2,4));
        int day = Integer.parseInt(dts.substring(0,2));
        int hour = Integer.parseInt(dts.substring(8,10));
        int minutes = Integer.parseInt(dts.substring(10));
        LocalDateTime time = LocalDateTime.of(year, month, day, hour, minutes);

        Event event = new Event(name, time, description, price);

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
        events.put(event.getId(), event);
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
        venues.put(venue.getId(), venue);
    }

    public void addUser(String name, String firstName, String birthDay) {
        BufferedWriter bw = null;
        LocalDate birthDayy = LocalDate.of(Integer.parseInt(birthDay.substring(4)), Integer.parseInt(birthDay.substring(2,4)), Integer.parseInt(birthDay.substring(0,2)));
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
        users.put(user.getId(), user);
    }

}
