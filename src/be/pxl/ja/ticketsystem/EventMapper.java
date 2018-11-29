package be.pxl.ja.ticketsystem;

import java.time.LocalDateTime;

public class EventMapper implements Mapper<Event> {
    private TicketSystem ticketSystem;

    public EventMapper(TicketSystem ticketSystem) {
        this.ticketSystem = ticketSystem;
    }

    @Override
    public Event map(String[] data) {
        Event event = new Event("unknown", LocalDateTime.now(), "unknown", 0);

        event.setId(data[0]);

        String dts = data[1];

        int year = Integer.parseInt(dts.substring(4,8));
        int month = Integer.parseInt(dts.substring(2,4));
        int day = Integer.parseInt(dts.substring(0,2));
        int hour = Integer.parseInt(dts.substring(8,10));
        int minutes = Integer.parseInt(dts.substring(10));

        event.setTime(LocalDateTime.of(year, month, day, hour, minutes));

        if (data[2] != null)
            event.setName(data[2]);

        if (data[3] != null)
            event.setDescription(data[3]);

        if (data[4] != null)
            event.setPrice(Double.parseDouble(data[4]));
        else
            event.setPrice(0);

        if (data[5] != null)
            event.setVenue(ticketSystem.getVenue(data[5]));

        return event;
    }
}
