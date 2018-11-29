package be.pxl.ja.ticketsystem;

public interface Mapper<T> {
    T map(String[] data);
}
