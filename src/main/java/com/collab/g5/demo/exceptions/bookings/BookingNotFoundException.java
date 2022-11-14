package com.collab.g5.demo.exceptions.bookings;

public class BookingNotFoundException extends RuntimeException{
    public BookingNotFoundException(int id) {
        super("Booking " + id + " not found");
    }
    public BookingNotFoundException(){super("no bookings found");}
}
