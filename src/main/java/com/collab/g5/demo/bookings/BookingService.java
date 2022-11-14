package com.collab.g5.demo.bookings;

import com.collab.g5.demo.bookings.Bookings;
import com.collab.g5.demo.users.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface BookingService {
    //CREATE
    Bookings save(Bookings bookings);

    //READ
    List<Bookings> getAllBookings();

    List<Bookings> getBookingByUser(String email);

    List<Bookings> getAllMyBookings(String email);

    List<Bookings> getAllMyPastBookings(User u);

    List<Bookings> getAllMyUpcomingBookings(User u);

    Bookings getBookingsById(int id);

    int getBookingsCountByEmail(String email);

    int getBookingsCountByUserAndMonth(String email, LocalDate date);

    int getBookingsCountByDate(int cid, LocalDate date);

    int checkForDuplicateBookings(String userEmail, LocalDate date);

    //UPDATE
    Bookings updateBookings(int id, Bookings bookings);

    void autoUpdateBookings(int cid, LocalDate date);

    //DELETE
    void delete(Bookings bookings);

    void deleteById(int id);

    //helper methods
    List<Bookings> removeDuplicates(List<Bookings> bookingsList, List<User> userList);
}
