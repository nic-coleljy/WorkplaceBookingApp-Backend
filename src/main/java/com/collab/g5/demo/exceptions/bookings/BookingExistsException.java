package com.collab.g5.demo.exceptions.bookings;

import com.collab.g5.demo.bookings.Bookings;

public class BookingExistsException extends RuntimeException {
    public BookingExistsException(Bookings b) {
        super("You already have a booking on " + b.getBDate());
    }
}
