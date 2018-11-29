package be.pxl.ja.ticketsystem;

import java.time.LocalDate;

public class UserMapper implements Mapper<User> {
    @Override
    public User map(String[] data) {
        User user = new User("unknown", "unknown", LocalDate.EPOCH);

        if (data[0] != null)
            user.setId(data[0]);

        if (data[1] != null)
            user.setLastname(data[1]);

        if (data[2] != null)
            user.setFirstname(data[2]);

        user.setBirthday(LocalDate.of(Integer.parseInt(data[3].substring(4)), Integer.parseInt(data[3].substring(2,4)), Integer.parseInt(data[3].substring(0,2))));

        return user;
    }
}
