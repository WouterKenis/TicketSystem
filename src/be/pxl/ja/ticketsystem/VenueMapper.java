package be.pxl.ja.ticketsystem;

public class VenueMapper implements Mapper<Venue> {
    @Override
    public Venue map(String[] data) {
        Venue venue = new Venue("unknown", "unknown", "unknown", "unknown", 0);

        if (data[0] != null)
            venue.setId(data[0]);

        if (data[1] != null)
            venue.setName(data[1]);

        String street = "unknown";
        if (data[2] != null && data[3] == null)
            street = data[2] + " (street number unknown)";
        else if (data[2] != null && data[3] != null)
            street = data[2] + " " + data[3];

        venue.setStreet(street);

        if (data[4] != null)
            venue.setZipCode(data[4]);

        if (data[5] != null)
            venue.setCity(data[5]);

        if (data[6] != null)
            venue.setCapacity(Integer.parseInt(data[6]));

        return venue;
    }
}
